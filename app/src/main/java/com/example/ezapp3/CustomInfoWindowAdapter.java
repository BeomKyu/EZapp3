package com.example.ezapp3;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

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
        View view = context.getLayoutInflater().inflate(R.layout.custom_info_contents,
                null);

        String infomationString = marker.getSnippet();
        String[] infomationSplit = infomationString.split("\n\n");

        TextView tvNumber = (TextView) view.findViewById(R.id.call_number);
        TextView tvFee = (TextView) view.findViewById(R.id.fee);
        TextView tvWhere = (TextView) view.findViewById(R.id.where);
        TextView tvTime = (TextView) view.findViewById(R.id.time);

        TextView tvTitle = (TextView) view.findViewById(R.id.title);
//        TextView tvSubTitle = (TextView) view.findViewById(R.id.snippet);

        tvTitle.setText(marker.getTitle());
        tvNumber.setText(infomationSplit[0]);
        tvFee.setText(infomationSplit[3]);
        tvWhere.setText(infomationSplit[2]);
        tvTime.setText(infomationSplit[1]);
//        tvSubTitle.setText(marker.getSnippet());

        return view;
    }
}
