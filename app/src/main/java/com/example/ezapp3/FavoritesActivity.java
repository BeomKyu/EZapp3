package com.example.ezapp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class FavoritesActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private Context mContext;
    private Button[] favorite_btn = new Button[6];
    private int[] resource = {R.id.favoriteButton1, R.id.favoriteButton2, R.id.favoriteButton3
            ,R.id.favoriteButton4, R.id.favoriteButton5, R.id.favoriteButton6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        for(int i = 0; i < 6; i++) {
            favorite_btn[i] = (Button) findViewById(resource[i]);
            int finalI = i;
            favorite_btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String temp_split_station[] = PreferenceManager
                            .getString(mContext, "favorite"+(finalI+1)).split("\n\n");
                    if(temp_split_station.length > 1) {
                        Intent intent = new Intent(getApplicationContext(),
                                com.example.ezapp3.FindChargingStationActivity.class);
//                    title, zcode, zscode, lat, lng
                        intent.putExtra("zcode", temp_split_station[1]);
                        intent.putExtra("zscode", temp_split_station[2]);
                        intent.putExtra("lat", temp_split_station[3]);
                        intent.putExtra("lng", temp_split_station[4]);
                        intent.putExtra("favoriteBool", true);
                        startActivity(intent);
                    }
                }
            });
            favorite_btn[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
//                    Log.i("myTag", String.valueOf(finalI));
                    delete_favorites_station(mContext, finalI);
                    setText_favorites_station(mContext);
                    return true;
                }
            });
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.favorites_button:
//                        Intent intent1 = new Intent(getApplicationContext(), com.example.ezapp3.FindChargingStationActivity.class);
//                        startActivity(intent1);
                        Toast.makeText(getApplicationContext(), "즐겨찾기 화면 입니다.",
                                Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.home_button:
                        Intent intent2 = new Intent(getApplicationContext(), com.example.ezapp3.MainActivity.class);
                        startActivity(intent2);
//                        Toast.makeText(getApplicationContext(), "홈 화면 입니다.",
//                                Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.profile_button:
                        Intent intent3 = new Intent(getApplicationContext(), com.example.ezapp3.ElectricVehicleTips.class);
                        startActivity(intent3);
//                        Toast.makeText(getApplicationContext(), "정보 화면 입니다.",
//                                Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });

        mContext = this;
//        set_favorites_station(mContext, "something", "4", "5", "6", "7");
//        setText_favorites_station(mContext);

        setText_favorites_station(mContext);
    }


    private void delete_favorites_station(Context mContext, int i) {
        PreferenceManager.setString(mContext, "favorite" + (i+1), "");
    }

    private void set_favorites_station(Context mContext, String title, String zcode, String zscode, String lat, String lng){
        String station = String.format("%s\n\n%s\n\n%s\n\n%s\n\n%s", title, zcode, zscode, lat, lng);
        int i = 0;
        for (i = 1; i < 7; i++) {
            String temp_station = PreferenceManager.getString(mContext, "favorite"+i);
            if(temp_station == ""){
                PreferenceManager.setString(mContext, "favorite" + (i), station);
                return;
            }
        }
        if(i==7)
        Toast.makeText(mContext, "즐겨찾기를 삭제 후 추가해 주세요.", Toast.LENGTH_SHORT).show();
    }


    private void setText_favorites_station(Context context){
        for (int i = 1; i < 7; i++) {
            String station_split[] = PreferenceManager.getString(context, "favorite"+i).split("\n\n");
            if(station_split.length != 0){
                favorite_btn[i-1].setText(station_split[0]);
            }
        }

    }
}