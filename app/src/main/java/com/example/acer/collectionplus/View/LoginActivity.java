package com.example.acer.collectionplus.View;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.acer.collectionplus.Base.IBaseView;
import com.example.acer.collectionplus.Constant.MainConstant;
import com.example.acer.collectionplus.Helper.DialogHelper;
import com.example.acer.collectionplus.Helper.SharedHelper;
import com.example.acer.collectionplus.Helper.ToastUtils;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.ViewModel.LoginVM;
import com.example.acer.collectionplus.databinding.ActivityLogBinding;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2018/5/19.
 */

public class LoginActivity extends AppCompatActivity implements IBaseView {
    public static final String TAG ="LoginActivity";
    ActivityLogBinding bindingLogin;
    LoginVM userVM;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //VM绑定VIEW
        bindingLogin= DataBindingUtil.setContentView(this, R.layout.activity_log);
        this.userVM = new LoginVM(this);
        bindingLogin.setMview(this);

    }

    @Override
    public void loadStart(int loadType) {

    }

    @Override
    public void loadComplete() {


        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        this.startActivity(intent);
        finish();


    }

    @Override
    public void loadFailure(String message) {
        ToastUtils toastUtils=new ToastUtils();
        toastUtils.show(LoginActivity.this,message);
    }

    public void LoginOnClick(){
        Log.d(TAG,"进入绑定成功");
        if (!validate()) {
            ToastUtils toastUtils=new ToastUtils();
            toastUtils.show(this,"LOGIN FAILED!");
            return;
        }else{
            //获取layout数据
            Log.d(TAG,bindingLogin.editTextname.getText().toString());
            Log.d(TAG,bindingLogin.editTextname.getText().toString());
            Map<String,String> userSend=new HashMap<>();
            userSend.put("username",bindingLogin.editTextname.getText().toString());
            userSend.put("password",bindingLogin.editTextpassword.getText().toString());

            userVM.sendUser(userSend);
        }


    }
    //跳转至注册界面
    public void RegisterOnClick(){
        Intent intentRegister=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intentRegister);
        Log.d(TAG, "enter Register");
    }
    //跳转至忘记密码界面
    public void ForgetOnClick(){
        Intent intentForget=new Intent(LoginActivity.this,ForgetActivity.class);
        startActivity(intentForget);

    }
    //进行一些窗口信息的简单判断
    private boolean validate(){
//        用户名：不为空
//        邮箱名
//        密码：4-16位密码，应包含数字、字母等不同的字符
        String username=bindingLogin.editTextname.getText().toString();
        String password=bindingLogin.editTextpassword.getText().toString();

        //输入内容是否为空
        if(username.isEmpty()){
            bindingLogin.editTextname.setError("用户名或邮箱名不能为空");
            return false;
        }
        else {
            bindingLogin.editTextname.setError(null);
        }

        if(password.isEmpty()){
            bindingLogin.editTextpassword.setError("密码不能为空");
            return false;
        }else {
            bindingLogin.editTextpassword.setError(null);
        }

        if(password.length()<4||password.length()>16){
            bindingLogin.editTextpassword.setError("密码长度应为4-16位");
            return false;
        }else {
            bindingLogin.editTextpassword.setError(null);
        }
        return true;
    }
}
