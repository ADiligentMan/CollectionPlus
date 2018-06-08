package com.example.acer.collectionplus.Model;

import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.JavaBean.SimpleUserDetailInfoBean;

public interface IUserDetailInfoModel {
    void loadData(BaseLoadListener<SimpleUserDetailInfoBean> loadListener);
}
