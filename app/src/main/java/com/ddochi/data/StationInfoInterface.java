package com.ddochi.data;

import com.ddochi.model.nearby_station.StationInfo;

import retrofit2.Callback;

public interface StationInfoInterface {
    boolean isAvailable();
    void getStationInfo(Callback<StationInfo> callback);
}
