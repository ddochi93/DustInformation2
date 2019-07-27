package com.ddochi.data;

import com.ddochi.model.cur_location.CurLocation;
import com.ddochi.util.FineDustUtil;

import retrofit2.Callback;

public class CCurLocationRepository implements CurLocationRepository {
    private FineDustUtil mFineDustUtil;
    private int numOfRows;
    private int pageNo;
    private String umdName;

    public CCurLocationRepository() {
        mFineDustUtil = new FineDustUtil();
    }

    public CCurLocationRepository(int numOfRows,int pageNo,String umdName){
        this();
        this.numOfRows = numOfRows;
        this.pageNo = pageNo;
        this.umdName = umdName;
    }

    @Override
    public boolean isAvailable() {
        if(umdName != null) {
            return true;
        }
        return false;
    }

    @Override
    public void getCurLocationData(Callback<CurLocation> callback) {
        mFineDustUtil.getApi().getCurLocation(numOfRows,pageNo,umdName)
                .enqueue(callback);
    }
}
