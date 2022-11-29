package com.example.ezapp3;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.w3c.dom.Text;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity context;

    public CustomInfoWindowAdapter(Activity context){
        this.context = context;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = context.getLayoutInflater().inflate(R.layout.dummy_layout,
                null);

//        String infomationString = marker.getSnippet();
//        String[] infomationSplit = infomationString.split("\n\n");
//
//        Button depart_btn = (Button) view.findViewById(R.id.depart_button);
//        Button via_btn = (Button) view.findViewById(R.id.via_button);
//        Button arrival_btn = (Button) view.findViewById(R.id.arrival_button);
//
//        Button favorite_btn = (Button) view.findViewById(R.id.favorite_button);
//        Button share_btn = (Button) view.findViewById(R.id.share_button);
//        Button edit_btn = (Button) view.findViewById(R.id.edit_button);
//
//        TextView station_address = (TextView) view.findViewById(R.id.station_address);
//        TextView use_time = (TextView) view.findViewById(R.id.use_time);
//        TextView chager_type = (TextView) view.findViewById(R.id.chager_type);
//        TextView chager_Stat = (TextView)view.findViewById(R.id.chager_stat);
//        TextView tvTitle = (TextView) view.findViewById(R.id.title);
//
//        station_address.setText(infomationSplit[2]);
//        use_time.setText(infomationSplit[5]);
//        chager_type.setText(infomationSplit[1]);
//        chager_Stat.setText(infomationSplit[6]);
//
//        tvTitle.setText(marker.getTitle());

//        depart_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context.getApplicationContext(), "출발버튼", Toast.LENGTH_SHORT).show();
//            }
//        });
//        via_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context.getApplicationContext(), "경유버튼", Toast.LENGTH_SHORT).show();
//            }
//        });
//        arrival_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context.getApplicationContext(), "도착버튼", Toast.LENGTH_SHORT).show();
//            }
//        });
//        favorite_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context.getApplicationContext(), "즐찾버튼", Toast.LENGTH_SHORT).show();
//            }
//        });
//        share_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context.getApplicationContext(), "공유버튼", Toast.LENGTH_SHORT).show();
//            }
//        });
//        edit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context.getApplicationContext(), "제보버튼", Toast.LENGTH_SHORT).show();
//            }
//        });
        return view;
    }

}
