package com.example.acer.collectionplus.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.example.acer.collectionplus.BR;
import com.example.acer.collectionplus.Base.BaseAdapter;
import com.example.acer.collectionplus.Base.BaseViewHolder;
import com.example.acer.collectionplus.JavaBean.SimpleRecomUserBean;
import com.example.acer.collectionplus.R;

import java.util.List;

public class RecomUserAdapter extends BaseAdapter<SimpleRecomUserBean, BaseViewHolder> {

    public RecomUserAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater,R.layout.recom_user, parent,false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindVH(BaseViewHolder holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.recomUserBean,dataSet.get(position));
    }

    @Override
    public void refreshData(List<SimpleRecomUserBean> data) {
        super.refreshData(data);
    }

    @Override
    public void loadMoreData(List<SimpleRecomUserBean> data) {
        super.loadMoreData(data);
    }
}