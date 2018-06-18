package com.example.acer.collectionplus.Adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.media.Image;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.acer.collectionplus.BR;
import com.example.acer.collectionplus.Base.BaseAdapter;
import com.example.acer.collectionplus.Base.BaseViewHolder;
import com.example.acer.collectionplus.JavaBean.SimpleRecomUserBean;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.View.UserDetailFragment;

import java.util.List;
import java.util.Random;

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
        int[] avatars = {R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4};
        Random rand = new Random();
//        int randInt = rand.nextInt(4);
        int avatarInt = position % 4;
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.recomUserBean,dataSet.get(position));
        binding.setVariable(BR.recomUserAdapter, this);
        ImageView imageView = binding.getRoot().findViewById(R.id.recom_user_image);
        imageView.setImageResource(avatars[avatarInt]);
    }

    @Override
    public void refreshData(List<SimpleRecomUserBean> data) {
        super.refreshData(data);
    }

    @Override
    public void loadMoreData(List<SimpleRecomUserBean> data) {
        super.loadMoreData(data);
    }

    public void clickUser(String userName) {
//        Toast.makeText(mContext, "444", Toast.LENGTH_SHORT).show();
        // 进入user界面
        FragmentManager manager = ((Activity)mContext).getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_fragment, new UserDetailFragment());
        transaction.commit();
    }
}