package com.example.acer.collectionplus.View;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.acer.collectionplus.Helper.SharedHelper;
import com.example.acer.collectionplus.Helper.ToastUtils;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.ViewModel.SetPasswordVM;
import com.example.acer.collectionplus.databinding.ActivityForget2Binding;

public class ForgetActivity2 extends AppCompatActivity implements ISetPasswordView{
    //变量定义
    public static final String TAG ="ForgetActivity2";
    private SetPasswordVM passwordVM;
    ActivityForget2Binding forgetBinding2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //绑定
        forgetBinding2= DataBindingUtil.setContentView(this, R.layout.activity_forget2);
        this.passwordVM=new SetPasswordVM(this,ForgetActivity2.this);
        forgetBinding2.setForgetView2(this);


    }
    @Override
    public void loadStart(int loadType) {

    }

    @Override
    public void loadComplete() {
        ToastUtils toastUtils=new ToastUtils();
        toastUtils.show(ForgetActivity2.this,"修改成功");
        //成功，跳转到登陆界面
        Intent intent=new Intent(ForgetActivity2.this,LoginActivity.class);
        this.startActivity(intent);
        finish();
    }

    @Override
    public void loadFailure(String message) {
        ToastUtils toastUtils=new ToastUtils();
        toastUtils.show(ForgetActivity2.this,message);
    }

    //点击完成按钮，发送密码
    public void FinishOnClick(){
        passwordVM.SetPassword();
    }

    @Override
    public String getFirstCode() {
        return forgetBinding2.editTextpw2.getText().toString();
    }

    @Override
    public String getSecondCode() {
        return forgetBinding2.editTextpwE2.getText().toString();
    }
    @Override
    public void setErrorView(String msg){
        forgetBinding2.editTextpw2.setError(msg);
        forgetBinding2.editTextpw2.setText("");
        forgetBinding2.editTextpwE2.setText("");
    }
    @Override
    public void setErrorEnsure(String msg){
        forgetBinding2.editTextpwE2.setError(msg);
        forgetBinding2.editTextpwE2.setText("");
    }
}
