package com.example.ezapp3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getContext();
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        String[] informationSplit = bundle.getString("info").split("\n\n");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_info_contents, null);

        builder.setView(view);

        TextView tvNumber = (TextView) view.findViewById(R.id.call_number);
        TextView tvFee = (TextView) view.findViewById(R.id.fee);
        TextView tvWhere = (TextView) view.findViewById(R.id.where);
        TextView tvTime = (TextView) view.findViewById(R.id.time);
        TextView tvTitle = (TextView) view.findViewById(R.id.title);

        Button favorite_button = (Button) view.findViewById(R.id.favorite_button);
        Button arrival_button = (Button) view.findViewById(R.id.arrival_button);

        favorite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                title, zcode, zscode, lat, lng
                String station = String.format("%s\n\n%s\n\n%s\n\n%s\n\n%s", title, informationSplit[9],
                        informationSplit[10], informationSplit[3], informationSplit[4]);
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

        tvTitle.setText(title);
        tvNumber.setText(informationSplit[0]);
        tvFee.setText(informationSplit[3]);
        tvWhere.setText(informationSplit[2]);
        tvTime.setText(informationSplit[1]);

        return builder.create();
    }
}
