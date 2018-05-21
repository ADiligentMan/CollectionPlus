package com.example.acer.collectionplus.Model;

import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.JavaBean.SimpleDirBean;
import com.example.acer.collectionplus.JavaBean.SimpleLinkBean;

import java.util.Map;

public interface IDirModel {
    /**
     * 刷新后加载所有收藏夹
     *
     * @param loadListener
     * @param params
     */
    void loadData(BaseLoadListener<SimpleDirBean> loadListener, Map<String,String> params);
}
