package com.example.acer.collectionplus.ViewModel;

import android.content.Context;
import android.util.Log;

import com.example.acer.collectionplus.Base.BaseBean;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Base.IBaseView;
import com.example.acer.collectionplus.Helper.ToastUtils;
import com.example.acer.collectionplus.JavaBean.ResultBean;
import com.example.acer.collectionplus.Model.EnsureCodeModel;
import com.example.acer.collectionplus.Model.RegisterModel;
import com.example.acer.collectionplus.View.LoginActivity;
import com.example.acer.collectionplus.View.RegisterActivity;
import com.example.acer.collectionplus.databinding.ActivityRegisterBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;

public class RegisterVM implements BaseLoadListener<ResultBean> {
    public static final String TAG="RegisterVM";
    IBaseView registerView;
    RegisterModel registerModel;
    ActivityRegisterBinding registerBinding;
    Context context;

    public RegisterVM(IBaseView registerView,Context context) {
        this.registerView=registerView;
        registerModel=new RegisterModel();
        this.context=context;
    }

    public void setRegisterBinding(ActivityRegisterBinding registerBinding) {
        this.registerBinding = registerBinding;
    }

    @Override
    public void loadSuccess(List<ResultBean> list) {
        ResultBean r=list.get(0);
        if(r.success1){
            registerView.loadComplete();
        }else{
            registerView.loadFailure(r.info1);
        }

     }

    @Override
    public void loadFailure(String message) {
        registerView.loadFailure(message);
    }

    @Override
    public void loadStart() {

    }

    @Override
    public void loadComplete() {

    }



    public void registerVM(){
        Log.i(TAG,"进入注册事件");
        //进行简单验证
        if(!validate()){
            ToastUtils toastUtils=new ToastUtils();
            toastUtils.show(context,"请正确填写注册信息");
            registerBinding.buttonSignup.setEnabled(true);
            return;
        }

        //发送请求
        Map<String,String> RegisterInfo=new HashMap<>();
        RegisterInfo.put("username",registerBinding.editTextName.getText().toString());
        RegisterInfo.put("password",registerBinding.editTextpw.getText().toString());
        RegisterInfo.put("email",registerBinding.editTextemail.getText().toString());
        RegisterInfo.put("activeCode",registerBinding.editTextEnsureWord.getText().toString());

        registerModel.loadData(this,RegisterInfo);

    }

    private boolean validate(){
        boolean validateResult=true;
        String editTextNameS=registerBinding.editTextName.getText().toString();
        String editTextpwS=registerBinding.editTextpw.getText().toString();
        String editTextemailS=registerBinding.editTextemail.getText().toString();
        String editTextEnsureWordS=registerBinding.editTextEnsureWord.getText().toString();
        String editTextEnsurePwS=registerBinding.editTextEnsurePw.getText().toString();

        //对于用户名
        if(editTextNameS.isEmpty()){
            validateResult=false;
            registerBinding.editTextName.setError("用户名不能为空");
        }else {
            registerBinding.editTextName.setError(null);
        }
        //对于邮箱
        if(editTextemailS.isEmpty()||!android.util.Patterns.EMAIL_ADDRESS.matcher(editTextemailS).matches()){
            validateResult=false;
            registerBinding.editTextemail.setError("邮箱地址不合法");
        }else {
            registerBinding.editTextemail.setError(null);
        }
        //对于邮箱验证码
        if(editTextEnsureWordS.isEmpty()){
            validateResult=false;
            registerBinding.editTextEnsureWord.setError("邮箱验证码不能为空");
        } {
            registerBinding. editTextEnsureWord.setError(null);
        }

        //对于密码
        if(editTextpwS.isEmpty()||editTextpwS.length()<4||editTextpwS.length()>16){
            validateResult=false;
            registerBinding.editTextpw.setError("密码应为4-16位");
        } else {
            registerBinding.editTextpw.setError(null);
        }

        //对于验证密码
        if(!editTextEnsurePwS.equals(editTextpwS)||editTextEnsurePwS.isEmpty()){
            validateResult=false;
            registerBinding. editTextEnsurePw.setError("两次输入密码不一致");
        } else {
            registerBinding.editTextEnsurePw.setError(null);
        }

        return validateResult;
    }
}
