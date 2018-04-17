package com.example.acer.myapplication.java_class;

/**
 * Created by asus on 2018/4/11.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.acer.myapplication.R;
import java.util.List;




public class ItemAdapter extends ArrayAdapter<Item> {

        private int resourceId;
        //构造方法
        public ItemAdapter(Context context, int textViewResourceId,
                           List<Item> objects) {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }
        //
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Item item = getItem(position); // 获取当前项的Fruit实例
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
                viewHolder = new ViewHolder();
                //获取所有组件
                viewHolder.link_image = (ImageView) view.findViewById (R.id.link_image);
                viewHolder.link_name = (TextView) view.findViewById (R.id.link_name);
                viewHolder.link_time=(TextView) view.findViewById(R.id.link_time);
                viewHolder.link_package=(TextView) view.findViewById(R.id.link_package);

                view.setTag(viewHolder); // 将ViewHolder存储在View中
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
            }
            //设置数据到组件中
            viewHolder.link_image.setImageResource(item.getLink_image());
            viewHolder.link_name.setText(item.getLink_name());
            viewHolder.link_time.setText(item.getLink_time());
            viewHolder.link_package.setText(item.getLink_package());

            return view;
        }
        //定义布局文件里组件
        class ViewHolder {

            ImageView link_image;

            TextView link_name;

            TextView link_time;

            TextView link_package;



        }
    }


