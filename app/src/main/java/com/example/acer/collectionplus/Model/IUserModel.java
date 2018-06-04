package com.example.acer.collectionplus.Model;

import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.JavaBean.SimpleDirBean;
import com.example.acer.collectionplus.JavaBean.SimpleUserBean;

import java.util.Map;

/**
 * Created by asus on 2018/5/19.
 */

public interface IUserModel {
    void loadData(BaseLoadListener<SimpleUserBean> loadListener, Map<String,String> params);
}
