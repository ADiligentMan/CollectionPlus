package com.example.acer.collectionplus.View;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.example.acer.collectionplus.Helper.DialogHelper;
import com.example.acer.collectionplus.Helper.SharedHelper;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.ViewModel.MainVM;
import com.example.acer.collectionplus.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    ActivityMainBinding binding;
    MainFragment mainFragment;
    UserFragment userFragment;
    Fragment currFragment;
    RecomFragment recomFragment;
    MainVM mainVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //初始化SharedPreference ,其生命周期同应用进程，不会随activity的销毁而销毁。
        SharedHelper.getInstance().initShared(getApplicationContext());
        SharedHelper.getInstance().setValue("username", "wangpeng");
        mainVM = new MainVM(getApplicationContext());
        initToolbar();
        initFragment();
        initRadioGruop();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        toolbar.setTitle("收藏夹");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 刚开始进来加载第一个fragment
     */
    private void initFragment() {
        mainFragment = mainFragment == null ? new MainFragment() : mainFragment;
        replaceFragment(mainFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //如果剪贴板改变 弹出对话框
        if (mainVM.chipIsChanged()) {
            DialogHelper.getInstance().show(this, mainVM.getCurrClip(), DialogHelper.ADD_DIALOG);
            mainVM.backToStartState();
        }
    }

    /**
     * 初始化RadioGruop
     */
    private void initRadioGruop() {
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment fragment = null;
                switch (checkedId) {
                    case R.id.bottom_home:
                        mainFragment = mainFragment == null ? new MainFragment() : mainFragment;
                        fragment = mainFragment;
                        break;
                    case R.id.bottom_find:
                        recomFragment = recomFragment == null ? new RecomFragment() : recomFragment;
                        fragment = recomFragment;
                        break;
                    case R.id.bottom_link:
                        break;
                    case R.id.bottom_mime:
                        userFragment = userFragment == null ? new UserFragment() : userFragment;
                        fragment = userFragment;
                        break;
                }
                replaceFragment(fragment);
            }
        });
    }

    /**
     * 切换fragment
     *
     * @param fragment
     */
    private void replaceFragment(Fragment fragment) {
        currFragment = fragment;
        if (fragment == null) return;
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_fragment, fragment);
        transaction.commit();
    }

    public void initBar(View view) {
        if (view != null) {
            Log.d(TAG, "replaceFragment: " + "已执行");
            mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawerlayout);
        }
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
    }

    //宿主activity声明回调方法
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*获取对应fragment*/
        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentByTag("UserFragment");
        /*fragment调用自己重写的回调方法*/
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentByTag("UserFragment");
        /*fragment调用自己重写的回调方法*/
        fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
