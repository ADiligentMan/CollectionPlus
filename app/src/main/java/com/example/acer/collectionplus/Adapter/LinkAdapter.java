package com.example.acer.collectionplus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.acer.collectionplus.BR;
import com.example.acer.collectionplus.Base.BaseAdapter;
import com.example.acer.collectionplus.Base.BaseViewHolder;
import com.example.acer.collectionplus.JavaBean.SimpleLinkBean;
import com.example.acer.collectionplus.R;

public class LinkAdapter extends BaseAdapter<SimpleLinkBean,BaseViewHolder>{

    public LinkAdapter(Context context) {
        super(context);
    }

    /**
     * 创建ViewHoler
     *
     * @param parent
     * @param viewType
     *
     * @return
     */
    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater,R.layout.item_link,parent,false);
        return new BaseViewHolder(binding);
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindVH(BaseViewHolder holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.mBean,dataSet.get(position));
        binding.setVariable(BR.mAdapter,this);
        binding.setVariable(BR.position,position);
    }

    /**
     * 点击小三角，隐藏按钮，更换小三角图标
     * @param position
     */
    public void clickSmallTtiangle(int position){
        SimpleLinkBean simpleLinkBean = dataSet.get(position);
        if(simpleLinkBean.isGone.get()){
            simpleLinkBean.isGone.set(false);
        }else {
            simpleLinkBean.isGone.set(true);
        }
    }

    /**
     * 点击网页后开启一个活动
     *
     * @param value 网页
     * @param position 点击的位置
     */
    public void clickLink(String value,int position){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(value));
        mContext.startActivity(intent);
    }
}
