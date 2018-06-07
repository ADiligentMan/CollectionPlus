package com.example.acer.collectionplus.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.acer.collectionplus.Helper.SharedHelper;
import com.example.acer.collectionplus.R;

public class StartPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startpage);


        Integer time = 2000;    //设置等待时间，单位为毫秒
        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //初始化 sharedPrefenrence
                SharedHelper.getInstance().initShared(getApplicationContext());
                Intent intentIN=new Intent(StartPageActivity.this, LoginActivity.class);
                startActivity(intentIN);
                StartPageActivity.this.finish();
            }
        }, time);
    }
}
