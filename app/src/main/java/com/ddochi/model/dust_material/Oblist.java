package com.ddochi.model.dust_material;

import com.google.gson.annotations.SerializedName;

public class Oblist {
    @SerializedName("dataTime")
    private String dataTime;
    @SerializedName("pm10Value")
    private String pm10Value;
    @SerializedName("pm25Value")
    private String pm25Value;

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getPm10Value() {
        return pm10Value;
    }

    public void setPm10Value(String pm10Value) {
        this.pm10Value = pm10Value;
    }

    public String getPm25Value() {
        return pm25Value;
    }

    public void setPm25Value(String pm25Value) {
        this.pm25Value = pm25Value;
    }
}
