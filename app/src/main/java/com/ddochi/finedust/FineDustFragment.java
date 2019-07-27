package com.ddochi.finedust;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ddochi.MainActivity;
import com.ddochi.R;
import com.ddochi.common.AddLocationDialogFragment;
import com.ddochi.data.CCurLocationRepository;
import com.ddochi.data.CStationInfoRepository;
import com.ddochi.data.FineDustRepository;
import com.ddochi.data.LocationFineDustRepository;
import com.ddochi.model.cur_location.Coordinate;
import com.ddochi.model.cur_location.CurLocation;
import com.ddochi.model.dust_material.FineDust;
import com.ddochi.model.nearby_station.StationInfo;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FineDustFragment extends Fragment implements FineDustContract.View {
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TextView mCurLocation;

    private TextView mStationName;
    private TextView mEstimateTime;
    private ImageView mEmoji;
    private TextView mState; //"좋음","나쁨" 상태정보표시
    private TextView mPm10State;
    private TextView mPm25State;
    private ScrollView mScrollView;

    private FineDustRepository mRepository;
    private FineDustPresenter mPresenter;

    private MaterialButton mAddButton;
    private MaterialButton mListButton;

    private int mFragmentIndex;



    public static FineDustFragment newInstance(int numOfRows, int pageNo,String stationName, String dataTerm,String curLocation,int fragmentIndex) {
        //팩토리메서드이다.
        Bundle args = new Bundle();
        args.putInt("numOfRows",numOfRows);
        args.putInt("pageNo",pageNo);
        args.putString("stationName",stationName);
        args.putString("dataTerm",dataTerm);
        args.putString("curLocation",curLocation);
        args.putInt("fragmentIndex",fragmentIndex);
        FineDustFragment fragment = new FineDustFragment();
        fragment.setArguments(args);
        return fragment;                    //지역추가 시 요청을 args로 넘기는건가?
    }

    public FineDustFragment() {
        //반드시 필요.
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //이거 뭐임?
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second,container,false);

        mStationName = view.findViewById(R.id.station_name);
        mEstimateTime = view.findViewById(R.id.estimate_time);
        mEmoji = view.findViewById(R.id.emoji);
        mState = view.findViewById(R.id.state);
        mPm10State = view.findViewById(R.id.pm10_state);
        mPm25State = view.findViewById(R.id.pm2_5_state);
        mScrollView = view.findViewById(R.id.content);
        mCurLocation = view.findViewById(R.id.cur_location);


        //뷰페이저에 프래그먼트 들어오고 나갈때마다 새로 받아온다? 그래서 값들을 저장한뒤 불러오는 코드? (아닌듯...)
        if(savedInstanceState != null) {
            //mStationName.setText(savedInstanceState.getString());
            Log.d("FineDustFragment","여기에 복구할 코드 넣으시오");
            mStationName.setText(savedInstanceState.getString("stationName"));
            mEstimateTime.setText(savedInstanceState.getString("estimateTime"));
           // mEmoji.setImageResource(savedInstanceState.getI);
            mState.setText(savedInstanceState.getString("state"));
            mPm10State.setText(savedInstanceState.getString("pm10_state"));
            mPm25State.setText(savedInstanceState.getString("pm2.5_state"));
            mCurLocation.setText(savedInstanceState.getString("curLocation"));
            mFragmentIndex = savedInstanceState.getInt("fragmentIndex");

            Log.d("Index확인",mFragmentIndex+"");

            //mScrollView.setText(savedInstanceSt
        }
        mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE); //refresh 화살표의 색상 변경.
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadFineDustData();
            }
        });

        mAddButton = view.findViewById(R.id.addButton);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("FineDustFragment","버튼 누림!");
                    AddLocationDialogFragment.newInstance(new AddLocationDialogFragment.OnClickListener(){
                    @Override
                    public void onOkclicked(String city) {
                        Log.d("FineDustFragment","ok 버튼 누림!");
                        CCurLocationRepository clr = new CCurLocationRepository(1,1,city.trim());

                        clr.getCurLocationData(new Callback<CurLocation>() { //이거 나중에 presenter로 빼라. (geoutil참고)
                            @Override
                            public void onResponse(Call<CurLocation> call, Response<CurLocation> response) {
                                List<Coordinate> coordinates = response.body().getList(); //입력된 주소로 좌표를 불러온다.(도시명이 이상할 경우 좌표 불러올 수 없다)
                                if(coordinates.size() > 0) {
                                    Log.d("editText좌표변환 결과", response.body().getList().get(0).getTmX() + " " + response.body().getList().get(0).getTmY());
                                    final String inputAddress = coordinates.get(0).getSidoName() + " " + coordinates.get(0).getSggName() + " " + coordinates.get(0).getUmdName();
                                    Log.d("FineDustFra input 주소" , inputAddress);
                                    CStationInfoRepository csr = new CStationInfoRepository(response.body().getList().get(0).getTmX(),
                                            response.body().getList().get(0).getTmY());
                                    csr.getStationInfo(new Callback<StationInfo>() {
                                        @Override
                                        public void onResponse(Call<StationInfo> call, Response<StationInfo> response) {
                                            Log.d("측정소까지의거리in" + mFragmentIndex +"thfragment", "측정소까지의 거리 : " + response.body().getList().get(0).getTm() + "km");
                                            //saveNewcity();
                                            ((MainActivity) getActivity()).addNewFragment(1, 1, response.body().getList().get(0)
                                                    .getStationName(), "DAILY",inputAddress);
                                        }

                                        @Override
                                        public void onFailure(Call<StationInfo> call, Throwable t) {

                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getContext(),"검색할 수 없는 주소입니다.",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<CurLocation> call, Throwable t) {

                            }
                        });
                    }
                }).show(getFragmentManager(),"dialog");

            }
        });

        mListButton = view.findViewById(R.id.station_list);
        mListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("remove할 index", mFragmentIndex+ "");
                ((MainActivity)getActivity()).removeFragment(mFragmentIndex );
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //아래 작업은 아무데서나 해도 상관없다?
        super.onActivityCreated(savedInstanceState);

        if(getArguments() != null) {
            int numOfRows = getArguments().getInt("numOfRows");
            int pageNo = getArguments().getInt("pageNo");
            String stationName = getArguments().getString("stationName");
            String dataTerm = getArguments().getString("dataTerm");
            String curLocation = getArguments().getString("curLocation");
            mFragmentIndex = getArguments().getInt("fragmentIndex");
            Log.d("여기서의 답",mFragmentIndex+"");
            mListButton.setText(mFragmentIndex+"");

            //예외적으로 여기서 표시해준다.
            mCurLocation.setText(curLocation);
            mRepository = new LocationFineDustRepository(numOfRows,pageNo,stationName,dataTerm);
        }else{
            Log.d("FineDustFragment","onActivityCreated넣어");
            mRepository = new LocationFineDustRepository(); //이유있다?
            ((MainActivity)getActivity()).getLastKnownLocation();
            //위치 받아오도록 해라...(getLastKnownLocation) 첫 화면
        }

        mPresenter = new FineDustPresenter(mRepository,this);
        mPresenter.loadFineDustData();

    }
/*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu);
    }*/

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("curLocation",mCurLocation.getText().toString());
        outState.putString("estimateTime",mEstimateTime.getText().toString());

        //emoji도 넣어
        outState.putString("state",mState.getText().toString());
        outState.putString("pm10_state",mPm10State.getText().toString());
        outState.putString("pm2.5_state",mPm25State.getText().toString());
        outState.putString("stationName",mStationName.getText().toString());
        outState.putInt("fragmentIndex",mFragmentIndex);
    }

    @Override
    public void showFineDustResult(FineDust fineDust) {
        //1.로딩이 끝나면 결과를 보여주는 부분.
        //일일 트래픽이 500제한이므로 여기에 대한 예외처리가 필요함.
       // Log.d("이거뭐지",fineDust.getList().get(0).getPm10Value());
        int pm25Value = -1;
        int pm10Value = -1;
        try {
            pm10Value = Integer.parseInt(fineDust.getList().get(0).getPm10Value());
             pm25Value = Integer.parseInt(fineDust.getList().get(0).getPm25Value());

        }catch (NumberFormatException e) {
           Log.e("FineDustFragment","측정소에서 수치가 - 로 측정됨");
        }

        mStationName.setText("(측정소 명 : " + fineDust.getParm().getStationName() + ")");

        mEstimateTime.setText("측정 일시 : " + fineDust.getList().get(0).getDataTime());
        mPm10State.setText("미세먼지 수치 : " + pm10Value +"㎍/㎥ ");
        mPm25State.setText("초미세먼지 수치 : "+pm25Value + "㎍/㎥ ");

        if(pm10Value == -1) {
            mState.setText("측정소 점검중입니다 ㅠㅠ");
            //여기서 말고 retrofit받아올떄 해도 되나?(20190720태인)
        }
        else if(pm10Value <= 30) {
            mScrollView.setBackgroundColor(getResources().getColor(R.color.좋음));
            mState.setText("좋음!");
            mEmoji.setImageResource(R.drawable.laughing);
        } else if(pm10Value <= 50 ) {
            mScrollView.setBackgroundColor(getResources().getColor(R.color.보통));
            mState.setText("보통");
            mEmoji.setImageResource(R.drawable.smiley);
        } else if(pm10Value <= 100 ) {
            mScrollView.setBackgroundColor(getResources().getColor(R.color.나쁨));
            mState.setText("나쁨");
            mEmoji.setImageResource(R.drawable.bad);
        } else if(pm10Value <= 150) {
            mScrollView.setBackgroundColor(getResources().getColor(R.color.매우나쁨));
            mState.setText("매우나쁨");
            mEmoji.setImageResource(R.drawable.crying);
        } else {
            mScrollView.setBackgroundColor(getResources().getColor(R.color.최악));
            mState.setText("최악!");
            mEmoji.setImageResource(R.drawable.gasmask);
        }
    }

    @Override
    public void showLoadError(String message) {
        //
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadingStart() {
        mSwipeRefreshLayout.setRefreshing(true);

    }

    @Override
    public void loadingEnd() {
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void reload(int numOfRows, int pageNo, String stationName, String dataTerm,String umd) {
        //umd는 첫 fragment생성시(현재위치)에만 쓴다.
        mCurLocation.setText(umd);
        mRepository = new LocationFineDustRepository(numOfRows,pageNo,stationName,dataTerm);
        mPresenter = new FineDustPresenter(mRepository,this);
        mPresenter.loadFineDustData();
    }

}
