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
        ViewHolder viewHolder;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.history_value=(TextView)view.findViewById(R.id.history_value);
            viewHolder.history_time=(TextView)view.findViewById(R.id.history_time);
            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }


        // TextView value_text=view.findViewById(R.id.history_value);
        //TextView time_text=view.findViewById(R.id.history_time);


        viewHolder.history_value.setText(historyBean.getValue());
        viewHolder.history_time.setText(historyBean.getTime());
        return view;

    }
    //ViewHolder类
    class ViewHolder{
        TextView history_value;
        TextView history_time;

    }
}
