package com.example.acer.collectionplus.ViewModel;

import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.JavaBean.SimpleUserBean;
import com.example.acer.collectionplus.Model.IUserModel;
import com.example.acer.collectionplus.Model.UserModel;
import com.example.acer.collectionplus.View.IUserFragmentView;
import com.example.acer.collectionplus.databinding.FragmentUserBinding;

import java.util.List;

/**
 * Created by asus on 2018/5/19.
 */

public class UserVM implements BaseLoadListener<SimpleUserBean> {
    //定义成员变量
    IUserFragmentView view;
    IUserModel userModel;

    FragmentUserBinding VM_binding;

    public UserVM(IUserFragmentView view) {
        this.view = view;
        userModel = new UserModel();
        //设置数据

        initData();
    }

    //初始化数据
    private void initData() {


        userModel.loadData(this, null);
    }

    //得到服务器传回的数据
    @Override
    public void loadSuccess(List<SimpleUserBean> list) {
        VM_binding.userIntroduce.setText(list.get(0).introduce.get());
        VM_binding.userName.setText(list.get(0).username.get());



    }


    @Override
    public void loadFailure(String message) {
        view.loadFailure(message);
    }

    @Override
    public void loadStart() {

    }

    @Override
    public void loadComplete() {
        view.loadComplete();
    }

    //获得fragment的binding对象
    public void getbinding(FragmentUserBinding binding) {
        this.VM_binding = binding;

    }
}
