package com.example.acer.collectionplus.ViewModel;

import com.example.acer.collectionplus.Base.BaseBean;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Base.IBaseView;
import com.example.acer.collectionplus.JavaBean.ResultBean;
import com.example.acer.collectionplus.Model.LoginModel;
import com.example.acer.collectionplus.View.IMainFragmentView;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/5/19.
 */

public class LoginVM  implements BaseLoadListener<ResultBean> {
    IBaseView view;
    LoginModel userModel;
    public LoginVM(IBaseView view){
        this.view=view;
        userModel=new LoginModel();
        initData();

    }
    private void initData(){

    }
    public void sendUser(Map<String,String> userSend){
        userModel.loadData(this,userSend);
    }
    @Override
    public void loadSuccess(List<ResultBean> list) {
        ResultBean resultBean=list.get(0);
        //如果返回成功了
        if(resultBean.success1){
            //跳转到主界面
            view.loadComplete();
        }else{
            view.loadFailure(resultBean.info1);
        }
    }

    @Override
    public void loadFailure(String message) {
        //显示失败信息
        view.loadFailure(message);

    }

    @Override
    public void loadStart() {

    }

    @Override
    public void loadComplete() {

    }


}
