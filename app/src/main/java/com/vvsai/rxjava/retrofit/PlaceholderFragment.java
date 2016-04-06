package com.vvsai.rxjava.retrofit;

import android.content.Context;
import android.os.Bundle;
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
        LogUtil.i(getTag() + "---" + id + "---onCreate");

        id = getArguments().getInt(ID);


    }

    private void uploadFile() {
        File file = new File("/storage/emulated/0/Android/data/com.vvsai.vvsaimain/files/Pictures/1459827540797.jpg");
//        String fileName = file.getName();
//        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        Observable.just(file)
                .map(new Func1<File, RequestBody>() {
                    @Override
                    public RequestBody call(File file) {
                        return RequestBody.create(MediaType.parse("image/*"), file);
                    }
                })
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<RequestBody, Observable<String>>() {
                    @Override
                    public Observable<String> call(RequestBody requestBody) {
                        return MyRetrofit.getApiService().uploadFile(requestBody);
                    }
                })
                .subscribeOn(Schedulers.io())
                .compose(this.<String>bindToLifecycle())
                //默认情况下， doOnSubscribe() 执行在 subscribe() 发生的线程；
                // 而如果在 doOnSubscribe() 之后有 subscribeOn() 的话，它将执行在离它最近的 subscribeOn() 所指定的线程。
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        aNodataTv.setText("加载数据中---" + id);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogUtil.i(getTag() + "---" + id + "---onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_retrofit, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        LogUtil.i(getTag() + "---" + id + "---onViewCreated");
        subscriber = new Subscriber<String>() {
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
                aNodataTv.setText("数据" + id);
            }
        };
        uploadFile();
//        normal();

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