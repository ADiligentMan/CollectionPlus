package com.example.acer.collectionplus.ViewModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Base.IBaseView;
import com.example.acer.collectionplus.Helper.ToastUtils;
import com.example.acer.collectionplus.JavaBean.ResultBean;
import com.example.acer.collectionplus.Model.RegisterModel;
import com.example.acer.collectionplus.Model.SetPasswordModel;
import com.example.acer.collectionplus.View.ForgetActivity2;
import com.example.acer.collectionplus.View.ISetPasswordView;
import com.example.acer.collectionplus.databinding.ActivityRegisterBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetPasswordVM implements BaseLoadListener<ResultBean> {
    public static final String TAG="SetPasswordVM";
    ISetPasswordView viewSetPassword;
    SetPasswordModel setPasswordModel;
    Context context;

    public SetPasswordVM(ISetPasswordView viewSetPassword, Context context) {
        this.viewSetPassword=viewSetPassword;
        setPasswordModel=new SetPasswordModel();
        this.context=context;
    }
    @Override
    public void loadSuccess(List<ResultBean> list) {
        ResultBean r=list.get(0);
        if(r.success1){
            viewSetPassword.loadComplete();
        }else{
            viewSetPassword.loadFailure(r.info1);
        }
    }

    @Override
    public void loadFailure(String message) {
        viewSetPassword.loadFailure(message);
    }

    @Override
    public void loadStart() {

    }

    @Override
    public void loadComplete() {

    }

    //发送密码
    public void SetPassword(){
        Log.i(TAG,"发送密码请求");
        //首先进行逻辑判断，判断两次密码是否一致
        if(valiate()){
            //发送请求
            Map<String,String> newPwInfo=new HashMap<>();
            newPwInfo.put("password",viewSetPassword.getFirstCode());

            setPasswordModel.loadData(this,newPwInfo);
        }

    }

    private boolean valiate(){
        //先简单逻辑验证
        String pw1=viewSetPassword.getFirstCode();
        String pw2=viewSetPassword.getSecondCode();

        if(pw1.isEmpty()||pw1.length()<4||pw1.length()>16){
            viewSetPassword.setErrorView("密码不符合要求");
            return false;
        }

        if(pw2.isEmpty()) {
            viewSetPassword.setErrorEnsure("请输入确认密码");
            return false;

        }else if(!pw1.equals(pw2)){
            viewSetPassword.setErrorEnsure("两次密码不一致");
            return false;
        }
        return true;
    }
}
