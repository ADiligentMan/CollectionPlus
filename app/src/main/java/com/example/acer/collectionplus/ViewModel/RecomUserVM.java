package com.example.acer.collectionplus.ViewModel;

import com.example.acer.collectionplus.Adapter.RecomUserAdapter;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.JavaBean.RecomUserBean;
import com.example.acer.collectionplus.JavaBean.SimpleRecomUserBean;
import com.example.acer.collectionplus.Model.RecomUserModel;

import java.util.List;

public class RecomUserVM implements BaseLoadListener<SimpleRecomUserBean> {
    private RecomUserAdapter recomUserAdapter;
    private RecomUserModel recomUserModel;

    public RecomUserVM(RecomUserAdapter recomUserAdapter) {
        this.recomUserAdapter = recomUserAdapter;
        this.recomUserModel = new RecomUserModel();
        initData();
    }

    private void initData() {
        recomUserModel.loadData(this);
    }
    @Override
    public void loadSuccess(List<SimpleRecomUserBean> list) {
        recomUserAdapter.refreshData(list);
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
