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
import com.example.acer.collectionplus.View.IEnsureCodeView;
import com.example.acer.collectionplus.databinding.ActivityForgetBinding;
import com.example.acer.collectionplus.databinding.ActivityRegisterBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnsureCodeVM implements BaseLoadListener<ResultBean> {
    public static final String TAG="EnsureCodeVM";

    IEnsureCodeView View;
    EnsureCodeModel ensureCodeModel;
    Context context;
    public EnsureCodeVM(IEnsureCodeView View, Context context){
        this.View=View;
        this.context=context;
        ensureCodeModel=new EnsureCodeModel();
    }


    @Override
    public void loadSuccess(List<ResultBean> list) {
        ResultBean resultBean=list.get(0);
        //如果返回成功了
        if(resultBean.success1){
            ToastUtils toastUtils=new ToastUtils();
            toastUtils.show(context,"发送成功");
        }else{
            View.loadFailure(resultBean.info1);
        }
    }

    @Override
    public void loadFailure(String message) {
        View.loadFailure(message);

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
        String emailAddress=View.getEmail();
        Log.i(TAG,emailAddress);
        if(emailAddress.isEmpty()||!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            View.serEmailError();
            Log.i(TAG,"邮箱地址不正确");
            return;
        }else{
            Log.i(TAG,"邮箱地址正确");
        }
        Log.i(TAG,"发送验证码请求");

        int type=View.getViewType();
        String typeS=Integer.toString(type);
        //发送请求
        Map<String,String> EnsureCodeInfo=new HashMap<>();
        EnsureCodeInfo.put("email",emailAddress);
        EnsureCodeInfo.put("type",typeS);
        ensureCodeModel.loadData(this,EnsureCodeInfo);
        ensureCodeModel.onCode(this);

    }
    public void buttonCode(){

        View.DisabledCode();
    }
    //倒计时
    public void buttonReCode(Long aLong){
        View.setResend(aLong);
    }

    public void buttonComplete(){
        View.completeCode();
    }

}
