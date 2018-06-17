package com.example.acer.collectionplus.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.acer.collectionplus.JavaBean.HistoryBean;
import com.example.acer.collectionplus.R;

import java.util.List;

/**
 * Created by asus on 2018/6/17.
 */
//浏览历史列表
public class HistoryAdapter extends ArrayAdapter<HistoryBean> {
private int resourceId;

    public HistoryAdapter(@NonNull Context context,int textViewResourceId, @NonNull List<HistoryBean> objects) {
        super(context,textViewResourceId, objects);
        this.resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        HistoryBean historyBean=getItem(position);
        View view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

        TextView value_text=view.findViewById(R.id.history_value);
        TextView time_text=view.findViewById(R.id.history_time);


        value_text.setText(historyBean.getValue());
        time_text.setText(historyBean.getTime());
        return view;

    }
}
