package com.ddochi.data;

import com.ddochi.model.tm_coordinates.Documents;
import com.ddochi.util.TransCoordUtil;

import retrofit2.Callback;

public class CTmCoordinatesRepository implements TmCoordinatesRepository {
    private String x;
    private String y;

    private TransCoordUtil mTransCoordUtil;

    public CTmCoordinatesRepository() {mTransCoordUtil = new TransCoordUtil();}
    public CTmCoordinatesRepository(String x, String y){
        this();
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isAvailable() {
        //남한의 북남방한계 && 동서방 한계
        double latitude = Double.parseDouble(y);
        double longitude = Double.parseDouble(x);
        if(latitude >= 33.0 && latitude <= 38.6 && longitude >=125.17 && longitude <= 131.87)
            return true;
        return false;
    }

    @Override
    public void getTmCoordinatesDocuments(Callback<Documents> callback) {
        mTransCoordUtil.getApi().getDocuments(x,y)
                .enqueue(callback);
    }
}
