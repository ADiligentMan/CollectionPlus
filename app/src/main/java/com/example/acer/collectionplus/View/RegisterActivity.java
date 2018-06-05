package com.example.acer.collectionplus.View;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.acer.collectionplus.Base.IBaseView;
import com.example.acer.collectionplus.Helper.ToastUtils;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.ViewModel.EnsureCodeVM;
import com.example.acer.collectionplus.ViewModel.RegisterVM;
import com.example.acer.collectionplus.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity implements IBaseView {
    public static final String TAG ="RegisterActivity";
    ActivityRegisterBinding registerBinding;
    private RegisterVM registerVM;
    private EnsureCodeVM ensureCodeVM;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        //VM绑定VIEW
        registerBinding= DataBindingUtil.setContentView(this, R.layout.activity_register);
        this.registerVM=new RegisterVM(this,RegisterActivity.this);
        this.ensureCodeVM=new EnsureCodeVM(this,RegisterActivity.this);

        registerBinding.setRegisterView(this);

        registerVM.setRegisterBinding(registerBinding);
        ensureCodeVM.setRegisterBinding(registerBinding);


    }

    @Override
    public void loadStart(int loadType) {

    }

    @Override
    public void loadComplete() {
        //登录完成后，返回登录界面
        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
        this.startActivity(intent);
        finish();
    }

    @Override
    public void loadFailure(String message) {
        ToastUtils toastUtils=new ToastUtils();
        toastUtils.show(RegisterActivity.this,message);

    }

    public void getCodeOnClick(){
        ensureCodeVM.getCodeVM();
     }

     public void registerOnClick(){
        registerVM.registerVM();
    }

    public void backOnClick(){
        //登录完成后，返回登录界面
        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
        this.startActivity(intent);
        finish();
    }
}
