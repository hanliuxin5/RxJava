package com.vvsai.rxjava.retrofit;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by lychee on 2016/3/29.
 */
public interface VVSaiServices {
    String BASE_URL = "http://vvsai.com:9000";

    @GET("/api/sport/searchSports")
    Observable<SportsListBean> getSportsList();

    @FormUrlEncoded
    @POST("/api/sportsEvent/searchSportEvents")
    Call<MatchListBean> getMatchList(@Field("status") String status);

    @FormUrlEncoded
    @POST("/api/arena/findArenaLists")
    Observable<VenuesBean> getArenaList(@Field("provinceCode") String provinceCode,@Field("sportclass") String sportclass, @Field("currentPage") int currentPage, @Field("pageSize") int pageSize);
//    @Multipart
//    @POST("/api/arena/findArenaLists")
//    Observable<VenuesBean> getArenaList(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("http://www.vvsai.com/uploadApp/uploadImage.php")
    Observable<String> uploadFile(@Part("uploadfile\"; filename=\"pp.png\" ") RequestBody file);

}
