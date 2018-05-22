package com.example.acer.collectionplus.Model;

import android.util.Log;

import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Helper.AnalyzeHtml;
import com.example.acer.collectionplus.Helper.SharedHelper;
import com.example.acer.collectionplus.Helper.TimeHelper;
import com.example.acer.collectionplus.Http.HttpUtils;
import com.example.acer.collectionplus.JavaBean.DirBean;
import com.example.acer.collectionplus.JavaBean.LinkBean;
import com.example.acer.collectionplus.JavaBean.SimpleDirBean;
import com.example.acer.collectionplus.JavaBean.SimpleLinkBean;
import com.example.acer.collectionplus.RetrofitInterface.MainFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static android.support.constraint.Constraints.TAG;


public class LinkModel implements ILinkModel {
    private static final String TAG = "LinkModel";
    List<SimpleLinkBean> simpleLinkbeanList = new ArrayList<SimpleLinkBean>();

    /**
     * 切换收藏夹加载所有链接
     *
     * @param page
     * @param loadListener
     * @param params
     */
    @Override
    public void loadData( int page, final BaseLoadListener<SimpleLinkBean> loadListener, Map<String, String> params) {
        String username = SharedHelper.getInstance().getValue("username");
        String dirname = params.get("dirname");
        Log.d(TAG, "loadData: "+username);
        Log.d(TAG, "loadData: "+dirname);
        Log.d(TAG,"loadData"+page);
        Observable<LinkBean> observable1 = HttpUtils.getRetrofit().create(MainFragment.class).getLinkList(username, dirname, String.valueOf(page));

        //加载所有收藏
        observable1.subscribeOn(Schedulers.io())
                .map(new Function<LinkBean, LinkBean>() {
                    @Override
                    public LinkBean apply(LinkBean linkBean) throws Exception {//如果picPath为空则从网页上解析URL
                        List<LinkBean.Entity> entities = linkBean.getData();
                        if (entities != null && entities.size() > 0) {
                            for (LinkBean.Entity entity : entities) {
                                String picPath = entity.getPicPath();
                                if(picPath==null){
                                    picPath = new  AnalyzeHtml().GetPath(entity.getValue());
                                    entity.setPicPath(picPath);
                                }
                            }
                        }
                        return linkBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LinkBean>() {

                    public void onNext(@NonNull LinkBean linkBean) {
                        Log.i(TAG, "onNext: ");
                        Link_onNext(linkBean);
                    }

                    @Override
                    protected void onStart() {
                        super.onStart();
                        Log.i(TAG, "onStart: ");
                        loadListener.loadStart();
                    }


                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i(TAG, "onError: "+e.getLocalizedMessage());
                        loadListener.loadFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                        loadListener.loadSuccess(simpleLinkbeanList);
                        loadListener.loadComplete();
                    }
                });

    }

    /**
     * 把linkBean转化为SimpleLinkBean
     *
     * @param linkBean
     */
    private void Link_onNext(LinkBean linkBean) {
        Log.i(TAG, "onNext: ");

        List<LinkBean.Entity> entities = linkBean.getData();
        simpleLinkbeanList.clear();
        if (entities != null && entities.size() > 0) {
            for (LinkBean.Entity entity : entities) {
                String dirname = entity.getDirname();
                String picPath = entity.getPicPath();
                String value = entity.getValue();
                boolean read = entity.isRead();
                String title = entity.getTitle();
                String time = TimeHelper.getFormatedTime(entity.getTime());
                SimpleLinkBean slb = new SimpleLinkBean();
                slb.dirname.set(dirname);
                slb.picPath.set(picPath);
                slb.value.set(value);
                slb.read.set(read);
                slb.title.set(title);
                slb.time.set(time);
                slb.isGone.set(true);
                simpleLinkbeanList.add(slb);
            }
        }
    }

}
