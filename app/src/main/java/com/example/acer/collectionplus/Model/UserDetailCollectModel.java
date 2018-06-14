package com.example.acer.collectionplus.Model;

import android.util.Log;

import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Helper.AnalyzeHtml;
import com.example.acer.collectionplus.Helper.SharedHelper;
import com.example.acer.collectionplus.Http.HttpUtils;
import com.example.acer.collectionplus.JavaBean.RecomLinkBean;
import com.example.acer.collectionplus.JavaBean.SimpleUserBean;
import com.example.acer.collectionplus.JavaBean.SimpleUserDetailCollectBean;
import com.example.acer.collectionplus.JavaBean.SimpleUserDetailInfoBean;
import com.example.acer.collectionplus.JavaBean.UserDetailCollectBean;
import com.example.acer.collectionplus.JavaBean.UserDetailInfoBean;
import com.example.acer.collectionplus.RetrofitInterface.RecomLinkHttp;
import com.example.acer.collectionplus.RetrofitInterface.UserDetailCollectHttp;
import com.example.acer.collectionplus.RetrofitInterface.UserDetailInfoHttp;
import com.example.acer.collectionplus.View.UserDetailFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class UserDetailCollectModel implements IUserDetailCollectModel {
    private final String TAG = "UserDetailCollect";
    List<SimpleUserDetailCollectBean> simpleUserDetailCollectBeans = new ArrayList<SimpleUserDetailCollectBean>();
    List<SimpleUserDetailInfoBean> simpleUserDetailInfoBeans = new ArrayList<SimpleUserDetailInfoBean>();

    @Override
    public void loadData(final BaseLoadListener<SimpleUserDetailCollectBean> loadListener) {
        //通过shareshelper得到用户名数据
        final String username = SharedHelper.getInstance().getValue("username");
        Observable<UserDetailCollectBean> observable1 = HttpUtils.getRetrofit().create(UserDetailCollectHttp.class).getPublicCollect(username);

        //加载所有收藏
        observable1.subscribeOn(Schedulers.io())
                .map(new Function<UserDetailCollectBean, UserDetailCollectBean>() {
                    @Override
                    public UserDetailCollectBean apply(UserDetailCollectBean userDetailCollectBean) throws Exception {//如果picPath为空则从网页上解析URL
                        List<UserDetailCollectBean.Entity> entities = userDetailCollectBean.getData();
                        if (entities != null && entities.size() > 0) {
                            for (UserDetailCollectBean.Entity entity : entities) {
                                String picPath = entity.getPicPath();
                                if(picPath==null){
                                    picPath = new AnalyzeHtml().GetPath(entity.getValue());
                                    Log.d(TAG, "apply: "+picPath);
                                    entity.setPicPath(picPath);
                                }
                            }
                        }
                        return userDetailCollectBean;
                    }
                })
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
                        UserDetailFragment.userSwipeRefreshLayout.setRefreshing(false);
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
                String picPath = entity.getPicPath();
                String value = entity.getValue();
                String date = entity.getTime();

                SimpleUserDetailCollectBean sucb = new SimpleUserDetailCollectBean();
                sucb.title.set(title);
                sucb.picPath.set(picPath);
                sucb.value.set(value);
                sucb.date.set(date);
                simpleUserDetailCollectBeans.add(sucb);
            }
        }
    }
}
