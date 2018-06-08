package com.example.acer.collectionplus.View;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.acer.collectionplus.Base.IBaseView;
import com.example.acer.collectionplus.Helper.SharedHelper;
import com.example.acer.collectionplus.Helper.ToastUtils;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.ViewModel.EnsureCodeVM;
import com.example.acer.collectionplus.ViewModel.RegisterVM;
import com.example.acer.collectionplus.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity implements IEnsureCodeView {
    public static final String TAG ="RegisterActivity";
    ActivityRegisterBinding registerBinding;
    private RegisterVM registerVM;
    private EnsureCodeVM ensureCodeVM;
    private String Email;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        //VM绑定VIEW
        registerBinding= DataBindingUtil.setContentView(this, R.layout.activity_register);
        this.registerVM=new RegisterVM(this,RegisterActivity.this);
        this.ensureCodeVM=new EnsureCodeVM(this,RegisterActivity.this);

        registerBinding.setRegisterView(this);
        registerVM.setRegisterBinding(registerBinding);

        //SharedHelper.getInstance().initShared(getApplicationContext());

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
        registerBinding.buttonEnsureWord.setEnabled(true);

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

    @Override
    public String getEmail(){
        return registerBinding.editTextemail.getText().toString();
    }
    @Override
    public void serEmailError(){
        registerBinding.editTextemail.setError("邮箱地址不合法");
    }

    @Override
    public void setResend(Long aLong){
        Log.d(TAG,aLong + "秒后重发");
        registerBinding.buttonEnsureWord.setText(aLong + "秒后重发");
    }
    @Override
    public void DisabledCode(){
        Log.d(TAG,"禁用按钮");
        registerBinding.buttonEnsureWord.setEnabled(false);
    }
    @Override
    public void completeCode(){
        Log.d(TAG,"完成");
        registerBinding.buttonEnsureWord.setEnabled(true);
        registerBinding.buttonEnsureWord.setText("获取验证码");
    }

    @Override
    public String getActiveCode() {
        return null;
    }

    @Override
    public void setNextError() {

    }

    @Override
    public int getViewType() {
        //表示注册
        return 0;
    }
}
