package com.example.acer.collectionplus.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.example.acer.collectionplus.Base.BaseAdapter;
import com.example.acer.collectionplus.Base.BaseViewHolder;
import com.example.acer.collectionplus.JavaBean.SimpleDirBean;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.BR;

public class ClassAdapter extends BaseAdapter<SimpleDirBean,BaseViewHolder> {
    private static String TAG ="ClassAdapter";
    private int oldPosition ;
    public ClassAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater,R.layout.item_classify,parent,false);
        BaseViewHolder holder = new BaseViewHolder(binding);
        return holder;

    }

    @Override
    public void onBindVH(BaseViewHolder holder, int position) {
        ViewDataBinding binding  = holder.getBinding();
        binding.setVariable(BR.simpleDirBean,dataSet.get(position));
        binding.setVariable(BR.classAdapter,this);
        binding.setVariable(BR.position,position);
    }

    /**
     * 在添加收藏页面点击一个分类，志云巽点击一个分类
     * @param position 分类的位置
     */
    public void onClickClass(int position){
        dataSet.get(oldPosition).isClicked.set(false);
        dataSet.get(position).isClicked.set(true);
        oldPosition = position;
    }

    /**
     * 返回选中的收藏夹名称
     * @return
     */
    public String getCheckedName(){
        return dataSet.get(oldPosition).dirname.get();
    }
}
