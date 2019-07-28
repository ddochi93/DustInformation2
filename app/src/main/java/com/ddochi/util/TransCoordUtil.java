package com.ddochi.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransCoordUtil {
    //싱글톤으로 하는게 나은가
    private TransCoordApi mGetApi;

    public TransCoordUtil() {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(TransCoordApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mGetApi = mRetrofit.create(TransCoordApi.class);
    }

    public TransCoordApi getApi(){
        return this.mGetApi; //외부에서 retrofit객체 갖다 쓸 수 있도록함.
    }

}
