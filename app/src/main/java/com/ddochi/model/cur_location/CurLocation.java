package com.ddochi.model.cur_location;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurLocation {
    @SerializedName("list")
    private List<Coordinate> list;

    public List<Coordinate> getList() {
        return list;
    }
    public void setList(List<Coordinate> list) {
        this.list = list;
    }
}
