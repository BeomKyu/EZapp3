package com.example.ezapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

public class APISempleActivity extends AppCompatActivity {

    Button button;
    String data;

    String datas;
    String placList[];
    String place[];
    ListView listView;
    PlaceItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apisemple);


        button = findViewById(R.id.button);
        listView = findViewById(R.id.listview);
        adapter = new PlaceItemAdapter();

        APITask APIData = new APITask();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        try {
                            datas = APIData.getAPIData();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                placList = datas.split("\n\n\n");

                                for(int i = 0; i < placList.length; i++){
                                    place = placList[i].split("\n\n");
                                    try {
                                        adapter.addItem(new PlaceItem(place[0],place[1],place[2],place[3],place[4],place[5]));
                                    }catch (Exception e){
                                        Log.i("MyTag", e.getMessage());
                                    }
                                }

                                listView.setAdapter(adapter);
                            }
                        });
                    }
                }).start();
            }
        });

    }
}