package com.example.acer.collectionplus.Model;

import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.JavaBean.SimpleLinkBean;

import java.util.Map;

public interface ILinkModel {
    /**
     * 切换收藏夹加载所有链接
     * @param page
     * @param loadListener
     * @param params
     */
    void loadData(int page, BaseLoadListener<SimpleLinkBean> loadListener, Map<String,String> params);
}
