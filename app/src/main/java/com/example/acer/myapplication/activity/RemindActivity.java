package com.example.acer.myapplication.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.acer.myapplication.R;
import com.example.acer.myapplication.java_class.AlarmReceiver;

//<activity android:name=".activity.RemindActivity"
//        android:theme="@style/Theme.AppCompat.Dialog">
//        </activity>

public class RemindActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_remind);

        Button yes= (Button) findViewById(R.id.remind_dialog_ok);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /**
     * 关闭提醒
     */
    public void stopRemind(){
        Intent intent = new Intent(RemindActivity.this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(RemindActivity.this, 0, intent, 0);
        AlarmManager am= (AlarmManager) RemindActivity.this.getSystemService(RemindActivity.this.ALARM_SERVICE);
        //取消警报
        am.cancel(pi);
        Toast.makeText(RemindActivity.this, "关闭提醒", Toast.LENGTH_SHORT).show();
    }
}
