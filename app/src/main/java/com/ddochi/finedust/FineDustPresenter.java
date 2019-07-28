package com.ddochi.finedust;

import com.ddochi.data.FineDustRepository;
import com.ddochi.model.dust_material.FineDust;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FineDustPresenter implements FineDustContract.UserActionsListener {
    private final FineDustRepository mRepository;
    private final FineDustContract.View mView;

    public FineDustPresenter(FineDustRepository repository, FineDustContract.View view ) {
        mRepository = repository;
        mView = view;
    }
    @Override
    public void loadFineDustData() {
        //데이터 제공이 가능하다면
        if(mRepository.isAvailable()){
            //로딩시작
            mView.loadingStart();

            //데이터가져오기
            mRepository.getFineDustData(new Callback<FineDust>() {
                @Override
                public void onResponse(Call<FineDust> call, Response<FineDust> response) {
                    mView.showFineDustResult(response.body());
                    //로딩끝
                    //여기서 최신시간 데이터 잘 받아오나 확인.
                    mView.loadingEnd();
                }

                @Override
                public void onFailure(Call<FineDust> call, Throwable t) {
                    //에러표시하기
                    mView.showLoadError(t.getLocalizedMessage());
                    //로딩끝
                    mView.loadingEnd();
                }
            });
        }
        else {
            mView.showLoadError("측정소 찾기 실패");
            //Log.d("FineDustPresenter","측정소 찾기 실패:");
        }

    }
}
