package com.ddochi.model.nearby_station;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StationInfo {
    @SerializedName("list")
    private List<Station> list;

    public List<Station> getList() {
        return list;
    }

    public void setList(List<Station> list) {
        this.list = list;
    }
}
