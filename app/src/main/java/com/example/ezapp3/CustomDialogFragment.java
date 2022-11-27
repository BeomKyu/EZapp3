package com.example.ezapp3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
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

        tvTitle.setText(title);
        tvNumber.setText(informationSplit[0]);
        tvFee.setText(informationSplit[3]);
        tvWhere.setText(informationSplit[2]);
        tvTime.setText(informationSplit[1]);

        return builder.create();
    }
}
