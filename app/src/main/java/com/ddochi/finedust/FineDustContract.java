package com.ddochi.finedust;

import com.ddochi.model.dust_material.FineDust;

public class FineDustContract {
    public interface View {
        void showFineDustResult(FineDust fineDust);
        void showLoadError(String message);
        void loadingStart();
        void loadingEnd();
        void reload(int numOfRows, int pageNo, String stationName, String dataTerm,String umd);
    }

    public interface UserActionsListener {
        void loadFineDustData();
    }
}
