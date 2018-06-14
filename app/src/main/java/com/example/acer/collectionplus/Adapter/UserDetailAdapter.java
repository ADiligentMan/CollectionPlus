package com.example.acer.collectionplus.Adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.acer.collectionplus.BR;
import com.example.acer.collectionplus.Base.BaseAdapter;
import com.example.acer.collectionplus.Base.BaseViewHolder;
import com.example.acer.collectionplus.JavaBean.SimpleRecomUserBean;
import com.example.acer.collectionplus.JavaBean.SimpleUserDetailCollectBean;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.View.UserDetailFragment;

import java.util.List;

public class UserDetailAdapter extends BaseAdapter<SimpleUserDetailCollectBean, BaseViewHolder> {
    public UserDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.user_collect, parent,false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindVH(BaseViewHolder holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.userCollect,dataSet.get(position));
        binding.setVariable(BR.userDetailAdapter, this);
    }

    @Override
    public void refreshData(List<SimpleUserDetailCollectBean> data) {
        super.refreshData(data);
    }

    @Override
    public void loadMoreData(List<SimpleUserDetailCollectBean> data) {
        super.loadMoreData(data);
    }

    /**
     * 点击网页后开启一个活动
     *
     * @param value 网页
     *
     */
    public void clickLink(String value){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(value));
        mContext.startActivity(intent);
    }
}
