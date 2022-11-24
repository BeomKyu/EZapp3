package com.example.ezapp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private Context mContext;
    private Button[] favorite_btn = new Button[6];
    private Button favorite_btn2;
    private Button favorite_btn3;
    private Button favorite_btn4;
    private Button favorite_btn5;
    private Button favorite_btn6;

    private String text[] = new String[6];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        favorite_btn[0] = findViewById(R.id.favoriteButton1);
        favorite_btn[1] = findViewById(R.id.favoriteButton2);
        favorite_btn[2] = findViewById(R.id.favoriteButton3);
        favorite_btn[3] = findViewById(R.id.favoriteButton4);
        favorite_btn[4] = findViewById(R.id.favoriteButton5);
        favorite_btn[5] = findViewById(R.id.favoriteButton6);
//        favorite_btn2 = findViewById(R.id.favoriteButton2);
//        favorite_btn3 = findViewById(R.id.favoriteButton3);
//        favorite_btn4 = findViewById(R.id.favoriteButton4);
//        favorite_btn5 = findViewById(R.id.favoriteButton5);
//        favorite_btn6 = findViewById(R.id.favoriteButton6);

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
        PreferenceManager.setString(mContext, "favorite1", "숲속의 작은 이야기");

        get_favorites_station(mContext);
        checked_favorites_station(mContext);
    }

    private void checked_favorites_station(Context context){
        for (int i = 1; i < 7; i++) {
            if(text[i-1].equals("")){
                favorite_btn[i-1].setText(String.valueOf(i));
            }
            else{
                favorite_btn[i-1].setText(text[i-1]);
            }
        }
        //            PreferenceManager.setString(mContext, "rebuild", "숲속의 작은 이야기");


    }

    private void get_favorites_station(Context context) {
        for (int i = 1; i < 7; i++) {
            text[i-1] = PreferenceManager.getString(context, "favorite" + i);
//            Log.i("myTag", "text" + i + text[i-1]);
        }
    }
}