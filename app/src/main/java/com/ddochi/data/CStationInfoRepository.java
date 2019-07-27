package com.ddochi.data;

import com.ddochi.model.nearby_station.StationInfo;
import com.ddochi.util.FineDustUtil;

import retrofit2.Callback;

public class CStationInfoRepository implements StationInfoInterface {
    private FineDustUtil mFineDustUtil;
    private String tmX;
    private String tmY;

    public CStationInfoRepository() {
        mFineDustUtil = new FineDustUtil();
    }

    public CStationInfoRepository(String tmX,String tmY) {
        this();
        this.tmX = tmX;
        this.tmY = tmY;
    }
    @Override
    public boolean isAvailable() {
        if(tmX != null && tmY != null)    //이거 맞나? double로 해야되는거아녀?
            return true;
        return false;
    }

    @Override
    public void getStationInfo(Callback<StationInfo> callback) {
        mFineDustUtil.getApi().getStationInfo(tmX,tmY)
                .enqueue(callback);
    }


}

