package com.ddochi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ddochi.data.CStationInfoRepository;
import com.ddochi.data.CTmCoordinatesRepository;
import com.ddochi.db.LocationRealmObject;
import com.ddochi.finedust.FineDustContract;
import com.ddochi.finedust.FineDustFragment;
import com.ddochi.model.nearby_station.StationInfo;
import com.ddochi.model.tm_coordinates.Documents;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_FINE_COARSE_PERMISSION = 1000;
    private ViewPager mViewPager;

    private static ArrayList<Pair<Fragment, String>> mFragmentList;
    private FusedLocationProviderClient mFusedLocationClient;

    private Realm mRealm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRealm = Realm.getDefaultInstance();

        setUpViewPager();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    public void saveNewCity(int numOfRows, int pageNo, String stationName, String dataTerm, String curLocation) {
        mRealm.beginTransaction();

        LocationRealmObject newLocationRealmObject =
                mRealm.createObject(LocationRealmObject.class);
        newLocationRealmObject.setNumOfRows(numOfRows);
        newLocationRealmObject.setPageNo(pageNo);
        newLocationRealmObject.setStationName(stationName);
        newLocationRealmObject.setDataTerm(dataTerm);
        newLocationRealmObject.setCurLocation(curLocation);

        mRealm.commitTransaction();
    }

    private void setUpViewPager() {
        mViewPager = findViewById(R.id.vp);

        loadDbData();


        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(adapter);


    }

    public void addNewFragment(int numOfRows, int pageNo, String stationName, String dataTerm, String curLocation) {
        saveNewCity(numOfRows, pageNo, stationName, dataTerm, curLocation);
        mFragmentList.add(new Pair<Fragment, String>(FineDustFragment.newInstance(numOfRows, pageNo, stationName
                , dataTerm, curLocation, mFragmentList.size()), "현위치"));
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    public void removeFragment(int index) {
        if (index != 0) {
            mRealm.beginTransaction();
            mRealm.where(LocationRealmObject.class).findAll().get(index - 1).deleteFromRealm();
            mRealm.commitTransaction();
        }
        int tSize = mFragmentList.size();

        for (int i = tSize - 1; i >= 0; i--) {
            mFragmentList.remove(i);
        }

        //mViewPager.getAdapter().notifyDataSetChanged();
        setUpViewPager();

    }

    private void loadDbData() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new Pair<Fragment, String>(new FineDustFragment(), "현재위치"));

        RealmResults<LocationRealmObject> realmResults =
                mRealm.where(LocationRealmObject.class).findAll();
        for (LocationRealmObject realmObject : realmResults) {
            mFragmentList.add(new Pair<Fragment, String>(
                    FineDustFragment.newInstance(realmObject.getNumOfRows(), realmObject.getPageNo(),
                            realmObject.getStationName(), realmObject.getDataTerm(), realmObject.getCurLocation(), mFragmentList.size()),
                    "현재위치"
            ));
        }
    }

    public void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            //권한 요청
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_FINE_COARSE_PERMISSION);
            return;
        }

        mFusedLocationClient.getLastLocation().
                addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            //위도 경도로 받아오므로 tm좌표로 변경하는 작업이 필요.

                            //final String tmX; //위도경도를 tm좌표로 바꿔서 이 tm좌표로 nearBy측정소 정보 얻어올것.
                            //final String tmY;
                            Log.d("MainActivity", "위도 : " + location.getLatitude() + "경도 : " +
                                    location.getLongitude());
                            //////////////////////////////


                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.KOREAN);
                            String umd = "";

                            try {
                                List<Address> resultList = geocoder.getFromLocation(location.getLatitude(),
                                        location.getLongitude(), 1);


                                Log.d("전체주소", resultList.toString());

                                Log.d("Mainactivity",
                                        "\ngetAddressLine() " + resultList.get(0).getAddressLine(0) +
                                                "\ngetAdminArea() :" + resultList.get(0).getAdminArea() +
                                                "\ngetLocality() :" + resultList.get(0).getLocality() +
                                                "\ngetSubLocality" + resultList.get(0).getSubLocality() +
                                                "\ngetTruoghfare" + resultList.get(0).getThoroughfare() +
                                                "\ngetSubThrough" + resultList.get(0).getSubThoroughfare());
                                Address addr = resultList.get(0);
                                if (addr.getLocality() != null)
                                    umd += addr.getLocality() + " ";
                                if (addr.getSubLocality() != null)
                                    umd += addr.getSubLocality() + " ";
                                if (addr.getThoroughfare() != null)
                                    umd += addr.getThoroughfare();
                                Log.d("결과 주소", umd);
                                final String umdAdress = umd;
                                String latitude = location.getLatitude() + "";
                                String longitude = location.getLongitude() + "";
                                CTmCoordinatesRepository ctr = new CTmCoordinatesRepository(longitude.trim(), latitude.trim());
                                if (ctr.isAvailable()) {
                                    ctr.getTmCoordinatesDocuments(new Callback<Documents>() {
                                        @Override
                                        public void onResponse(Call<Documents> call, Response<Documents> response) {


                                            Log.d("좌표변환 결과", response.body().getDocuments().get(0).getX() + " " + response.body().getDocuments().get(0).getY());
                                            CStationInfoRepository csr = new CStationInfoRepository(response.body().getDocuments().get(0).getX(),
                                                    response.body().getDocuments().get(0).getY());
                                            csr.getStationInfo(new Callback<StationInfo>() {
                                                @Override
                                                public void onResponse(Call<StationInfo> call, Response<StationInfo> response) {
                                                    Log.d("측정소까지의 거리", "측정소까지의 거리 : " + response.body().getList().get(0).getTm() + "km");
                                                    FineDustContract.View view = (FineDustContract.View) mFragmentList.get(0).first;
                                                    view.reload(10, 1, response.body().getList().get(0).getStationName(), "DAILY", umdAdress);
                                                }

                                                @Override
                                                public void onFailure(Call<StationInfo> call, Throwable t) {
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(Call<Documents> call, Throwable t) {
                                            Log.e("MainActivity", "tm좌표 변환 실패");

                                        }
                                    });

                                } else {
                                    //필요한가? 아래 catch문에서 처리?
                                    Log.e("MainActivity", "남한 영토를 벗어났습니다.");
                                }


                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d("MainActivity", "주소변환실패");
                            }

                    /*FineDustContract.View view = (FineDustContract.View) mFragmentList.get(0).first;
                    view.reload(10, 1, "종로구", "DAILY");*/
                        }
                    }
                });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_FINE_COARSE_PERMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getLastKnownLocation();
            }
        }
    }


    /*
    View.OnClickListener movePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int tag = (int) view.getTag();
            vp.setCurrentItem(tag);
        }
    };
*/


    public static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Pair<Fragment, String>> mFragmentList;
        //android에서 추가된 Pair, 현재 위치말고도 탭에 위치가 표시되야 하기때문에 이렇게 선언한다.(내거에선 Pair안써도될듯?)


        public MyPagerAdapter(FragmentManager fm, List<Pair<Fragment, String>> fragmentList) {
            super(fm);
            mFragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position).first;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            //이것도 필요없을듯...(탭레이아웃위해 만든것)
            return mFragmentList.get(position).second;
        }
    }
}
