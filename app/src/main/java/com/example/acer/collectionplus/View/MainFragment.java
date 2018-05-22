package com.example.acer.collectionplus.View;


import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.acer.collectionplus.Adapter.DirAdapter;
import com.example.acer.collectionplus.Adapter.LinkAdapter;
import com.example.acer.collectionplus.Constant.MainConstant;
import com.example.acer.collectionplus.Helper.DialogHelper;
import com.example.acer.collectionplus.Helper.ToastUtils;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.ViewModel.DirVM;
import com.example.acer.collectionplus.ViewModel.LinkVM;
import com.example.acer.collectionplus.databinding.FragmentMainBinding;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements IMainFragmentView {
    public static final String TAG = "MainFragment";
    private FragmentMainBinding binding;
    //about link
    private LinkAdapter linkAdapter;
    private LinkVM linkVM;
    //about Directory
    private DirAdapter dirAdapter;
    private DirVM dirVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = (FragmentMainBinding) DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        initDirRecyclView();
        initLinkRecycleView();
        this.linkVM = new LinkVM(this, linkAdapter);
        this.dirVM = new DirVM(this, dirAdapter);

        return binding.getRoot();
    }

    private void initLinkRecycleView() {

        binding.xrvLink.setRefreshProgressStyle(ProgressStyle.BallClipRotate);
        binding.xrvLink.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        binding.xrvLink.setArrowImageView(R.drawable.pull_down_arrow);
        binding.xrvLink.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        binding.xrvLink.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                linkVM.onRefreshData();
            }

            @Override
            public void onLoadMore() {
                linkVM.onLoadMoreData();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.xrvLink.setLayoutManager(layoutManager);
        linkAdapter = new LinkAdapter(getActivity());

        binding.xrvLink.setAdapter(linkAdapter);

    }

    private void initDirRecyclView() {
        binding.xrvDir.setRefreshProgressStyle(ProgressStyle.BallClipRotate);
        binding.xrvDir.addItemDecoration(new  DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        binding.xrvDir.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                dirVM.onRefreshData();
            }

            @Override
            public void onLoadMore() {

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.xrvDir.setLayoutManager(layoutManager);
        dirAdapter = new DirAdapter(getActivity(), this);

        binding.xrvDir.setAdapter(dirAdapter);

    }

    /**
     * 开始加载
     *
     * @param loadType 加载的类型 0：第一次记载 1：下拉刷新 2：上拉加载更多
     */
    @Override
    public void loadStart(int loadType) {
        if (loadType == MainConstant.LoadData.FIRST_LOAD) {
            DialogHelper.getInstance().show(getActivity(), "加载中...", DialogHelper.LOAD_DIALOG);
        }
    }

    /**
     * 加载完成
     */
    @Override
    public void loadComplete() {
        DialogHelper.getInstance().close();
        binding.xrvLink.loadMoreComplete(); //结束加载
        binding.xrvLink.refreshComplete();  //结束加载
        binding.xrvDir.refreshComplete();  //结束刷新
    }

    /**
     * 加载失败
     *
     * @param message
     */
    @Override
    public void loadFailure(String message) {
        DialogHelper.getInstance().close();
        binding.xrvLink.loadMoreComplete();//结束加载
        binding.xrvLink.refreshComplete();  //结束加载
        binding.xrvDir.refreshComplete();  //结束刷新
        ToastUtils.show(getActivity(), message);
    }

    /**
     * 点击收藏夹的事件处理（切换收藏夹）
     *
     * @param dirname  收藏夹名称
     * @param position 收藏夹位置
     */
    @Override
    public void changeDir(String dirname, int position) {

        linkVM.onChangeDir(dirname, position);
    }
}
