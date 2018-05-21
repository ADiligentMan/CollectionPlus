package com.example.acer.collectionplus.ViewModel;

import android.content.Context;

import com.example.acer.collectionplus.Adapter.DirAdapter;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.JavaBean.SimpleDirBean;
import com.example.acer.collectionplus.Model.DirModel;
import com.example.acer.collectionplus.Model.IDirModel;
import com.example.acer.collectionplus.Model.ILinkModel;
import com.example.acer.collectionplus.View.IMainFragmentView;

import java.util.List;

public class DirVM implements BaseLoadListener<SimpleDirBean>{
    IMainFragmentView view;
    IDirModel dirModel;
    DirAdapter mAdapter;
    public DirVM(IMainFragmentView view, DirAdapter mAdapter){
        this.view =view;
        dirModel  = new DirModel();
        this.mAdapter = mAdapter;
        initData();
    }

    private void initData(){

        dirModel.loadData(this,null);
    }
    public  void onRefreshData(){
        //刷新数据
        dirModel.loadData(this,null);
    }
    /**
     * 加载数据成功
     *
     * @param list
     */
    @Override
    public void loadSuccess(List<SimpleDirBean> list) {
        //更新数据
        mAdapter.refreshData(list);
    }



    /**
     * 加载失败
     *
     * @param message
     */
    @Override
    public void loadFailure(String message) {
        //加载失败时，提示
        view.loadFailure(message);
    }

    /**
     * 开始加载
     */
    @Override
    public void loadStart() {
        //不做任何操作，因为不需要更新界面。
    }

    /**
     * 加载结束
     */
    @Override
    public void loadComplete() {
        //结束刷新，更新界面
        view.loadComplete();
    }
}
