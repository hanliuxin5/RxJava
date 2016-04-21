package com.vvsai.rxjava.retrofit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vvsai.rxjava.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lychee on 2016/4/21.
 */
public class PlaceAdapter<T> extends RecyclerView.Adapter {
    public static final int STATE_NO_MORE = 1;
    public static final int STATE_LOAD_MORE = 2;
    public static final int STATE_INVALID_NETWORK = 3;
    public static final int STATE_HIDE = 5;
    public static final int STATE_REFRESHING = 6;
    public static final int STATE_LOAD_ERROR = 7;
    public static final int STATE_LOADING = 8;

    public final int BEHAVIOR_MODE;

    public static final int NEITHER = 0;
    public static final int ONLY_HEADER = 1;
    public static final int ONLY_FOOTER = 2;
    public static final int BOTH_HEADER_FOOTER = 3;

    protected static final int VIEW_TYPE_NORMAL = 0;
    protected static final int VIEW_TYPE_HEADER = -1;
    protected static final int VIEW_TYPE_FOOTER = -2;

    protected Context mContext;
    protected List<T> items;
    protected LayoutInflater mInflater;
    protected int mState;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnLoadingListener onLoadingListener;
    private OnLoadingHeaderCallBack onLoadingHeaderCallBack;

    public PlaceAdapter(Context context, List<T> items, int mode) {
        this.mContext = context;
        this.items = items;
        mState = STATE_HIDE;
        mInflater = LayoutInflater.from(mContext);
        BEHAVIOR_MODE = mode;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                if (onLoadingHeaderCallBack != null)
                    return onLoadingHeaderCallBack.onCreateHeaderHolder(parent);
                else throw new IllegalArgumentException("VIEW_TYPE_HEADER needs to implements!");
            case VIEW_TYPE_FOOTER:
                return new FooterViewHolder(mInflater.inflate(R.layout.item_footer, parent, false));
            default:
                final RecyclerView.ViewHolder holder = onCreateDefaultViewHolder(parent, viewType);
                if (holder != null) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onItemClickListener != null)
                                onItemClickListener.onItemClick(holder.getAdapterPosition(), holder.getItemId(), v);
                        }
                    });
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (onItemLongClickListener == null)
                                return false;
                            onItemLongClickListener.onLongClick(holder.getAdapterPosition(), holder.getItemId(), v);
                            return true;
                        }
                    });
                }
                return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_HEADER:
                if (onLoadingHeaderCallBack != null)
                    onLoadingHeaderCallBack.onBindHeaderHolder(holder, position);
                break;

            case VIEW_TYPE_FOOTER:
                if (mState == STATE_LOAD_MORE && onLoadingListener != null) {
                    onLoadingListener.onLoading();
                }
                FooterViewHolder fvh = (FooterViewHolder) holder;
                fvh.itemView.setVisibility(View.VISIBLE);
                switch (mState) {
                    case STATE_LOAD_MORE:
                    case STATE_LOADING:
                        fvh.stateText.setText(mContext.getResources().getText(R.string.loading));
                        fvh.progressbar.setVisibility(View.VISIBLE);
                        break;
                    case STATE_NO_MORE:
                        fvh.stateText.setText(mContext.getResources().getText(R.string.load_no_more));
                        fvh.progressbar.setVisibility(View.GONE);
                        break;
                    case STATE_REFRESHING:
                        fvh.stateText.setText(mContext.getResources().getText(R.string.refreshing));
                        fvh.progressbar.setVisibility(View.GONE);
                        break;
                    case STATE_HIDE:
                        fvh.itemView.setVisibility(View.GONE);
                        break;
                }
                break;

            default:
                onBindDefaultViewHolder(holder, getRealIndex(position));
        }
    }

    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_place, parent, false));
    }

    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder vh = (ItemViewHolder) holder;
        VenuesBean.ResultEntity.ArenasEntity item = (VenuesBean.ResultEntity.ArenasEntity) items.get(position);
        Picasso p = Picasso.with(mContext);
        p.setIndicatorsEnabled(true);
        p.load(item.getIcon())
                .into(vh.iv);

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && (BEHAVIOR_MODE == ONLY_HEADER || BEHAVIOR_MODE == BOTH_HEADER_FOOTER))
            return VIEW_TYPE_HEADER;
        if (position + 1 == getItemCount() && (BEHAVIOR_MODE == ONLY_FOOTER || BEHAVIOR_MODE == BOTH_HEADER_FOOTER))
            return VIEW_TYPE_FOOTER;
        else return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (BEHAVIOR_MODE == ONLY_FOOTER || BEHAVIOR_MODE == ONLY_HEADER) {
            return items.size() + 1;
        } else if (BEHAVIOR_MODE == BOTH_HEADER_FOOTER) {
            return items.size() + 2;
        } else return items.size();
    }

    /**
     * 传入的position为真实的position
     *
     * @param position
     * @return
     */
    private int getRealIndex(int position) {
        return BEHAVIOR_MODE == ONLY_HEADER || BEHAVIOR_MODE == BOTH_HEADER_FOOTER ? position - 1 : position;
    }

    /**
     * 传入的position为数据集中的position
     *
     * @param position
     * @return
     */
    private int getDateIndex(int position) {
        return BEHAVIOR_MODE == ONLY_HEADER || BEHAVIOR_MODE == BOTH_HEADER_FOOTER ? position + 1 : position;
    }

    public final void addItems(List<T> objs) {
        items.addAll(objs);
        notifyDataSetChanged();
    }

    public final void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(getDateIndex(position));
    }

    public final void clear() {
        int count = items.size();
        items.clear();
        notifyItemRangeRemoved(0, count);
    }

    public final T getItem(int position) {
        return items.get(getDateIndex(position));
    }

    public final void setState(int state) {
        mState = state;
    }

    public final int getState() {
        return mState;
    }

    public final int getDataSize() {
        return items.size();
    }


    public interface OnItemClickListener {
        void onItemClick(int position, long id, View view);
    }


    public interface OnItemLongClickListener {
        void onLongClick(int position, long id, View view);
    }


    public interface OnLoadingListener {
        void onLoading();
    }

    public final void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public final void setOnItemLongClickListener(OnItemLongClickListener listener) {
        onItemLongClickListener = listener;
    }

    public final void setOnLoadingListener(OnLoadingListener listener) {
        onLoadingListener = listener;
    }


    public interface OnLoadingHeaderCallBack {
        RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent);
        void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position);
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.progressbar)
        ProgressBar progressbar;
        @Bind(R.id.state_text)
        TextView stateText;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv)
        ImageView iv;

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
