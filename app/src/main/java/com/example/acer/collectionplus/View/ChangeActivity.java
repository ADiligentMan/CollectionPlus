package com.example.acer.collectionplus.View;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.acer.collectionplus.R;


public class ChangeActivity extends AppCompatActivity {
private ChangeFragment changeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        //将fragment写在activity里面
        changeFragment = changeFragment==null?new ChangeFragment():changeFragment;
        replaceFragment(changeFragment);
    }
    private void replaceFragment(Fragment fragment) {
        if(fragment==null) return;
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.changeactivity,fragment);
        transaction.commit();
    }

}
