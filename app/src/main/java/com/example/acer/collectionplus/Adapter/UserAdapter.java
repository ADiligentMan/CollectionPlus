package com.example.acer.collectionplus.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.example.acer.collectionplus.BR;
import com.example.acer.collectionplus.Base.BaseAdapter;
import com.example.acer.collectionplus.Base.BaseViewHolder;
import com.example.acer.collectionplus.JavaBean.SimpleDirBean;
import com.example.acer.collectionplus.JavaBean.SimpleUserBean;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.View.IUserFragmentView;

import java.util.List;

/**
 * Created by asus on 2018/5/19.
 */

public class UserAdapter extends BaseAdapter<SimpleUserBean,BaseViewHolder> {
    private IUserFragmentView view;

    public UserAdapter(Context context, IUserFragmentView view) {
        super(context);
        this.view = view;
    }

    //绑定databinding
    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, parent, false);
        return new BaseViewHolder(binding);

    }

    @Override
    public void onBindVH(BaseViewHolder holder, int position) {
        //获取binding
        ViewDataBinding binding = holder.getBinding();
        //为布局设置数据 先用一个空的填上
        binding.setVariable(BR.userbean,datasetOject);

    }


    /**
     * 刷新数据
     *
     * @param data
     */

    public void refreshObject(SimpleUserBean data) {
        datasetOject=data;
        notifyDataSetChanged();
    }
    /**
     * 刷新数据
     *
     * @param data
     */
    @Override
    public void refreshData(List<SimpleUserBean> data) {
        dataSet.clear();
        SimpleUserBean simpleUserBean = new SimpleUserBean();
        simpleUserBean.username.set("wangpeng");
        simpleUserBean.password.set("");
        simpleUserBean.email.set("");
        simpleUserBean.phone.set("");
        simpleUserBean.introduce.set("");
        simpleUserBean.gender.set("");
        simpleUserBean.age.set("");
        simpleUserBean.sharenumber.set("");
        simpleUserBean.likenumber.set("");
        simpleUserBean.funnumber.set("");
        simpleUserBean.sourcenumber.set("");
        simpleUserBean.notenumber.set("");

        dataSet.add(simpleUserBean);
        dataSet.addAll(data);
        notifyDataSetChanged();
    }
}
