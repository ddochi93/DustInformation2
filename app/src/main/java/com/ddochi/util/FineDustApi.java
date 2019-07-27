package com.ddochi.util;

import com.ddochi.model.cur_location.CurLocation;
import com.ddochi.model.dust_material.FineDust;
import com.ddochi.model.nearby_station.StationInfo;
import com.ddochi.model.tm_coordinates.Documents;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface FineDustApi {
    String BASE_URL = "http://openapi.airkorea.or.kr/";

    //http://openapi.airkorea.or.kr/
    // openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?
    // serviceKey=vcP6iwVQFOyx50EjucFsd6q4URLx2jrz02K1UM5413e7LeV%2B%2FAA4VbdRKG%2Fgrjb9zik8YvFbTWYVr1OM2bXbWQ%3D%3D&
    // numOfRows=1&pageNo=1&stationName=관악구&
    // dataTerm=DAILY&ver=1.3&_returnType=json
    @GET("openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=vcP6iwVQFOyx50EjucFsd6q4URLx2jrz02K1UM5413e7LeV%2B%2FAA4VbdRKG%2Fgrjb9zik8YvFbTWYVr1OM2bXbWQ%3D%3D&ver=1.3&_returnType=json")
    Call<FineDust> getFineDust(@Query("numOfRows") int numOfRows,
                               @Query("pageNo") int pageNo,
                               @Query("stationName")String stationName,
                               @Query("dataTerm")String dataTerm
                               );

    //http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getTMStdrCrdnt
    // ?ServiceKey=vcP6iwVQFOyx50EjucFsd6q4URLx2jrz02K1UM5413e7LeV%2B%2FAA4VbdRKG%2Fgrjb9zik8YvFbTWYVr1OM2bXbWQ%3D%3D&
    // _returnType=json&numOfRows=15&pageNo=1&umdName=서울특별시 동작구 상도동


    @GET("openapi/services/rest/MsrstnInfoInqireSvc/getTMStdrCrdnt?ServiceKey=vcP6iwVQFOyx50EjucFsd6q4URLx2jrz02K1UM5413e7LeV%2B%2FAA4VbdRKG%2Fgrjb9zik8YvFbTWYVr1OM2bXbWQ%3D%3D&_returnType=json")
    Call<CurLocation> getCurLocation(@Query("numOfRows")int numOfRows,
                                     @Query("pageNo")int pageNo,
                                     @Query("umdName")String umdName);


    @GET("openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList?ServiceKey=vcP6iwVQFOyx50EjucFsd6q4URLx2jrz02K1UM5413e7LeV%2B%2FAA4VbdRKG%2Fgrjb9zik8YvFbTWYVr1OM2bXbWQ%3D%3D&_returnType=json")
    Call<StationInfo> getStationInfo(@Query("tmX")String tmX,
                                     @Query("tmY")String tmY );



    //?뒤에 문제없나 확인
    @Headers("Authorization:KakaoAK b80c0256e499fc599177397d95690f0d")
    @GET("https://dapi.kakao.com/v2/local/geo/transcoord.json")
    Call<Documents> getDocuments(@Query("x")double x,
                                 @Query("y")double y,
                                 @Query("input_coord")String input_coord,
                                 @Query("output_coord")String output_coord);
}
