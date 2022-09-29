package com.example.ezapp3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton myFindChargingStation =(ImageButton) findViewById(R.id.find_charging_station_button);
        Button myElectricVehicleTips1 = (Button) findViewById(R.id.electirc_vehicle_tips_button1);
        ImageButton myElectricVehicleTips2 = (ImageButton) findViewById(R.id.electirc_vehicle_tips_button2);
        ImageButton myHome =(ImageButton) findViewById(R.id.home_button);
        ImageButton myfavorites =(ImageButton) findViewById(R.id.favorites_button);
        ImageButton myprofile =(ImageButton) findViewById(R.id.profile_button);

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
                Intent intent = new Intent(getApplicationContext(), ElectricVehicleTips.class);
                startActivity(intent);
            }
        });
    }
}