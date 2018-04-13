package com.example.acer.myapplication.activity.service;

import android.content.Context;

import com.example.acer.myapplication.R;

/**
 * Created by admin on 2018/4/6.
 */

public class UrlService {

    //获取前半部分server的url
    public static String getServerUrl(Context context){
        return context.getString(R.string.URL_SERVER);
    }
    //根据情况获取整体的url，包括server和后半部分的url
    public static String getUrl(Context context,int resId){
        return context.getString(R.string.URL_SERVER)+ context.getString(resId);
    }
}
