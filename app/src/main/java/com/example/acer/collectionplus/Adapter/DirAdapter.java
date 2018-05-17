package com.example.acer.collectionplus.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.acer.collectionplus.BR;
import com.example.acer.collectionplus.Base.BaseAdapter;
import com.example.acer.collectionplus.Base.BaseViewHolder;
import com.example.acer.collectionplus.JavaBean.SimpleDirBean;
import com.example.acer.collectionplus.JavaBean.SimpleLinkBean;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.View.IMainFragmentView;

import java.util.List;

public class DirAdapter extends BaseAdapter<SimpleDirBean,BaseViewHolder> {

    private IMainFragmentView view;
    public DirAdapter(Context context,IMainFragmentView view) {
        super(context);
        this.view = view;
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
       ViewDataBinding binding  = DataBindingUtil.inflate(inflater, R.layout.item_dir,parent,false);
       return  new BaseViewHolder(binding);
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
        binding.setVariable(BR.dirname,this.dataSet.get(position).dirname.get());
        binding.setVariable(BR.mView,this.view);
        binding.setVariable(BR.time,dataSet.get(position).time.get());
        binding.setVariable(BR.position,position);
    }

    /**
     * 刷新数据
     *
     * @param data
     */
    @Override
    public void refreshData(List<SimpleDirBean> data) {
        dataSet.clear();
        SimpleDirBean simpleDirBean = new SimpleDirBean();
        simpleDirBean.dirname.set("all");
        simpleDirBean.time.set("0-0-0 00:00:00");
        dataSet.add(simpleDirBean);
        dataSet.addAll(data);
        notifyDataSetChanged();
    }
}

