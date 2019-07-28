package com.ddochi.model.tm_coordinates;

import com.google.gson.annotations.SerializedName;

public class TMCoordinates {
    @SerializedName("x")
    private String x;
    @SerializedName("y")
    private String y;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
