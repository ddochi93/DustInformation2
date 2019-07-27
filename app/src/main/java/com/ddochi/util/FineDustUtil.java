package com.ddochi.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FineDustUtil {
    //싱글톤으로 하는게 나은가
    private FineDustApi mGetApi;

    public FineDustUtil() {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(FineDustApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mGetApi = mRetrofit.create(FineDustApi.class);
    }

    public FineDustApi getApi(){
        return this.mGetApi; //외부에서 retrofit객체 갖다 쓸 수 있도록함.
    }


}
