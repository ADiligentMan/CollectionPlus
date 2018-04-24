package com.example.acer.myapplication.java_class;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.acer.myapplication.R;

import java.util.List;


/**
 * Created by zhuhongmeng on 2018/4/16.
 */

public class ItemAdapter extends ArrayAdapter implements View.OnClickListener{
    private int resourceId;
    private Context context;
    final private List<Item> list;
    public int clickPosition = -1;


    public ItemAdapter(Context context, int resourceId, List<Item> list){
        super(context, resourceId, list);
        this.context = context;
        this.resourceId = resourceId;
        this.list = list;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //获取当前item实例
        //Item item = list.get(position);
        final Item item= (Item) getItem(position);

        final ItemAdapter.MyViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.item, null);
            vh = new ItemAdapter.MyViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ItemAdapter.MyViewHolder) convertView.getTag();
        }

        //刷新adapter的时候 getView重新执行 此时对在点击中标记的position做处理
        //当条目是刚才点击的条目时
        //当条目状态图标为选中时说明该条目处于展开状态 此时让它隐藏并切换状态图标的状态
        if (clickPosition == position)
            if (vh.arrow.isSelected()){
                vh.arrow.setSelected(false);
                vh.link_title.setVisibility(View.VISIBLE);
                vh.link_time.setVisibility(View.VISIBLE);
                vh.link_package.setVisibility(View.VISIBLE);
                vh.link_image.setVisibility(View.VISIBLE);
                vh.arrow.setImageResource(R.drawable.arrowright);
                vh.ll_hide.setVisibility(View.GONE);
                Log.e("listView", "if执行");
                //隐藏布局后需要把标记的position去除掉
                //否则，滑动listView让该条目划出屏幕范围
                clickPosition = -1;
                //当该条目重新进入屏幕后，会重新恢复原来的显示状态。经过打log可知每次else都执行一次
                //条目第二次进入屏幕时会在getView中寻找自己的状态，相当于重新执行一次getView
                //因为每次滑动的时候没标记的position填充会执行click
            } else {
                //当状态条目处于未选中时说明条目处于未展开状态 此时展开同时切换状态图标的状态
                vh.arrow.setSelected(true);
                vh.arrow.setImageResource(R.drawable.arrowdown);
                vh.link_image.setVisibility(View.GONE);
                vh.link_package.setVisibility(View.GONE);
                vh.link_title.setVisibility(View.GONE);
                vh.link_time.setVisibility(View.GONE);
                vh.ll_hide.setVisibility(View.VISIBLE);
                Log.e("listView", "else执行");

            }
        else{
            //当填充的条目position不是刚才点击所标记的position时 让其隐藏
            vh.ll_hide.setVisibility(View.GONE);
            vh.arrow.setSelected(false);
            vh.link_title.setVisibility(View.VISIBLE);
            vh.link_time.setVisibility(View.VISIBLE);
            vh.link_package.setVisibility(View.VISIBLE);
            vh.link_image.setVisibility(View.VISIBLE);
            vh.arrow.setImageResource(R.drawable.arrowright);
            Log.e("listView", "状态改变");
                    /*每次滑动的时候没标记得position填充会执行此处，把状态改变。
                    所以如果在以上的if (vh.arrow.isSelected()) {}中不设置clickPosition=-1；
                    则条目再次进入屏幕后，还是会进入clickPosition==position的逻辑中
                    而之前的滑动（未标记条目的填充）时，执行此处逻辑，已经把状态图片的selected置为false
                    所以上面的else中的逻辑会在标记过的条目第二次进入屏幕时执行
                    如果之前的状态是显示，则无影响；如果是隐藏状态，会被重新显示出来* */
        }

        //设置数据到组件中
//        vh.link_image.setImageURI(item.getLink_image());///?
        vh.link_title.setText(item.getTitle());
        vh.link_time.setText(item.getTime());
        vh.link_package.setText("Java");
//        vh.link_title.setOnClickListener(this);
        //vh.hide_1.setOnClickListener(this);
        vh.hide_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked){
                if(isChecked){
                    vh.hide_1.setText("已读");
                    //此处应有信息栏item的背景修改
                }
                else{
                    vh.hide_1.setText("未读");
                }
            }
        });

        vh.hide_2.setOnClickListener(this);
        vh.hide_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "删除", Toast.LENGTH_SHORT).show();

                //Log.e("list?", String.valueOf(list));
                if(list.size()>0){
                    list.remove(position);
                }
                notifyDataSetChanged();
            }
        });
        vh.hide_4.setOnClickListener(this);
        vh.hide_5.setOnClickListener(this);
        vh.arrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                clickPosition = position;//记录点击的position

                //刷新adapter重新填充条目。在重新填充的过程中，被记录的position会做展开或隐藏的动作
                notifyDataSetChanged();
                        /*当adapter执行刷新操作时，整个getView方法会重新执行，也就是条目重新初始化被填充数据
                        所以标记position，不会对条目产生影响
                        执行刷新后条目重新填充。当填充至所标记的position时，对其处理，达到展开和隐藏的目的
                        每次点击代码执行逻辑是onclick()-->getView()
                        * */
            }
        });
        return convertView;
    }

    //点击操作项图标
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.link_url:
//                Toast.makeText(getContext(), "跳转链接", Toast.LENGTH_SHORT).show();
//                //转链接跳转
//                break;

            case R.id.hide_2:
                Toast.makeText(getContext(), "编辑", Toast.LENGTH_SHORT).show();
                //转收藏夹编辑
                //...
                break;

            case R.id.hide_4:
                Toast.makeText(getContext(), "分享", Toast.LENGTH_SHORT).show();
                //转分享
                //...
                break;

            case R.id.hide_5:
                Toast.makeText(getContext(), "定时", Toast.LENGTH_SHORT).show();
                //调用
                SetAlarm setAlarm=new SetAlarm(getContext());
                setAlarm.showDialogPick();
                break;

        }
    }

    class MyViewHolder {
        View itemView;
        //TextView tv_test;
        ImageView hide_2, hide_3, hide_4, hide_5,link_image,arrow;
        TextView link_title,link_time,link_package;
        LinearLayout ll_hide;
        CheckBox hide_1;

        public MyViewHolder(View itemView) {

            this.itemView = itemView;
            //tv_test = (TextView) itemView.findViewById(R.id.tv_test);
            //获取所有组件
            link_image = (ImageView) itemView.findViewById (R.id.link_image);
            link_title= (TextView) itemView.findViewById (R.id.link_url);
            link_time=(TextView) itemView.findViewById(R.id.link_time);
            link_package=(TextView) itemView.findViewById(R.id.link_package);
            arrow=(ImageView) itemView.findViewById(R.id.arrow);

            hide_1 = (CheckBox) itemView.findViewById(R.id.hide_1);
            hide_2 = (ImageView) itemView.findViewById(R.id.hide_2);
            hide_3 = (ImageView) itemView.findViewById(R.id.hide_3);
            hide_4 = (ImageView) itemView.findViewById(R.id.hide_4);
            hide_5 = (ImageView) itemView.findViewById(R.id.hide_5);
            ll_hide = (LinearLayout) itemView.findViewById(R.id.ll_hide);
        }
        //

    }


}