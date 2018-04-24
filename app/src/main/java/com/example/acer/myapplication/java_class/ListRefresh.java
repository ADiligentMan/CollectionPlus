package com.example.acer.myapplication.java_class;

import android.app.Notification;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.logging.Handler;

/**
 * Created by asus on 2018/4/15.
 * 下拉刷新
 */

public class ListRefresh {

private SwipeRefreshLayout swipeRefreshLayout;


    public ListRefresh(SwipeRefreshLayout swipeRefreshLayout, Handler handler) {
        this.swipeRefreshLayout = swipeRefreshLayout;

    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }



 //设置刷新事件

    private void myonRefreshListener(SwipeRefreshLayout swipeRefreshLayout){
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        //重写刷新事件
                                     }
                }).start();
            }
        });

    }


}
