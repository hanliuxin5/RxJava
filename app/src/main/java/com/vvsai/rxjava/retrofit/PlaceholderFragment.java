package com.vvsai.rxjava.retrofit;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.vvsai.rxjava.R;
import com.vvsai.rxjava.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by lychee on 2016/3/29.
 */
public class PlaceholderFragment extends RxFragment implements SwipeRefreshLayout.OnRefreshListener, ErrorLayout.OnActiveClickListener,
        PlaceAdapter.OnLoadingListener, PlaceAdapter.OnLoadingHeaderCallBack {

    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.error_frame)
    ErrorLayout errorFrame;
    @Bind(R.id.in_bt)
    Button inBt;
    @Bind(R.id.in_et)
    ImageView inEt;

//    Call<MatchListBean> matchList;
//    Call<UploadFileBean> uploadFile;

    //    Subscription subscribe;
//    Subscriber<VenuesBean> subscriber;

    private int id = 999;
    public static final String ID = "id";
    public int mState = STATE_NONE;

    private boolean isVisible = false;
    private boolean isPrepared = false;


    private PlaceAdapter placeAdapter;
    private int currentPage = 1;
    private int pageSize = 10;
    private ArrayList<VenuesBean.ResultEntity.ArenasEntity> vList = new ArrayList<>();

    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESHING = 1;
//    public static final int STATE_ERROR = 2;

    //    public static final int LOAD_MODE_DEFAULT = 1; // 默认的下拉刷新,小圆圈
//    public static final int LOAD_MODE_UP_DRAG = 2; // 上拉到底部时刷新
//    public static final int LOAD_MODE_CACHE = 3; // 缓存,ErrorLayout显示情况

    protected static final String BUNDLE_STATE_REFRESH = "BUNDLE_STATE_REFRESH";
    private String str = "";
    private static final String STR = "str";


    public static PlaceholderFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(ID, id);
        PlaceholderFragment fragment = new PlaceholderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            LogUtil.i(getTag() + "---" + id + "---onCreate");
        } else {
            str = savedInstanceState.getString(STR);
            LogUtil.i(getTag() + "---" + id + "---onCreate" + "---" + str);
        }
        id = getArguments().getInt(ID);
        currentPage = id;
//        setRetainInstance(true);

    }

    private void getList() {
//        Map<String, RequestBody> map = new HashMap<>();
//        map.put("currentPage", toRequestBody(currentPage));
//        map.put("pageSize", toRequestBody(pageSize));
//        MyRetrofit.getApiService().getArenaList(map)
//        subscriber = new Subscriber<VenuesBean>() {
//            @Override
//            public void onCompleted() {
//                onLoadFinishState(LOAD_MODE_DEFAULT);
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                onLoadErrorState(LOAD_MODE_DEFAULT);
//
//            }
//
//            @Override
//            public void onNext(VenuesBean mlb) {
//                onLoadResultData(mlb);
//
//            }
//        };

        MyRetrofit.getApiService().getArenaList("51", "", currentPage, pageSize)
                .subscribeOn(Schedulers.io())
                .compose(this.<VenuesBean>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        onLoadIngState();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
//                .delay(2, TimeUnit.SECONDS)
//                .delaySubscription(2,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
                .subscribe(new Subscriber<VenuesBean>() {
                    @Override
                    public void onCompleted() {
                        onLoadFinishState();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onLoadErrorState();
                    }

                    @Override
                    public void onNext(VenuesBean mlb) {
                        onLoadResultData(mlb);
                    }
                });
    }

    /**
     * 请求成功，加载数据
     *
     * @param result
     */
    public void onLoadResultData(VenuesBean result) {
        if (result == null) {
            return;
        }
        List<VenuesBean.ResultEntity.ArenasEntity> arenas = result.getResult().getArenas();
        if (currentPage == id)
            placeAdapter.clear();
        if (placeAdapter.getDataSize() + arenas.size() == 0 || (placeAdapter.getDataSize() == 0 && currentPage > 2)) {
            //列表之前的数据加上新加载的数据总数为0
            errorFrame.setState(ErrorLayout.EMPTY_DATA);
            placeAdapter.setState(PlaceAdapter.STATE_HIDE);
            return;
        } else if (currentPage > 2) {
            //模拟只能加载2页数据
            errorFrame.setState(ErrorLayout.HIDE);
            placeAdapter.setState(PlaceAdapter.STATE_NO_MORE);
            placeAdapter.notifyDataSetChanged();
        } else {
            //一般情况正常加载到了数据
            errorFrame.setState(ErrorLayout.HIDE);
            placeAdapter.setState(PlaceAdapter.STATE_LOAD_MORE);
            placeAdapter.addItems(arenas);
        }
    }

    /**
     * 加载中
     */
    public void onLoadIngState() {
        if (placeAdapter.getDataSize() == 0) {
            errorFrame.setState(ErrorLayout.LOADING);
        }
    }

    /**
     * 加载完成!
     */
    public void onLoadFinishState() {
        swipeRefresh.setRefreshing(false);
        swipeRefresh.setEnabled(true);
        mState = STATE_NONE;
    }

    /**
     * 加载失败
     */
    public void onLoadErrorState() {
        swipeRefresh.setEnabled(true);
        swipeRefresh.setRefreshing(false);
        mState = STATE_NONE;
        if (placeAdapter.getDataSize() > 0) {
            Toast.makeText(getActivity(), "数据加载失败", Toast.LENGTH_SHORT).show();
        } else {
            errorFrame.setState(ErrorLayout.LOAD_FAILED);
        }
    }


    @Override
    public void onLoadActiveClick() {
        errorFrame.setState(ErrorLayout.LOADING);
        currentPage = id;
        getList();
    }

    @Override
    public void onLoading() {
        if (mState == STATE_REFRESHING) {
            placeAdapter.setState(PlaceAdapter.STATE_REFRESHING);
            return;
        }
        currentPage++;
        placeAdapter.setState(PlaceAdapter.STATE_LOADING);
        getList();

    }

    @Override
    public void onRefresh() {
        if (mState == STATE_REFRESHING)
            return;
        mState = STATE_REFRESHING;
        swipeRefresh.setRefreshing(true);
        swipeRefresh.setEnabled(false);
        currentPage = id;
        getList();
    }

    private void loading() {
        if (!isPrepared || !isVisible) {
            return;
        }
        //可以加上任意你需要的判断，时间间隔？缓存失效？等等
        if (placeAdapter.getDataSize() == 0) {
            getList();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        LogUtil.i(getTag() + "---" + id + "---setUserVisibleHint: " + getUserVisibleHint());
        isVisible = getUserVisibleHint();
        loading();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(getTag() + "---" + id + "---onResume");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null) {
//            LogUtil.i(getTag() + "---" + id + "---onCreateView");
        } else {
            str = savedInstanceState.getString(STR);
//            LogUtil.i(getTag() + "---" + id + "---onCreateView" + "\n"
//                    + str);
        }
        View rootView = inflater.inflate(R.layout.fragment_retrofit, container, false);
        ButterKnife.bind(this, rootView);
        isPrepared = true;
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            LogUtil.i(getTag() + "---" + id + "---onViewCreated");
        } else {
            str = savedInstanceState.getString(STR);
            LogUtil.i(getTag() + "---" + id + "---onViewCreated" + "---" + str);
        }

        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeResources(
                R.color.swipe_refresh_first, R.color.swipe_refresh_second,
                R.color.swipe_refresh_third, R.color.swipe_refresh_four
        );
        errorFrame.setOnActiveClickListener(this);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
//        inEt.setText((Math.random() * 10 + 1) + "");
        inBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                inEt.setText((Math.random() * 10 + 1) + "");
                View rootView = getActivity().findViewById(android.R.id.content).getRootView();
                rootView.setDrawingCacheEnabled(true);
                Bitmap bitmap = rootView.getDrawingCache();
                inEt.setImageBitmap(null);
                inEt.setImageBitmap(bitmap);
//                bitmap = null;
            }
        });
        if (placeAdapter != null) {
            rv.setAdapter(placeAdapter);
        } else {

            placeAdapter = new PlaceAdapter(getActivity(), vList, PlaceAdapter.BOTH_HEADER_FOOTER);
            rv.setAdapter(placeAdapter);
            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(placeAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(rv);
            placeAdapter.setOnLoadingHeaderCallBack(this);
            placeAdapter.setOnLoadingListener(this);
        }
        loading();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position) {
//        LogUtil.e("onBindHeaderHolder: " + position);
        HeaderViewHolder vh = (HeaderViewHolder) holder;
        vh.button4.setText((Math.random() * 10 + 1) + "");
    }

//    private void uploadFile() {
////        File file = new File("/storage/emulated/0/Android/data/com.vvsai.vvsaimain/files/Pictures/1459827540797.jpg");
//        final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/11.png");
////        String fileName = file.getName();
////        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
////        LogUtil.i("subscriber---" + id + "---" + subscriber.isUnsubscribed());
//
//        Observable
////                .just(file)
//                .create(new Observable.OnSubscribe<File>() {
//                    @Override
//                    public void call(Subscriber<? super File> subscriber) {
////                        LogUtil.e("create: currentThread---" + Thread.currentThread().getId());
////                        subscriber.onNext(file);
//                    }
//                })
//
////                .subscribeOn(Schedulers.io())
//                .compose(this.<File>bindToLifecycle())
//                .observeOn(Schedulers.io())
//                .map(new Func1<File, RequestBody>() {
//                    @Override
//                    public RequestBody call(File file) {
////                        LogUtil.e("map: currentThread---" + Thread.currentThread().getId());
//                        return RequestBody.create(MediaType.parse("image/*"), file);
//                    }
//                })
////                .doOnSubscribe(new Action0() {
////                    @Override
////                    public void call() {
////                        LogUtil.e("doOnSubscribe: currentThread---" + Thread.currentThread().getId());
////                        aNodataTv.setText("加载数据中---" + id);
////                    }
////                })
//                .delay(3, TimeUnit.SECONDS)
//
////                .subscribeOn(Schedulers.io())
////                .observeOn(Schedulers.io())
//                .flatMap(new Func1<RequestBody, Observable<String>>() {
//                    @Override
//                    public Observable<String> call(RequestBody requestBody) {
////                        LogUtil.e("flatMap: currentThread---" + Thread.currentThread().getId());
//                        return MyRetrofit.getApiService().uploadFile(requestBody);
////                                .doOnError(new Action1<Throwable>() {
////                                    @Override
////                                    public void call(Throwable throwable) {
////                                        LogUtil.i("doOnError---" + id + "---" + throwable.toString());
////                                    }
////                                });
//                    }
//                })
////                .timeout(3, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(subscriber);
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.i("onError---" + id + "---" + e.toString());
////                        aNodataTv.setText("未能成功加载数据" + id + "--" + (int) (Math.random() * 10));
//                    }
//
//                    @Override
//                    public void onNext(String str) {
////                        LogUtil.e("Subscriber: currentThread---" + Thread.currentThread().getId());
//                        LogUtil.i("onNext---" + id + "---" + str);
////                        aNodataTv.setText("数据" + id + "--" + (int) (Math.random() * 10));
//                    }
//                });
//
//
//    }
//
//    private static RequestBody toRequestBody(String value) {
//        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
//        return body;
//    }
//
//    private static RequestBody toRequestBody(int value) {
//        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value + "");
//        return body;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        LogUtil.i(getTag() + "---" + id + "---onDestroyView");
////        subscriber.unsubscribe();
////        ButterKnife.unbind(this);
////        ButterKnife.unbind(this);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        LogUtil.i(getTag() + "---" + id + "---onStop");
//    }
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
////        LogUtil.i(getTag() + "---" + id + "---onSaveInstanceState");
//        outState.putString(STR, "lychee");
//        outState.putInt(BUNDLE_STATE_REFRESH, mState);
//
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (savedInstanceState == null) {
////            LogUtil.i(getTag() + "---" + id + "---onActivityCreated");
//        } else {
//            str = savedInstanceState.getString(STR);
////            LogUtil.i(getTag() + "---" + id + "---onActivityCreated" + "---" + str);
//        }
//    }
//
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
////        LogUtil.i(getTag() + "---" + id + "---onAttach");
//
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
////        LogUtil.i(getTag() + "---" + id + "---onPause");
//    }
//
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
////        LogUtil.i(getTag() + "---" + id + "---onDetach");
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        LogUtil.i(getTag() + "---" + id + "---onDestroy");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
////        LogUtil.i(getTag() + "---" + id + "---onResume");
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
////        LogUtil.i(getTag() + "---" + id + "---onStart");
//
//    }
//
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        if (savedInstanceState == null) {
////            LogUtil.i(getTag() + "---" + id + "---onViewStateRestored");
//        } else {
//            str = savedInstanceState.getString(STR);
////            LogUtil.i(getTag() + "---" + id + "---onViewStateRestored" + "---" + str);
//        }
//    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.button4)
        Button button4;
        @Bind(R.id.editText2)
        EditText editText2;

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    LogUtil.e("我是头部点击");
                    placeAdapter.notifyItemChanged(0);
                }
            });
        }
    }
}