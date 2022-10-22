package com.example.ezapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class APISempleActivity extends AppCompatActivity {

    TextView text;
    Button button;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apisemple);

        text = findViewById(R.id.result);
        button = findViewById(R.id.button);

        APITask APIData = new APITask();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        try {
                            data = APIData.getAPIData();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                text.setText(data); //TextView에 문자열  data 출력
                            }
                        });
                    }
                }).start();
            }
        });

    }
}