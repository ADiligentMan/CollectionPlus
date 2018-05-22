package com.example.acer.collectionplus.ViewModel;

import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.JavaBean.SimpleDirBean;

public interface AddLoadListener<T> extends BaseLoadListener<T>{

    void loadSuccessForTitle(String title);
}
