package com.example.acer.collectionplus.Helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.acer.collectionplus.View.TimerActivity;

public class AlarmReciver extends BroadcastReceiver {
    private Context context;
    private Intent intent;

    public AlarmReciver(){}

    public Context getContext(){
        return context;
    }
    public Intent intent(){
        return intent;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        //当系统到设定的时间点

        Log.e("alarmReceiver", "定时到达触发");
        Toast.makeText(context,"收到提醒",Toast.LENGTH_SHORT).show();

        intent = new Intent(context, TimerActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("STR_CALLER","");
        intent.putExtras(bundle);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}
