package com.example.ezapp3;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.gms.maps.model.LatLng;

public class CustomDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getContext();
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        String[] informationSplit = bundle.getString("info").split("\n\n");
        String lat = bundle.getString("myPlaceLat");
        String lng = bundle.getString("myPlaceLng");
//        Log.i("myTag", lat +" " + lng);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_info_contents, null);

        builder.setView(view);

        TextView tvNumber = (TextView) view.findViewById(R.id.call_number);
        TextView tvWhere = (TextView) view.findViewById(R.id.where);
        TextView tvTime = (TextView) view.findViewById(R.id.time);
        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        TextView tvBnm = (TextView) view.findViewById(R.id.bnm);
        TextView tvBusiNm = (TextView) view.findViewById(R.id.busiNm);
        TextView tvBusiCall = (TextView) view.findViewById(R.id.busiCall);
        TextView tvMethod = (TextView) view.findViewById(R.id.method);
        TextView tvNote = (TextView) view.findViewById(R.id.note);
        TextView tvLimitYn = (TextView) view.findViewById(R.id.limitYn);
        TextView tvLimitDetail = (TextView) view.findViewById(R.id.limitDetail);
        TextView tvStatUpdDt = (TextView) view.findViewById(R.id.statUpdDt);


        Button favorite_button = (Button) view.findViewById(R.id.favorite_button);
        Button arrival_button = (Button) view.findViewById(R.id.arrival_button);

        favorite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                title, zcode, zscode, lat, lng
                String station = String.format("%s\n\n%s\n\n%s\n\n%s\n\n%s", title, informationSplit[12],
                        informationSplit[13], informationSplit[3], informationSplit[4]);
                Log.i("myTag", informationSplit[3] + informationSplit[4]);
                int i = 0;
                for (i = 1; i < 7; i++) {
                    String temp_station = PreferenceManager.getString(getActivity(), "favorite"+i);
                    if(temp_station == ""){
                        PreferenceManager.setString(getActivity(), "favorite" + (i), station);
                        return;
                    }
                }
                Toast.makeText(context, "즐겨찾기를 삭제 후 추가해 주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        arrival_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("kakaomap://route?sp="+lat+","+lng+"&ep="+informationSplit[3]+","+informationSplit[4]+"&by=CAR")));
                }catch (android.content.ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=net.daum.android.map")));
                }
            }
        });
        tvTitle.setText(title);
        tvNumber.setText(informationSplit[0]);
        tvTime.setText(informationSplit[1]);
        tvWhere.setText(informationSplit[2]);
        tvBnm.setText(informationSplit[6]);//기관명
        tvBusiNm.setText(informationSplit[7]);//운영기관명
        tvBusiCall.setText(informationSplit[8]);//운영기관 연락처
        if(informationSplit[11].substring(7).equals("null"))
            tvMethod.setVisibility(GONE);
        else {
            tvMethod.setVisibility(VISIBLE);
            tvMethod.setText(informationSplit[11]);//충전 방식
        }
        if(informationSplit[14].substring(9).equals("null"))
            tvNote.setVisibility(GONE);
        else {
            tvNote.setVisibility(VISIBLE);
            tvNote.setText(informationSplit[14]);//충전소 안내
        }
        if(informationSplit[15].substring(9).equals("Y")) {
            tvLimitYn.setText("이용자 제한 : 있음");
            tvLimitDetail.setVisibility(VISIBLE);
            tvLimitDetail.setText(informationSplit[16]);
        }
        else {
            tvLimitYn.setText("이용자 제한 : 없음");
            tvLimitDetail.setVisibility(GONE);
        }
        System.out.println(informationSplit[10]);
        tvStatUpdDt.setText(
                informationSplit[10].substring(0, 9) +
                informationSplit[10].substring(9, 13) + "/" +
                informationSplit[10].substring(13, 15) + "/" +
                informationSplit[10].substring(15, 17) + " " +
                informationSplit[10].substring(17, 19) + ":" +
                informationSplit[10].substring(19, 21) + ":" +
                informationSplit[10].substring(21));//상태 갱신 일시


        return builder.create();
    }
}
