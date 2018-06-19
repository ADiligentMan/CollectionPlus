package com.example.acer.collectionplus.View;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.acer.collectionplus.Helper.ToastUtils;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.ViewModel.CheckEnsureCodeVM;
import com.example.acer.collectionplus.ViewModel.EnsureCodeVM;
import com.example.acer.collectionplus.databinding.ActivityForgetBinding;

public class ForgetActivity extends AppCompatActivity implements IEnsureCodeView {
    //变量定义
    public static final String TAG ="ForgetActivity";
    private EnsureCodeVM ensureCodeVM;
    private CheckEnsureCodeVM checkEnsureCodeVM;
    ActivityForgetBinding forgetBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //绑定
        forgetBinding=DataBindingUtil.setContentView(this, R.layout.activity_forget);
        this.ensureCodeVM=new EnsureCodeVM(this,ForgetActivity.this);
        this.checkEnsureCodeVM=new CheckEnsureCodeVM(this,ForgetActivity.this);
        forgetBinding.setForgetView(this);
    }

        @Override
    public void loadStart(int loadType) {

    }

    @Override
    public void loadComplete() {
        Intent intentF2=new Intent(ForgetActivity.this,ForgetActivity2.class);
        startActivity(intentF2);
    }

    @Override
    public void loadFailure(String message) {
        ToastUtils toastUtils=new ToastUtils();
        toastUtils.show(ForgetActivity.this,message);
    }

    //设置点击事件
    //获取验证码
    public void getCodeOnClick(){
        ensureCodeVM.getCodeVM();
    }
    //下一步
    public void nextOnClick() {
        checkEnsureCodeVM.checkEnsureCode();
    }

    public void backOnClick(){
            //登录完成后，返回登录界
            finish();
        }

    @Override
    public String getEmail() {
        return forgetBinding.editTextemailF.getText().toString();
    }

    @Override
    public void serEmailError() {
        forgetBinding.editTextemailF.setError("邮箱地址不合法");
    }

    @Override
    public void setResend(Long aLong) {
        forgetBinding.buttonEnsureWordF.setText(aLong + "秒后重发");
    }

    @Override
    public void DisabledCode() {
        forgetBinding.buttonEnsureWordF.setEnabled(false);
    }

    @Override
    public void completeCode() {
        forgetBinding.buttonEnsureWordF.setEnabled(true);
        forgetBinding.buttonEnsureWordF.setText("获取验证码");
    }
    @Override
    public String getActiveCode(){
        return forgetBinding.editTextEnsureWordF.getText().toString();
    }

    @Override
    public void setNextError() {
        forgetBinding.editTextEnsureWordF.setError("请输入验证码");
    }


    public int getViewType(){
        //表示忘记密码
        return 1;
    }

}
