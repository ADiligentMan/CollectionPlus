package com.example.acer.collectionplus.Model;

import android.util.Log;

import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Helper.TimeHelper;
import com.example.acer.collectionplus.Http.HttpUtils;
import com.example.acer.collectionplus.JavaBean.RecomUserBean;
import com.example.acer.collectionplus.JavaBean.SimpleLinkBean;
import com.example.acer.collectionplus.JavaBean.SimpleRecomUserBean;
import com.example.acer.collectionplus.RetrofitInterface.RecomUserHttp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RecomUserModel implements IRecomUserModel {
    private static final String TAG = "RecomUserModel";
    List<SimpleRecomUserBean> simpleRecomUserBeanList = new ArrayList<SimpleRecomUserBean>();

    @Override
    public void loadData(final BaseLoadListener<SimpleRecomUserBean> loadListener) {
        Observable<RecomUserBean> observable1 = HttpUtils.getRetrofit().create(RecomUserHttp.class).getRecomUser();

        //加载所有收藏
        observable1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<RecomUserBean>() {

                    public void onNext(@NonNull RecomUserBean recomUserBean) {
                        Log.i(TAG, "onNext: ");
                        recomUserOnNext(recomUserBean);
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
                        loadListener.loadSuccess(simpleRecomUserBeanList);
                        loadListener.loadComplete();
                    }
                });

    }

    private void recomUserOnNext(RecomUserBean recomUserBean) {
        Log.i(TAG, "onNext: ");

        List<RecomUserBean.Entity> entities = recomUserBean.getData();
        simpleRecomUserBeanList.clear();
        if (entities != null && entities.size() > 0) {
            for (RecomUserBean.Entity entity : entities) {
                String name = entity.getUsername();

                SimpleRecomUserBean srb = new SimpleRecomUserBean();
                srb.name.set(name);
                simpleRecomUserBeanList.add(srb);
            }
        }
    }
}
