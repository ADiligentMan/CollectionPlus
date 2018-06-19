package com.example.acer.collectionplus.View;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
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
    private Toolbar otherToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    ActivityMainBinding binding;
    MainFragment mainFragment;
    UserFragment userFragment;
    RecomFragment recomFragment;
    MainVM mainVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
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
        FragmentManager manager = getFragmentManager();
        FragmentTransaction mTransaction = manager.beginTransaction();
        if( mainFragment == null){
            mainFragment = new MainFragment();
            addFragment(mainFragment,mTransaction);
        }
        showFragment(mainFragment,mTransaction);
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
                Log.d("click","success");
                FragmentManager manager = getFragmentManager();
                FragmentTransaction mTransaction = manager.beginTransaction();
                Fragment fragment = null;
                switch (checkedId) {
                    case R.id.bottom_home:
                        if( mainFragment == null){
                            mainFragment = new MainFragment();
                            addFragment(mainFragment,mTransaction);
                        }
                        fragment = mainFragment;
                        break;
                    case R.id.bottom_find:
                        if(recomFragment ==null){
                            recomFragment = new RecomFragment();
                            addFragment(recomFragment,mTransaction);
                        }
                        fragment = recomFragment;
                        break;
                    case R.id.bottom_mime:
                        if(userFragment == null){
                            userFragment = new UserFragment();
                            addFragment(userFragment,mTransaction);
                        }
                        fragment = userFragment;
                        break;
                }
                showFragment(fragment,mTransaction);
            }
        });
    }

    /**
     * 每个fragment第一次初始话时，add
     * @param fragment
     */
    private void addFragment(Fragment fragment,FragmentTransaction mTransaction){
        mTransaction.add(R.id.container_fragment,fragment);
    }

    /**
     * 切换fragment
     *
     * @param fragment
     */
    private void showFragment(Fragment fragment,FragmentTransaction mTransaction) {
        //先隐藏所有的fragment
        if(this.mainFragment!= null)mTransaction.hide(mainFragment);
        if(this.recomFragment!=null)mTransaction.hide(recomFragment);
        if(this.userFragment!=null)mTransaction.hide(userFragment);

        //再显示需要显示的Fragment
       mTransaction.show(fragment);
       mTransaction.commit();
    }

    /**
     * 初始化DrawerLayout
     * @param view
     */
    public void initBar(View view) {
        if (view != null) {
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

        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

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
