package com.ddochi.model.dust_material;

import com.google.gson.annotations.SerializedName;

public class Parm {
    @SerializedName("stationName")
    private String stationName;

    public String getStationName(){
        return stationName;
    }

    public void setStationName(String stationName){
        this.stationName = stationName;
    }
}
