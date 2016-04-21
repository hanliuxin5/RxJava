package com.vvsai.rxjava.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
//        Interceptor headersInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request newRequest = chain.request().newBuilder()
//                        .addHeader("platform", "android")
//                        .build();
//                return chain.proceed(newRequest);
//            }
//        };

        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(7, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .addInterceptor(headersInterceptor)
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
