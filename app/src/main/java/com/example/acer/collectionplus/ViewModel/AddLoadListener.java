package com.example.acer.collectionplus.ViewModel;

import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.JavaBean.SimpleDirBean;

import java.util.List;

public interface AddLoadListener<T> extends BaseLoadListener<T>{
    void loadSuccessForPathAndTitle(List<String> list);
}
