package com.example.acer.collectionplus.Base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BaseViewHolder extends RecyclerView.ViewHolder{
    private ViewDataBinding binding;
    public BaseViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * 得到binding 对象
     */
    public ViewDataBinding getBinding(){
        return this.binding;
    }
}
