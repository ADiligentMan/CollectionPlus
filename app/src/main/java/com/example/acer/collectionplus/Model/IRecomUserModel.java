package com.example.acer.collectionplus.Model;

import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.JavaBean.SimpleRecomUserBean;

public interface IRecomUserModel {
    void loadData(BaseLoadListener<SimpleRecomUserBean> loadListener);
}
