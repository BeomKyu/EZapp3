package com.example.ezapp3;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton myFindChargingStation =(ImageButton) findViewById(R.id.find_charging_station_button);
        ImageButton myElectricVehicleTips1 =(ImageButton) findViewById(R.id.electirc_vehicle_tips_button1);
//        ImageButton myElectricVehicleTips2 =(ImageButton) findViewById(R.id.electirc_vehicle_tips_button2);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.favorites_button:
                        Intent intent1 = new Intent(getApplicationContext(), com.example.ezapp3.FavoritesActivity.class);
                        startActivity(intent1);
//                        Toast.makeText(getApplicationContext(), "favorites",
//                                Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.home_button:
//                        Intent intent2 = new Intent(getApplicationContext(), com.example.ezapp3.MainActivity.class);
//                        startActivity(intent2);
                        Toast.makeText(getApplicationContext(), "홈 화면 입니다.",
                                Toast.LENGTH_SHORT).show();
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
//        ImageButton myHome =(ImageButton) findViewById(R.id.home_button);
//        ImageButton myfavorites =(ImageButton) findViewById(R.id.favorites_button);
//        ImageButton myprofile =(ImageButton) findViewById(R.id.profile_button);

        myFindChargingStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), com.example.ezapp3.FindChargingStationActivity.class);
                startActivity(intent);
            }
        });

        myElectricVehicleTips1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), com.example.ezapp3.ElectricVehicleTips.class);
                startActivity(intent);
            }
        });
    }
}