package com.example.ezapp3;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ElectricVehicleTips extends AppCompatActivity {
    private int currentPage;
    private int numOfPage;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric_vehicle_tips);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.favorites_button:
                        Intent intent1 = new Intent(getApplicationContext(), com.example.ezapp3.FavoritesActivity.class);
                        startActivity(intent1);
                        Toast.makeText(getApplicationContext(), "favorites",
                                Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.home_button:
                        Intent intent2 = new Intent(getApplicationContext(), com.example.ezapp3.MainActivity.class);
                        startActivity(intent2);
//                        Toast.makeText(getApplicationContext(), "홈 화면 입니다.",
//                                Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.profile_button:
//                        Intent intent3 = new Intent(getApplicationContext(), com.example.ezapp3.ElectricVehicleTips.class);
//                        startActivity(intent3);
                        Toast.makeText(getApplicationContext(), "정보 화면 입니다.",
                                Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float density = displayMetrics.density;

        String[] allTips = getResources().getStringArray(R.array.tips);
        String[] allUrls = getResources().getStringArray(R.array.urls);
        String[] urls = {allUrls[0], allUrls[1], allUrls[2], allUrls[3]};
        String[] tips = {allTips[0], allTips[1], allTips[2], allTips[3]};

        LinearLayout[] tipLayout = {
                (LinearLayout) findViewById(R.id.tip_layout1),
                (LinearLayout) findViewById(R.id.tip_layout2),
                (LinearLayout) findViewById(R.id.tip_layout3),
                (LinearLayout) findViewById(R.id.tip_layout4)};

        TextView[] tipText = {
                (TextView) findViewById(R.id.electirc_vehicle_tip1),
                (TextView) findViewById(R.id.electirc_vehicle_tip2),
                (TextView) findViewById(R.id.electirc_vehicle_tip3),
                (TextView) findViewById(R.id.electirc_vehicle_tip4)};

        ImageView[] tipImage = {
                (ImageView) findViewById(R.id.tipimage1),
                (ImageView) findViewById(R.id.tipimage2),
                (ImageView) findViewById(R.id.tipimage3),
                (ImageView) findViewById(R.id.tipimage4)
        };

        for(int i=0; i<4; i++){
            tipText[i].setText(tips[i]);
            tipLayout[i].setTag(i);
            tipLayout[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = (Integer)view.getTag();
                    Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls[i]));
                    startActivity(urlintent);
                }
            });
        }

        //하단 페이지 버튼
        LinearLayout pageButtonLayout =(LinearLayout) findViewById(R.id.tip_page_button_layout);
        numOfPage = (allTips.length-1)/4+1;
        currentPage = 0;
        if(numOfPage<=1) pageButtonLayout.setVisibility(View.INVISIBLE);
        else{
            pageButtonLayout.setVisibility(View.VISIBLE);
            Button[] pageButton = new Button[numOfPage];

            LinearLayout.LayoutParams buttonlp = new LinearLayout.LayoutParams((int)(40*density+0.5), (int)(40*density+0.5));
            buttonlp.leftMargin = (int)(8*density+0.5);
            buttonlp.rightMargin = (int)(8*density+0.5);

            View.OnClickListener pageButtonListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = (Integer)view.getTag();
                    int changedPage = currentPage;
                    if(i == -1){//left button
                        if(currentPage > 0){
                            changedPage -= 1;
                        }
                    }
                    else if(i == -2){//right button
                        if(currentPage < (numOfPage - 1)){
                            changedPage += 1;
                        }
                    }
                    else{
                        changedPage = i;
                    }
                    if(changedPage != currentPage) {
                        for (int j = 0; j < 4; j++) {
                            if (changedPage * 4 + j >= allTips.length)
                                tipLayout[j].setVisibility(View.INVISIBLE);
                            else {
                                tipLayout[j].setVisibility(View.VISIBLE);
                                tips[j] = allTips[changedPage * 4 + j];
                                urls[j] = allUrls[changedPage * 4 + j];
                                tipImage[j].setImageResource(getResources().getIdentifier(
                                        "tipimage" + (changedPage * 4 + j + 1), "drawable", getApplicationContext().getPackageName()));
                                tipText[j].setText(tips[j]);
                            }
                        }
                        pageButton[currentPage].setBackgroundColor(Color.parseColor("#227106"));
                        pageButton[changedPage].setBackgroundColor(Color.parseColor("#5FC895"));
                        currentPage = changedPage;
                    }
                }
            };

            Button leftPageButton = new Button(this);
            leftPageButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_sharp_arrow_left_24, null));
            pageButtonLayout.addView(leftPageButton);
            leftPageButton.setTag(-1);
            leftPageButton.setOnClickListener(pageButtonListener);

            for(int i=0; i<numOfPage; i++){
                pageButton[i] = new Button(this);
                pageButton[i].setBackgroundColor(Color.parseColor("#227106"));
                pageButtonLayout.addView(pageButton[i], buttonlp);
                pageButton[i].setTag(i);
                pageButton[i].setOnClickListener(pageButtonListener);
            }
            pageButton[currentPage].setBackgroundColor(Color.parseColor("#5FC895"));

            Button rightPageButton = new Button(this);
            rightPageButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_sharp_arrow_right_24, null));
            pageButtonLayout.addView(rightPageButton);
            rightPageButton.setTag(-2);
            rightPageButton.setOnClickListener(pageButtonListener);
        }
    }
}