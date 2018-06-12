package com.example.acer.collectionplus.Model;

import android.util.Log;

import com.example.acer.collectionplus.Adapter.UserDetailAdapter;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Http.HttpUtils;
import com.example.acer.collectionplus.JavaBean.SimpleUserDetailCollectBean;
import com.example.acer.collectionplus.JavaBean.SimpleUserDetailInfoBean;
import com.example.acer.collectionplus.JavaBean.UserDetailInfoBean;
import com.example.acer.collectionplus.Model.UserDetailCollectModel;
import com.example.acer.collectionplus.RetrofitInterface.UserDetailInfoHttp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class UserDetailInfoModel implements IUserDetailInfoModel {

    private final String TAG = "UserDetailInfo";
    List<SimpleUserDetailInfoBean> userDetailInfoBeans = new ArrayList<SimpleUserDetailInfoBean>();
    List<SimpleUserDetailInfoBean> simpleUserDetailInfoBeans = new ArrayList<SimpleUserDetailInfoBean>();

    @Override
    public void loadData(final BaseLoadListener<SimpleUserDetailInfoBean> loadListener) {
        Observable<UserDetailInfoBean> observable1 = HttpUtils.getRetrofit().create(UserDetailInfoHttp.class).getUserDetailInfo();

        //加载所有收藏
        observable1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UserDetailInfoBean>() {

                    public void onNext(@NonNull UserDetailInfoBean userDetailInfoBean) {
                        Log.i(TAG, "onNext: ");
                        userDetailInfoOnNext(userDetailInfoBean);
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
                        loadListener.loadSuccess(simpleUserDetailInfoBeans);
                        loadListener.loadComplete();
                    }
                });

    }

    private void userDetailInfoOnNext(UserDetailInfoBean userDetailInfoBean) {
        Log.i(TAG, "onNext: ");

        List<UserDetailInfoBean.Entity> entities = userDetailInfoBean.getData();
        simpleUserDetailInfoBeans.clear();
        if (entities != null && entities.size() > 0) {
            for (UserDetailInfoBean.Entity entity : entities) {
                String collectNum = entity.getCollectNum();
                String folderNum = entity.getFolderNum();
                String fansNum = entity.getFansNum();
                String followNum = entity.getFollowNum();

                SimpleUserDetailInfoBean sucb = new SimpleUserDetailInfoBean();
                sucb.collectNum.set(collectNum);
                sucb.folderNum.set(folderNum);
                sucb.fansNum.set(fansNum);
                sucb.followNum.set(followNum);
                simpleUserDetailInfoBeans.add(sucb);
            }
        }
    }
}
