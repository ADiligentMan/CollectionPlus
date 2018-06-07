package com.example.acer.collectionplus.Model;

import com.example.acer.collectionplus.Base.BaseBean;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.JavaBean.SimpleDirBean;
import com.example.acer.collectionplus.JavaBean.SimpleUserBean;

import java.util.Map;

/**
 * Created by asus on 2018/5/19.
 */
//提供给VM进行方法调用

public interface IUserModel {
    //加载用户所有信息
    void loadData(BaseLoadListener<SimpleUserBean> loadListener, Map<String,String> params);
    //修改用户信息
    void modifyuser(BaseLoadListener<BaseBean> loadListener, Map<String,String> params);
}
