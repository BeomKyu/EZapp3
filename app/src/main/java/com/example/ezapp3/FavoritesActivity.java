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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class FavoritesActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private Context mContext;
    private Button[] favorite_btn = new Button[6];
    private int[] resource = {R.id.favoriteButton1, R.id.favoriteButton2, R.id.favoriteButton3
            ,R.id.favoriteButton4, R.id.favoriteButton5, R.id.favoriteButton6};
//    private Button favorite_btn2;
//    private Button favorite_btn3;
//    private Button favorite_btn4;
//    private Button favorite_btn5;
//    private Button favorite_btn6;

    private String text[] = new String[6];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        for(int i = 0; i < 6; i++) {
            favorite_btn[i] = (Button) findViewById(resource[i]);
            favorite_btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "111",
                            Toast.LENGTH_SHORT).show();

                    Log.i("myTag", "test2");
                }
            });
            favorite_btn[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(getApplicationContext(), "222",
                            Toast.LENGTH_SHORT).show();
                    Log.i("myTag", "test");
                    return true;
                }
            });
        }


//        for(int i = 0; i < favorite_btn.length; i++){
//            favorite_btn[i].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(getApplicationContext(), "toas1t", Toast.LENGTH_SHORT);
//
//                }
//            });
//            int temp_i = i;
//            favorite_btn[i].setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    delete_favorites_station(mContext, temp_i);
//                    Toast.makeText(getApplicationContext(), "toast", Toast.LENGTH_SHORT);
//                    return false;
//                }
//            });
//        }

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
//        PreferenceManager.setString(mContext, "favorite1", "숲속의 작은 이야기");

        get_favorites_station(mContext);
        setText_favorites_station(mContext);
//        PreferenceManager.setString(mContext, "favorite2", "숲속의 작은 이야기");
    }


    private void delete_favorites_station(Context mContext, int i) {
        PreferenceManager.setString(mContext, "favorite" + i, "");
    }


    private void setText_favorites_station(Context context){
        for (int i = 1; i < 7; i++) {
            if(text[i-1].equals("")){
//                favorite_btn[i-1].setText(String.valueOf(i));
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