package com.example.acer.collectionplus.View;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.acer.collectionplus.Adapter.RecomLinkAdapter;
import com.example.acer.collectionplus.Adapter.RecomUserAdapter;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.ViewModel.RecomLinkVM;
import com.example.acer.collectionplus.ViewModel.RecomUserVM;
import com.example.acer.collectionplus.databinding.FragmentRecomBinding;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZViewHolder;

public class RecomFragment extends Fragment implements IUserFragmentView {

    private FragmentRecomBinding binding;
    private MZBannerView mMZBanner;
    private ImageView arrowLeft;
    private ImageView arrowRight;
    private RecomUserVM recomUserVM;
    private RecomUserAdapter recomUserAdapter;
    private RecomLinkVM recomLinkVM;
    private RecomLinkAdapter recomLinkAdapter;
    public static SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView lRecyclerView;
    LinearLayoutManager lLayoutManager;
    RecyclerView uRecyclerView;
    LinearLayoutManager uLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recom, container, false);
        initBanner();
        initRecomUser();
        initRecomLink();

        this.recomUserVM = new RecomUserVM(recomUserAdapter);
        this.recomLinkVM = new RecomLinkVM(recomLinkAdapter);

        return binding.getRoot();
    }

    private void initBanner()
    {
        mMZBanner = binding.banner;
        //资源文件
        int[] RES = {R.drawable.image1, R.drawable.image1, R.drawable.image1};
        // 设置数据
        java.util.List<Integer> list = new java.util.ArrayList<>();
        for (int i = 0; i < RES.length; i++) {
            list.add(RES[i]);
        }
        // 设置页面点击事件
        mMZBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
//                android.widget.Toast.makeText(getContext(), "click page:" + position, android.widget.Toast.LENGTH_LONG).show();
            }
        });
        mMZBanner.setPages(list, new com.zhouwei.mzbanner.holder.MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
    }

    private void initRecomLink() {
        lRecyclerView= binding.recyRecomLink;
        lRecyclerView.setHasFixedSize(true);//设置固定大小
        lRecyclerView.setItemAnimator(new android.support.v7.widget.DefaultItemAnimator());//设置默认动画
        lLayoutManager=new android.support.v7.widget.LinearLayoutManager(getActivity());
//        mLayoutManager.setOrientation(android.support.v7.widget.OrientationHelper.HORIZONTAL);//设置滚动方向，横向滚动
        lRecyclerView.setLayoutManager(lLayoutManager);

        recomLinkAdapter=new RecomLinkAdapter(getActivity());
        lRecyclerView.setAdapter(recomLinkAdapter);

        swipeRefreshLayout = binding.swipeRecom;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recomLinkVM.initData();
            }
        });
    }

    private void initRecomUser() {
        uRecyclerView= binding.recyRecomUser;
        uRecyclerView.setHasFixedSize(true);//设置固定大小
        uRecyclerView.setItemAnimator(new android.support.v7.widget.DefaultItemAnimator());//设置默认动画
        uLayoutManager=new android.support.v7.widget.LinearLayoutManager(getActivity());
        uLayoutManager.setOrientation(android.support.v7.widget.OrientationHelper.HORIZONTAL);//设置滚动方向，横向滚动
        uRecyclerView.setLayoutManager(uLayoutManager);

        recomUserAdapter=new RecomUserAdapter(getActivity());
        uRecyclerView.setAdapter(recomUserAdapter);

        // 两个箭头的事件
        arrowLeft = binding.arrowLeft;
        arrowRight = binding.arrowRight;

        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uLayoutManager.scrollToPosition(uLayoutManager.findFirstVisibleItemPosition() - 1);
                Log.d("leftPos", String.valueOf(uLayoutManager.findFirstVisibleItemPosition()));
            }
        });
        arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uLayoutManager.scrollToPosition(uLayoutManager.findLastVisibleItemPosition() + 1);
                Log.d("rightPos", String.valueOf(uLayoutManager.findLastVisibleItemPosition()));
            }
        });
    }

    //轮播的动画效果
    @Override
    public void onPause() {
        super.onPause();
        mMZBanner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();//开始轮播
    }

    @Override
    public void loadStart(int loadType) {
    }

    @Override
    public void loadComplete() {

    }

    @Override
    public void loadFailure(String message) {

    }

    @Override
    public void modifySucess(String msg) {

    }

    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }
}
