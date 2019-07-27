package com.ddochi.model.cur_location;

import com.google.gson.annotations.SerializedName;

public class Coordinate {
    @SerializedName("tmX")
    private String tmX;
    @SerializedName("tmY")
    private String tmY;
    @SerializedName("sidoName")
    private String sidoName;
    @SerializedName("sggName")
    private String sggName;
    @SerializedName("umdName")
    private String umdName;

    public String getTmX() {
        return tmX;
    }

    public void setTmX(String tmX) {
        this.tmX = tmX;
    }

    public String getTmY() {
        return tmY;
    }

    public void setTmY(String tmY) {
        this.tmY = tmY;
    }

    public String getSidoName() {
        return sidoName;
    }

    public void setSidoName(String sidoName) {
        this.sidoName = sidoName;
    }

    public String getSggName() {
        return sggName;
    }

    public void setSggName(String sggName) {
        this.sggName = sggName;
    }

    public String getUmdName() {
        return umdName;
    }

    public void setUmdName(String umdName) {
        this.umdName = umdName;
    }
}
