package com.example.acer.collectionplus.ViewModel;

import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;

import com.example.acer.collectionplus.Adapter.ClassAdapter;
import com.example.acer.collectionplus.BR;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Helper.TimeHelper;
import com.example.acer.collectionplus.JavaBean.LinkBean;
import com.example.acer.collectionplus.JavaBean.SimpleDirBean;
import com.example.acer.collectionplus.Model.AddModel;
import com.example.acer.collectionplus.View.IAddView;
import com.example.acer.collectionplus.databinding.ActivityAddBinding;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddVM implements AddLoadListener<SimpleDirBean>{
    private String content;                 //剪贴板中内容
    private boolean isLink;                 //剪贴板中的内容是不是链接
    private ClassAdapter mAdapter;
    private IAddView mView;
    private AddModel model;
    private ActivityAddBinding binding;
    private DataToShow data;
    LinkBean.Entity entity = new LinkBean.Entity();

    public AddVM (ClassAdapter mAdapter, IAddView mView , ActivityAddBinding binding,String content){
        this.mView = mView;
        this.data = new DataToShow();
        this.mAdapter = mAdapter;
        this.binding = binding;
        this.model = new AddModel();
        this.content = content;
        this.binding.setVariable(BR.mView,this.mView);
        isLink();
        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData(){
        model.LoadData(content,this);
    }
    /**
     * 判断剪贴板中的内容是不是链接
     */
    private void isLink(){
        Pattern pattern = Pattern.compile(".*((https|http)://[a-z./\\d$-_+!*'()]*).*");
        Matcher matcher = pattern.matcher(content);
        // 如果有match
        if (matcher.matches()) {
            content = matcher.group(1);
            this.isLink = true;
        } else {
            this.isLink = false;
        }
    }

    /**
     * model成功加载链接标题和链接图片地址。
     * @param list
     */
    @Override
    public void loadSuccessForPathAndTitle(List<String> list) {
        binding.etTitle.setText(list.get(0));
        this.entity.setPicPath(list.get(1));
    }

    /**
     * model,成功加载收藏夹列表
     * @param list
     */
    @Override
    public void loadSuccess(List<SimpleDirBean> list) {
        mAdapter.refreshData(list);
    }

    @Override
    public void loadFailure(String message) {
        mView.loadFailure(message);
    }

    @Override
    public void loadStart() {
        
    }

    @Override
    public void loadComplete() {

    }

    /**
     * 保存链接
     */
    public void saveLink(){

        entity.setValue(content);
        if(isLink) {
            entity.setDirname(mAdapter.getCheckedName());
            entity.setRead(false);
            entity.setSource("QQ");
            entity.setTitle(binding.etTitle.getText().toString());
            entity.setType("计算机");
            entity.setTime(TimeHelper.getFormatedTime(new Date(),1));
        }

        model.saveData(entity,this);
    }

    public static class DataToShow{
        public ObservableField<String> title = new ObservableField<String>();
    }
}
