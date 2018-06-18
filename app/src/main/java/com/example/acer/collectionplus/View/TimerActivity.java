package com.example.acer.collectionplus.View;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.ViewModel.TimerVM;
import com.example.acer.collectionplus.databinding.ActivityTimerBinding;

public class TimerActivity extends AppCompatActivity {
    public static final String TAG ="TimerActivity";
    ActivityTimerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_timer);
        binding.setTimerView(this);

    }

    //点击确认
    public void clickYes(){
        TimerVM timerVM=new TimerVM(this);
        timerVM.stopRemind();
        finish();
    }
}
