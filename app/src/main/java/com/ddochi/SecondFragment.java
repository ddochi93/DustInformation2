package com.ddochi;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ddochi.data.LocationFineDustRepository;
import com.ddochi.model.dust_material.FineDust;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SecondFragment extends Fragment {

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_second,container,false);

        LocationFineDustRepository lfr =  new LocationFineDustRepository(10,1,"관악구","DAILY");
        lfr.getFineDustData(new Callback<FineDust>() {
            @Override
            public void onResponse(Call<FineDust> call, Response<FineDust> response) {
                String pm25 = response.body().getList().get(0).getPm25Value();
                String pm10 = response.body().getList().get(0).getPm10Value();
                String estimate_time = response.body().getList().get(0).getDataTime();
                Log.d("onResponses내부",pm10);
                TextView pm25state =  rootView.findViewById(R.id.pm2_5_state);
                TextView pm10state = rootView.findViewById(R.id.pm10_state);
                TextView estimate_time_state = rootView.findViewById(R.id.estimate_time);
                pm25state.setText("초미세먼지 : "  + pm25);
                pm10state.setText("미세먼지 : " + pm10);
                estimate_time_state.setText("측정일시 : " + estimate_time);




            }

            @Override
            public void onFailure(Call<FineDust> call, Throwable t) {
                Log.e("secondFrag","불러오기실패ㅜㅜ");
            }
        });

        //새로고침하면 미세먼지 정보 다시 불러오도록 함.(final 말고 다른방법 있나?)
        final SwipeRefreshLayout refresh = rootView.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LocationFineDustRepository lfr =  new LocationFineDustRepository(10,1,"관악구","DAILY");
                lfr.getFineDustData(new Callback<FineDust>() {
                    @Override
                    public void onResponse(Call<FineDust> call, Response<FineDust> response) {
                        String pm25 = response.body().getList().get(0).getPm25Value();
                        String pm10 = response.body().getList().get(0).getPm10Value();
                        String estimate_time = response.body().getList().get(0).getDataTime();
                        Log.d("onResponses내부",pm10);
                        TextView pm25state =  rootView.findViewById(R.id.pm2_5_state);
                        TextView pm10state = rootView.findViewById(R.id.pm10_state);
                        TextView estimate_time_state = rootView.findViewById(R.id.estimate_time);
                        pm25state.setText("초미세먼지(새로고침후) : "  + pm25);
                        pm10state.setText("미세먼지 : " + pm10);
                        estimate_time_state.setText("측정일시 : " + estimate_time);
                        refresh.setRefreshing(false);


                    }

                    @Override
                    public void onFailure(Call<FineDust> call, Throwable t) {
                        Log.e("secondFrag","불러오기실패ㅜㅜ");
                    }
                });
            }
        });
        return rootView;
    }


}
