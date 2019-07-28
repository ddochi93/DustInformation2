package com.ddochi.util;

import com.ddochi.model.tm_coordinates.Documents;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TransCoordApi {
    String BASE_URL = "http://dapi.kakao.com/";


    //?뒤에 문제없나 확인
    @Headers("Authorization: KakaoAK b80c0256e499fc599177397d95690f0d")
    @GET("v2/local/geo/transcoord.json?input_coord=WGS84&output_coord=TM")
    Call<Documents> getDocuments(@Query("x")String x,
                                 @Query("y")String y);



}
