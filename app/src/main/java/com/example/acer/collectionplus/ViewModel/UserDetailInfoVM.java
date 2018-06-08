package com.example.acer.Infoionplus.ViewModel;

import com.example.acer.collectionplus.Adapter.UserDetailAdapter;
import com.example.acer.collectionplus.BR;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.JavaBean.SimpleUserDetailCollectBean;
import com.example.acer.collectionplus.JavaBean.SimpleUserDetailInfoBean;
import com.example.acer.collectionplus.Model.UserDetailCollectModel;
import com.example.acer.collectionplus.Model.UserDetailInfoModel;
import com.example.acer.collectionplus.databinding.FragmentUserBinding;
import com.example.acer.collectionplus.databinding.FragmentUserDetailBinding;

import java.util.List;

public class UserDetailInfoVM implements BaseLoadListener<SimpleUserDetailInfoBean> {
    private UserDetailAdapter userDetailAdapter;
    private UserDetailInfoModel userDetailInfoModel;
    private FragmentUserDetailBinding fragmentUserDetailBinding;

    public UserDetailInfoVM() {
        this.userDetailInfoModel = new UserDetailInfoModel();
        initData();
    }

    private void initData() {
        userDetailInfoModel.loadData(this);
    }

    @Override
    public void loadSuccess(List<SimpleUserDetailInfoBean> list) {
        fragmentUserDetailBinding.setVariable(BR.userInfo, list.get(0));
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

    public void getbinding(FragmentUserDetailBinding binding) {
        this.fragmentUserDetailBinding = binding;

    }
}
