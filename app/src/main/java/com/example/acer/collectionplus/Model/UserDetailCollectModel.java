package com.example.acer.collectionplus.Model;

import android.util.Log;

import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Http.HttpUtils;
import com.example.acer.collectionplus.JavaBean.SimpleUserBean;
import com.example.acer.collectionplus.JavaBean.SimpleUserDetailCollectBean;
import com.example.acer.collectionplus.JavaBean.SimpleUserDetailInfoBean;
import com.example.acer.collectionplus.JavaBean.UserDetailCollectBean;
import com.example.acer.collectionplus.JavaBean.UserDetailInfoBean;
import com.example.acer.collectionplus.RetrofitInterface.UserDetailCollectHttp;
import com.example.acer.collectionplus.RetrofitInterface.UserDetailInfoHttp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class UserDetailCollectModel implements IUserDetailCollectModel {
    private final String TAG = "UserDetailCollect";
    List<SimpleUserDetailCollectBean> simpleUserDetailCollectBeans = new ArrayList<SimpleUserDetailCollectBean>();
    List<SimpleUserDetailInfoBean> simpleUserDetailInfoBeans = new ArrayList<SimpleUserDetailInfoBean>();

    @Override
    public void loadData(final BaseLoadListener<SimpleUserDetailCollectBean> loadListener) {
        Observable<UserDetailCollectBean> observable1 = HttpUtils.getRetrofit().create(UserDetailCollectHttp.class).getPublicCollect();

        //加载所有收藏
        observable1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UserDetailCollectBean>() {

                    public void onNext(@NonNull UserDetailCollectBean userDetailCollectBean) {
                        Log.i(TAG, "onNext: ");
                        userDetailOnNext(userDetailCollectBean);
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
                        loadListener.loadSuccess(simpleUserDetailCollectBeans);
                        loadListener.loadComplete();
                    }
                });

    }

    private void userDetailOnNext(UserDetailCollectBean userDetailCollectBean) {
        Log.i(TAG, "onNext: ");

        List<UserDetailCollectBean.Entity> entities = userDetailCollectBean.getData();
        simpleUserDetailCollectBeans.clear();
        if (entities != null && entities.size() > 0) {
            for (UserDetailCollectBean.Entity entity : entities) {
                String title = entity.getTitle();

                SimpleUserDetailCollectBean sucb = new SimpleUserDetailCollectBean();
                sucb.title.set(title);
                simpleUserDetailCollectBeans.add(sucb);
            }
        }
    }
}
