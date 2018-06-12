package com.example.acer.collectionplus.ViewModel;

import com.example.acer.collectionplus.Base.BaseBean;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.JavaBean.SimpleUserBean;
import com.example.acer.collectionplus.Model.UserModel;
import com.example.acer.collectionplus.View.IUserFragmentView;

import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2018/6/6.
 */

public class ChangeVM implements BaseLoadListener<BaseBean> {
    IUserFragmentView view;
   private UserModel userModel;
    public ChangeVM(IUserFragmentView view){
        this.view =view;
        userModel= new UserModel();
        //设置数据

        // initData();
    }
    //传递修改信息
    public void  modifyData(Map<String,String> map){
        userModel.modifyuser(this,map);
    }
    @Override
    public void loadSuccess(List<BaseBean> list) {

    }

    @Override
    public void loadFailure(String message) {
        if(view!=null){
            view.modifySucess(message);
        }
    }

    @Override
    public void loadStart() {

    }

    @Override
    public void loadComplete() {

    }
}
