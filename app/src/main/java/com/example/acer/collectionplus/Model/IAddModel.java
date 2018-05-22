package com.example.acer.collectionplus.Model;

import com.example.acer.collectionplus.JavaBean.LinkBean;
import com.example.acer.collectionplus.JavaBean.SimpleDirBean;
import com.example.acer.collectionplus.ViewModel.AddLoadListener;

public interface IAddModel {
    /**
     * 加载收藏夹以及链接标题
     * @param content
     */
     void LoadData(String content, AddLoadListener<SimpleDirBean> listener);

    /**
     * 保存一个链接
     * @param entity  链接实体
     * @param listener 加载监听器
     */
    void saveData( LinkBean.Entity entity , AddLoadListener<SimpleDirBean> listener);
}
