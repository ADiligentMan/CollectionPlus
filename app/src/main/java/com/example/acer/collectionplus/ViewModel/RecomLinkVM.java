package com.example.acer.collectionplus.ViewModel;

import com.example.acer.collectionplus.Adapter.RecomLinkAdapter;
import com.example.acer.collectionplus.Adapter.RecomUserAdapter;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.JavaBean.SimpleRecomLinkBean;
import com.example.acer.collectionplus.JavaBean.SimpleRecomUserBean;
import com.example.acer.collectionplus.Model.RecomLinkModel;
import com.example.acer.collectionplus.Model.RecomUserModel;

import java.util.List;

public class RecomLinkVM  implements BaseLoadListener<SimpleRecomLinkBean> {
    private RecomLinkAdapter recomLinkAdapter;
    private RecomLinkModel recomLinkModel;

    public RecomLinkVM(RecomLinkAdapter recomLinkAdapter) {
        this.recomLinkAdapter = recomLinkAdapter;
        this.recomLinkModel= new RecomLinkModel();
        initData();
    }

    private void initData() {
        recomLinkModel.loadData(this);
    }

    @Override
    public void loadSuccess(List<SimpleRecomLinkBean> list) {
        recomLinkAdapter.refreshData(list);
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
