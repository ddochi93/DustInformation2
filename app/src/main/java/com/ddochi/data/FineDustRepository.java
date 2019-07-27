package com.ddochi.data;

import com.ddochi.model.dust_material.FineDust;

import retrofit2.Callback;

public interface FineDustRepository {
    //나중에 코딩 복잡해지지 않기 위해
    boolean isAvailable();
    void getFineDustData(Callback<FineDust> callback);
}
