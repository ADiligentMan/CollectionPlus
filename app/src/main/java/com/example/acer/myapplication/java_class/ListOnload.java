package com.example.acer.myapplication.java_class;

import android.support.v4.widget.NestedScrollView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * Created by asus on 2018/4/15.
 */

public class ListOnload {
private NestedScrollView nestedScrollView;
private ListView listView;
private  View footer;
    public ListOnload(NestedScrollView nestedScrollView,ListView listView,View footer) {
        this.nestedScrollView = nestedScrollView;
        this.listView = listView;
        this.footer=footer;
    }

//get set

    public NestedScrollView getNestedScrollView() {
        return nestedScrollView;
    }

    public void setNestedScrollView(NestedScrollView nestedScrollView) {
        this.nestedScrollView = nestedScrollView;
    }

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public View getFooter() {
        return footer;
    }

    public void setFooter(View footer) {
        this.footer = footer;
    }
    //添加触摸事件监听接口
    private void setonTouchListener(){
        nestedScrollView.setOnTouchListener(new TouchListenerImpl());
    }

    //触摸事件监听接口具体实现
    private class TouchListenerImpl implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    break;
                case MotionEvent.ACTION_MOVE:
                    int scrollY=view.getScrollY();
                    int height=view.getHeight();
                    int scrollViewMeasuredHeight=nestedScrollView.getChildAt(0).getMeasuredHeight();
                    if(scrollY==0){
                        System.out.println("滑动到了顶端 view.getScrollY()="+scrollY);
                    }
                    if((scrollY+height)==scrollViewMeasuredHeight){
                        System.out.println("滑动到了底部 scrollY="+scrollY);
                        System.out.println("滑动到了底部 height="+height);
                        System.out.println("滑动到了底部 scrollViewMeasuredHeight="+scrollViewMeasuredHeight);
                        //加载之前添加页脚
                        listView.addFooterView(footer);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                //重写加载方法
                            }
                        }).start();




                    }
                    break;

                default:
                    break;
            }
            return false;
        }

    }
}
