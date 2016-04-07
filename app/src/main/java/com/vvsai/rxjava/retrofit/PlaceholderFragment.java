package com.vvsai.rxjava.retrofit;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxFragment;
import com.vvsai.rxjava.R;
import com.vvsai.rxjava.utils.LogUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lychee on 2016/3/29.
 */
public class PlaceholderFragment extends RxFragment {


    @Bind(R.id.a_nodata_tv)
    TextView aNodataTv;
    @Bind(R.id.a_nodata_iv)
    ImageView aNodataIv;
    @Bind(R.id.a_nodata_rl)
    RelativeLayout aNodataRl;

    Call<MatchListBean> matchList;
    Call<UploadFileBean> uploadFile;
    //    Subscription subscribe;
    Subscriber<String> subscriber;

    private int id = 999;
    public static final String ID = "id";
    private String str = "";
    private static final String STR = "str";
    private boolean isVisible = false;
    private boolean isPrepared = false;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.i(getTag() + "---" + id + "---onSaveInstanceState");
        outState.putString(STR, "lychee");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            LogUtil.i(getTag() + "---" + id + "---onActivityCreated");
        } else {
            str = savedInstanceState.getString(STR);
            LogUtil.i(getTag() + "---" + id + "---onActivityCreated" + "---" + str);
        }
    }

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


    }

    private void uploadFile() {
//        File file = new File("/storage/emulated/0/Android/data/com.vvsai.vvsaimain/files/Pictures/1459827540797.jpg");
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/1.png");
//        String fileName = file.getName();
//        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
//        LogUtil.i("subscriber---" + id + "---" + subscriber.isUnsubscribed());

        Observable.just(file)
                .map(new Func1<File, RequestBody>() {
                    @Override
                    public RequestBody call(File file) {
                        return RequestBody.create(MediaType.parse("image/*"), file);
                    }
                })
                .compose(this.<RequestBody>bindToLifecycle())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        aNodataTv.setText("加载数据中---" + id);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<RequestBody, Observable<String>>() {
                    @Override
                    public Observable<String> call(RequestBody requestBody) {
                        return MyRetrofit.getApiService().uploadFile(requestBody);
                    }
                })
//                .timeout(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i("onError---" + id + "---" + e.toString());
                        aNodataTv.setText("未能成功加载数据" + id);
                    }

                    @Override
                    public void onNext(String str) {
                        LogUtil.i("onNext---" + id + "---" + str);
                        aNodataTv.setText("数据" + id + "--" + (int) (Math.random() * 10));
                    }
                });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            LogUtil.i(getTag() + "---" + id + "---onCreateView");
        } else {
            str = savedInstanceState.getString(STR);
            LogUtil.i(getTag() + "---" + id + "---onCreateView" + "\n"
                    + str);
        }
        View rootView = inflater.inflate(R.layout.fragment_retrofit, container, false);
        ButterKnife.bind(this, rootView);
        isPrepared = true;
//        subscriber = new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LogUtil.i("onError---" + id + "---" + e.toString());
//                aNodataTv.setText("未能成功加载数据" + id);
//            }
//
//            @Override
//            public void onNext(String str) {
//                LogUtil.i("onNext---" + id + "---" + str);
//                aNodataTv.setText("数据" + id);
//            }
//        };
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
        loding();
//        normal();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.i(getTag() + "---" + id + "---setUserVisibleHint: " + getUserVisibleHint());
        isVisible = getUserVisibleHint();
        loding();
    }

    private void loding() {
        if (!isPrepared || !isVisible) {
            return;
        }
        uploadFile();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.i(getTag() + "---" + id + "---onDestroyView");
//        subscriber.unsubscribe();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.i(getTag() + "---" + id + "---onAttach");

    }

    @Override
    public void onPause() {
        super.onPause();

        LogUtil.i(getTag() + "---" + id + "---onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.i(getTag() + "---" + id + "---onStop");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.i(getTag() + "---" + id + "---onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(getTag() + "---" + id + "---onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(getTag() + "---" + id + "---onResume");

    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.i(getTag() + "---" + id + "---onStart");

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState == null) {
            LogUtil.i(getTag() + "---" + id + "---onViewStateRestored");
        } else {
            str = savedInstanceState.getString(STR);
            LogUtil.i(getTag() + "---" + id + "---onViewStateRestored" + "---" + str);
        }
    }

    private void normal() {
        matchList = MyRetrofit.getApiService().getMatchList("");
        matchList.enqueue(new Callback<MatchListBean>() {
            @Override
            public void onResponse(Call<MatchListBean> call, Response<MatchListBean> response) {
                LogUtil.i("数据---" + response.body().toString());
                aNodataRl.setVisibility(View.VISIBLE);
                aNodataTv.setText("数据");
            }

            @Override
            public void onFailure(Call<MatchListBean> call, Throwable t) {
                LogUtil.i("未能成功加载数据---" + call.toString() + "---" + t.getMessage());
                aNodataRl.setVisibility(View.VISIBLE);
                aNodataTv.setText("未能成功加载数据");
            }
        });
    }


}