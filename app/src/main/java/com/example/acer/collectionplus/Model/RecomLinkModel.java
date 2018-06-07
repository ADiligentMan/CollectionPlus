package com.example.acer.collectionplus.Model;

import android.util.Log;

import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Http.HttpUtils;
import com.example.acer.collectionplus.JavaBean.RecomLinkBean;
import com.example.acer.collectionplus.JavaBean.SimpleRecomLinkBean;
import com.example.acer.collectionplus.RetrofitInterface.RecomLinkHttp;
import com.example.acer.collectionplus.RetrofitInterface.RecomUserHttp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RecomLinkModel implements IRecomLinkModel {
    private static final String TAG = "RecomLinkModel";
    List<SimpleRecomLinkBean> simpleRecomLinkBeanList = new ArrayList<SimpleRecomLinkBean>();

    @Override
    public void loadData(final BaseLoadListener<SimpleRecomLinkBean> loadListener) {
        Observable<RecomLinkBean> observable1 = HttpUtils.getRetrofit().create(RecomLinkHttp.class).getRecomLink();

        //加载所有收藏
        observable1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<RecomLinkBean>() {

                    public void onNext(@NonNull RecomLinkBean recomLinkBean) {
                        Log.i(TAG, "onNext: ");
                        recomLinkOnNext(recomLinkBean);
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
                        Log.i(TAG, "onError: " + e.getLocalizedMessage());
                        loadListener.loadFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                        loadListener.loadSuccess(simpleRecomLinkBeanList);
                        loadListener.loadComplete();
                    }
                });

    }

    private void recomLinkOnNext(RecomLinkBean recomLinkBean) {
        Log.i(TAG, "onNext: ");

        List<RecomLinkBean.Entity> entities = recomLinkBean.getData();
        simpleRecomLinkBeanList.clear();
        if (entities != null && entities.size() > 0) {
            for (RecomLinkBean.Entity entity : entities) {
                String title = entity.getTitle();

                SimpleRecomLinkBean srl = new SimpleRecomLinkBean();
                srl.title.set(title);
                simpleRecomLinkBeanList.add(srl);
            }
        }
    }
}
