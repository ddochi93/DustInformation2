package com.ddochi.model.nearby_station;

import com.google.gson.annotations.SerializedName;

public class Station {
    @SerializedName("tm")
    private Double tm;
    @SerializedName("stationName")
    private String stationName;

    public Double getTm() {
        return tm;
    }

    public void setTm(Double tm) {
        this.tm = tm;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
