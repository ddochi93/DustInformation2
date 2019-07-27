package com.ddochi.data;

import com.ddochi.model.cur_location.CurLocation;

import retrofit2.Callback;

public interface CurLocationRepository {
    boolean isAvailable();
    void getCurLocationData(Callback<CurLocation> callback);
}
