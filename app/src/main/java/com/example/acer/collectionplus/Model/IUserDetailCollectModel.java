package com.example.acer.collectionplus.Model;

import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.JavaBean.SimpleUserDetailCollectBean;
import com.example.acer.collectionplus.JavaBean.SimpleUserDetailInfoBean;

public interface IUserDetailCollectModel {
    void loadData(BaseLoadListener<SimpleUserDetailCollectBean> loadListener);
}
