package com.example.ezapp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PlaceItemAdapter extends BaseAdapter {
    ArrayList<PlaceItem> items = new ArrayList<PlaceItem>();
    Context context;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int posotion, View convertView, ViewGroup viewGroup) {
        context =  viewGroup.getContext();
        PlaceItem placeItem = items.get(posotion);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem, viewGroup, false);
        }

        TextView stanameTv = convertView.findViewById(R.id.staname);
        TextView chgerTypeTv = convertView.findViewById(R.id.chgerType);
        TextView addrTv = convertView.findViewById(R.id.addr);
        TextView useTimeTv = convertView.findViewById(R.id.useTime);
        TextView statTv = convertView.findViewById(R.id.stat);
        TextView statUpdDtTv = convertView.findViewById(R.id.statUpdDt);

        stanameTv.setText(placeItem.getStatNm());
        chgerTypeTv.setText(placeItem.getChgerType());
        addrTv.setText(placeItem.getAddr());
        useTimeTv.setText(placeItem.getUseTime());
        statTv.setText(placeItem.getStat());
        statUpdDtTv.setText(placeItem.getStatUpdDt());

        return convertView;
    }

    public void addItem(PlaceItem item){
        items.add(item);
    }
}
