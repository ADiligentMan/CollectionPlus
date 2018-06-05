package com.example.acer.collectionplus.Model;

import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.JavaBean.SimpleRecomLinkBean;
import com.example.acer.collectionplus.JavaBean.SimpleRecomUserBean;

public interface IRecomLinkModel {
    void loadData(BaseLoadListener<SimpleRecomLinkBean> loadListener);
}
