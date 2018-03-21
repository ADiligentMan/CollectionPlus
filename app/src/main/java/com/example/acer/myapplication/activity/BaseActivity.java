package com.example.acer.myapplication.activity;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.acer.myapplication.utility.ActivityCollector;

/**
 * Created by acer on 2018/3/21.
 */

public class BaseActivity extends AppCompatActivity {
    public static final  String TAG = "BaseActivity";
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
