package com.example.acer.collectionplus.ViewModel;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.acer.collectionplus.Base.BaseBean;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Base.IBaseView;
import com.example.acer.collectionplus.Helper.ToastUtils;
import com.example.acer.collectionplus.JavaBean.ResultBean;
import com.example.acer.collectionplus.Model.EnsureCodeModel;
import com.example.acer.collectionplus.Model.RegisterModel;
import com.example.acer.collectionplus.databinding.ActivityRegisterBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnsureCodeVM implements BaseLoadListener<ResultBean> {
    public static final String TAG="EnsureCodeVM";

    IBaseView registerView;
    EnsureCodeModel ensureCodeModel;
    ActivityRegisterBinding registerBinding;
    Context context;
    public EnsureCodeVM(IBaseView registerView,Context context){
        this.registerView=registerView;
        this.context=context;
        ensureCodeModel=new EnsureCodeModel();
    }

    public void setRegisterBinding(ActivityRegisterBinding registerBinding) {
        this.registerBinding = registerBinding;
    }

    @Override
    public void loadSuccess(List<ResultBean> list) {
        ResultBean resultBean=list.get(0);
        //如果返回成功了
        if(resultBean.success1){
            ToastUtils toastUtils=new ToastUtils();
            toastUtils.show(context,"发送成功");
        }else{
            registerView.loadFailure(resultBean.info1);
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

    public void getCodeVM(){
        Log.d(TAG,"进入获取验证码事件");
        //获取，检验并发送邮箱地址
        String emailAddress=registerBinding.editTextemail.getText().toString();
        if(emailAddress.isEmpty()||!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            registerBinding.editTextemail.setError("邮箱地址不合法");
            return;
        }

        //发送请求
        Map<String,String> EnsureCodeInfo=new HashMap<>();
        EnsureCodeInfo.put("email",registerBinding.editTextemail.getText().toString());
        ensureCodeModel.loadData(this,EnsureCodeInfo);
        ensureCodeModel.onCode(this);

    }
    public void buttonCode(){
        registerBinding.buttonEnsureWord.setEnabled(false);
    }
    //倒计时
    public void buttonReCode(Long aLong){
        registerBinding.buttonEnsureWord.setText(aLong + "秒后重发");
    }

    public void buttonComplete(){
        registerBinding.buttonEnsureWord.setEnabled(true);
        registerBinding.buttonEnsureWord.setText("获取验证码");
    }
}
