package com.example.acer.collectionplus.Base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{
    protected Context mContext;
    protected List<T> dataSet;
    protected LayoutInflater inflater;

    public BaseAdapter(Context context) {
        super();
        this.mContext = context;
        dataSet = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
       return onCreateVH(parent,viewType);
    }


    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindVH(holder,position);
    }

    /**
     * 创建ViewHoler
     * @return
     */
    public abstract  VH onCreateVH(ViewGroup parent, int viewType);

    /**
     * 绑定数据
     */
    public abstract  void onBindVH(VH holder, int position);
    /**
     * 数据集大小
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    /**
     * 刷新数据
     *
     * @param data
     */
    public void refreshData(List<T> data){
        dataSet.clear();
        dataSet.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 加载更多数据
     *
     * @param data
     */
    public void loadMoreData(List<T> data){
        dataSet.addAll(data);
        notifyDataSetChanged();
    }
}
