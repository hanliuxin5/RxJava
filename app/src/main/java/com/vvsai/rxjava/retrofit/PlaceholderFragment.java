package com.vvsai.rxjava.retrofit;

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
import java.net.FileNameMap;
import java.net.URLConnection;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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

    public PlaceholderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.i("onError---" + e.toString());
                aNodataRl.setVisibility(View.VISIBLE);
                aNodataTv.setText("未能成功加载数据");
            }

            @Override
            public void onNext(String str) {
                LogUtil.i("onNext---" + str);
                aNodataRl.setVisibility(View.VISIBLE);
                aNodataTv.setText("数据");
            }
        };
    }

    public static PlaceholderFragment newInstance() {
        PlaceholderFragment fragment = new PlaceholderFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_retrofit, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        uploadFile();
//        normal();
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private void uploadFile() {
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/1.png");
        File file = new File("/storage/emulated/0/Android/data/com.vvsai.vvsaimain/files/Pictures/1458742768797.jpg");
//        String fileName = file.getName();
        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        MyRetrofit.getApiService().uploadFile(fbody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

//        uploadFile = MyRetrofit.getApiService().uploadFile(fbody);
//        uploadFile.enqueue(new Callback<UploadFileBean>() {
//            @Override
//            public void onResponse(Call<UploadFileBean> call, Response<UploadFileBean> response) {
//                LogUtil.i("CALL---" + call.toString() + "\n数据---" + response.body().toString());
//                aNodataRl.setVisibility(View.VISIBLE);
//                aNodataTv.setText("数据");
//            }
//
//            @Override
//            public void onFailure(Call<UploadFileBean> call, Throwable t) {
//                LogUtil.i("未能成功加载数据---" + call.toString() + "\n" + t.getMessage());
//                aNodataRl.setVisibility(View.VISIBLE);
//                aNodataTv.setText("未能成功加载数据");
//            }
//        });
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        LogUtil.i("onDestroyView");
        ButterKnife.unbind(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        subscriber.unsubscribe();
//        subscribe.unsubscribe();
//        uploadFile.cancel();
//        matchList.cancel();
//        LogUtil.i("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
//        LogUtil.i("onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        LogUtil.i("onDestroy");
    }
}