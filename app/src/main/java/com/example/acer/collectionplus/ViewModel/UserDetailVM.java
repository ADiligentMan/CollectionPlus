package com.example.acer.collectionplus.ViewModel;

import android.app.Fragment;

import com.example.acer.collectionplus.Adapter.RecomUserAdapter;
import com.example.acer.collectionplus.Adapter.UserDetailAdapter;
import com.example.acer.collectionplus.BR;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.JavaBean.SimpleRecomUserBean;
import com.example.acer.collectionplus.JavaBean.SimpleUserDetailCollectBean;
import com.example.acer.collectionplus.Model.RecomUserModel;
import com.example.acer.collectionplus.Model.UserDetailCollectModel;
import com.example.acer.collectionplus.databinding.FragmentUserDetailBinding;

import java.util.List;

public class UserDetailVM implements BaseLoadListener<SimpleUserDetailCollectBean> {
    private UserDetailAdapter userDetailAdapter;
    private UserDetailCollectModel userDetailCollectModel;
//    private FragmentUserDetailBinding fragmentUserDetailBinding;

    public UserDetailVM(UserDetailAdapter userDetailAdapter) {
        this.userDetailAdapter = userDetailAdapter;
        this.userDetailCollectModel = new UserDetailCollectModel();
        initData();
    }

    public void initData() {
        userDetailCollectModel.loadData(this);
    }

    @Override
    public void loadSuccess(List<SimpleUserDetailCollectBean> list) {
        userDetailAdapter.refreshData(list);
    }

    @Override
    public void loadFailure(String message) {

    }

    @Override
    public void loadStart() {

    }

    @Override
    public void loadComplete() {

    }
}
