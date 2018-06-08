package com.example.acer.collectionplus.ViewModel;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Base.IBaseView;
import com.example.acer.collectionplus.Helper.ToastUtils;
import com.example.acer.collectionplus.JavaBean.ResultBean;
import com.example.acer.collectionplus.Model.CheckEnsureCodeModel;
import com.example.acer.collectionplus.Model.RegisterModel;
import com.example.acer.collectionplus.View.IEnsureCodeView;
import com.example.acer.collectionplus.databinding.ActivityRegisterBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckEnsureCodeVM implements BaseLoadListener<ResultBean> {
    public static final String TAG="RegisterVM";
    IEnsureCodeView checkView;
    CheckEnsureCodeModel checkModel;
    Context context;

    public CheckEnsureCodeVM(IEnsureCodeView checkView,Context context) {
        this.checkView=checkView;
        checkModel=new CheckEnsureCodeModel();
        this.context=context;
    }

    @Override
    public void loadSuccess(List<ResultBean> list) {
        ResultBean resultBean=list.get(0);
        //如果返回成功了
        if(resultBean.success1){
            ToastUtils toastUtils=new ToastUtils();
            toastUtils.show(context,"验证成功");
            checkView.loadComplete();
        }else{
            checkView.loadFailure(resultBean.info1);
        }
    }

    @Override
    public void loadFailure(String message) {
        checkView.loadFailure(message);
    }

    @Override
    public void loadStart() {

    }

    @Override
    public void loadComplete() {

    }
    public void checkEnsureCode(){
        Log.i(TAG,"进入检验事件");
        //进行简单验证
        String emailAddress= checkView.getEmail();
        Log.i(TAG,emailAddress);
        if(emailAddress.isEmpty()||!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            checkView.serEmailError();
            Log.i(TAG,"邮箱地址不正确");
            return;
        }else{
            Log.i(TAG,"邮箱地址正确");
        }
        String activeCodeS=checkView.getActiveCode();
        if(activeCodeS.isEmpty()){
            checkView.setNextError();
            return;
        }
        Log.i(TAG,"发送验证码请求");
        //发送请求
        Map<String,String> EnsureCodeInfo=new HashMap<>();
        EnsureCodeInfo.put("email",emailAddress);
        EnsureCodeInfo.put("activeCode",activeCodeS);
        checkModel.loadData(this,EnsureCodeInfo);
    }
}
