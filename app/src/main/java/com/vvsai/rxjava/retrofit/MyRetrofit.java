package com.vvsai.rxjava.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by lychee on 2016/3/29.
 */
public class MyRetrofit {
    private static Retrofit retrofit = null;
    private static VVSaiServices apiService = null;
    private static OkHttpClient okHttpClient = null;
//    public static Context context;

    /**
     * 初始化
     */
    private static void init() {
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(1, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(VVSaiServices.BASE_URL)
                .build();
        apiService = retrofit.create(VVSaiServices.class);

    }

    public static VVSaiServices getApiService() {
        if (apiService != null) return apiService;
        init();
        return getApiService();
    }

}
