package com.example.acer.collectionplus.ViewModel;

import com.example.acer.collectionplus.Adapter.LinkAdapter;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Constant.MainConstant;
import com.example.acer.collectionplus.JavaBean.SimpleLinkBean;
import com.example.acer.collectionplus.Model.ILinkModel;
import com.example.acer.collectionplus.Model.LinkModel;
import com.example.acer.collectionplus.View.IMainFragmentView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinkVM implements BaseLoadListener<SimpleLinkBean> {
    private ILinkModel linkModel;
    private IMainFragmentView view;
    private LinkAdapter mAdapter;
    private int currPage = 1; //当前页数
    private int loadType; //加载数据的类型
    private String currDir = "all";

    public LinkVM(IMainFragmentView view, LinkAdapter adapter) {
        this.view = view;
        this.mAdapter = adapter;
        this.linkModel = new LinkModel();
        linkModel = new LinkModel();
        initData();
    }

    /**
     * 初始化
     */
    private void initData() {
        loadType = MainConstant.LoadData.FIRST_LOAD;
        Map<String, String> params = new HashMap<String, String>();
        params.put("dirname", currDir);
        linkModel.loadData(currPage, this, params);
    }

    /**
     * 加载更多
     */
    public void onLoadMoreData() {
        currPage++;
        loadType = MainConstant.LoadData.LOAD_MORE;
        Map<String, String> params = new HashMap<String, String>();
        params.put("dirname", currDir);
        linkModel.loadData(currPage, this, params);

    }

    /**
     * 下拉刷新
     */
    public void onRefreshData() {
        currPage = 1;
        loadType = MainConstant.LoadData.REFRESH;
        Map<String, String> params = new HashMap<String, String>();
        params.put("dirname", currDir);
        linkModel.loadData(currPage, this, params);
    }

    /**
     * 切换收藏夹重新求数据。
     *
     * @param dirname
     * @param position
     */
    public void onChangeDir(String dirname,int position){
        currDir = dirname;
        onRefreshData();
    }



    /**
     * 加载数据成功
     *
     * @param list
     */
    @Override
    public void loadSuccess(List<SimpleLinkBean> list) {
        if (currPage > 1) {
            //上拉加载的数据
            mAdapter.loadMoreData(list);
        } else {
            //第一次加载或者下拉刷新的数据
            mAdapter.refreshData(list);
        }
    }

    /**
     * 加载失败
     *
     * @param message
     */
    @Override
    public void loadFailure(String message) {
        //加载失败后提示
        if(currPage>1){
            //加载失败后需要回到之前的页数
            currPage--;
        }
        view.loadFailure(message);
    }

    /**
     * 开始加载
     */
    @Override
    public void loadStart() {
        view.loadStart(loadType);
    }

    /**
     * 加载结束
     */
    @Override
    public void loadComplete() {
        view.loadComplete();
    }
}
