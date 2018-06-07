package com.example.acer.collectionplus.View;

import android.app.Application;

/**
 * Created by asus on 2018/5/31.
 */
public class MyApplication extends Application {
    private static  MyApplication mInstance;

    public static MyApplication getInstance(){
        if(mInstance == null){
            mInstance = new MyApplication();
        }
        return mInstance;
    }
}
