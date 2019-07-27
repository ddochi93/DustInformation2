package com.ddochi.data;

import com.ddochi.model.dust_material.FineDust;
import com.ddochi.util.FineDustUtil;

import retrofit2.Callback;

public class LocationFineDustRepository implements FineDustRepository {
    private FineDustUtil mFineDustUtil;
    private int numOfRows;
    private int pageNo;
    private String stationName;
    private String dataTerm;


    private String umdName;

    public LocationFineDustRepository(){
        mFineDustUtil = new FineDustUtil();
    }

    public LocationFineDustRepository(int numOfRows, int pageNo, String stationName, String dataTerm) {
        this();
        this.numOfRows = numOfRows;
        this.pageNo = pageNo;
        this.stationName = stationName;
        this.dataTerm = dataTerm;
    }

    @Override
    public boolean isAvailable() {
        if(stationName != null) //추후수정 (존재하는 측정소인지 판별하여 넘기도록)
        {
            return true;
        }
        return false;
    }

    @Override
    public void getFineDustData(Callback<FineDust> callback) {
        mFineDustUtil.getApi().getFineDust(numOfRows,pageNo,stationName,dataTerm)
                .enqueue(callback); //비동기처리 (?_)
    }

}
