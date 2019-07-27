package com.ddochi.db;

import io.realm.RealmObject;

public class LocationRealmObject extends RealmObject {
    private int numOfRows;
    private int pageNo;
    private String stationName;
    private String dataTerm;
    private String curLocation;

    public int getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getDataTerm() {
        return dataTerm;
    }

    public void setDataTerm(String dataTerm) {
        this.dataTerm = dataTerm;
    }

    public String getCurLocation() {
        return curLocation;
    }

    public void setCurLocation(String curLocation) {
        this.curLocation = curLocation;
    }
}
