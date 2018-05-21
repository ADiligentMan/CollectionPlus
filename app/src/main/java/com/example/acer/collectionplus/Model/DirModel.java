package com.example.acer.collectionplus.Model;

import android.util.Log;

import com.example.acer.collectionplus.Adapter.DirAdapter;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Helper.SharedHelper;
import com.example.acer.collectionplus.Helper.TimeHelper;
import com.example.acer.collectionplus.Http.HttpUtils;
import com.example.acer.collectionplus.JavaBean.DirBean;
import com.example.acer.collectionplus.JavaBean.SimpleDirBean;
import com.example.acer.collectionplus.JavaBean.SimpleLinkBean;
import com.example.acer.collectionplus.RetrofitInterface.MainFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DirModel implements IDirModel{
    public static final  String  TAG = "DirModel" ;
    private List<SimpleDirBean> simpleDirBeanList = new ArrayList<SimpleDirBean>();
    /**
     * 刷新后加载所有收藏夹
     *
     *
     * @param loadListener
     * @param params
     */
    @Override
    public void loadData(final BaseLoadListener<SimpleDirBean> loadListener, Map<String, String> params) {
        String username = SharedHelper.getInstance().getValue("username");

        Observable<DirBean> observable = HttpUtils.getRetrofit().create(MainFragment.class).getDirList(username);


        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<DirBean>() {

                    public void onNext(@NonNull DirBean dirBean) {
                        Log.i(TAG, "onNext: ");
                        //转化成simplebean

                        DirOnNext(dirBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: "+e.getMessage());
                        loadListener.loadFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                        loadListener.loadSuccess(simpleDirBeanList);
                        loadListener.loadComplete();
                    }
                });
    }

    /**
     * 把dirBean转化为SimpleDirBean
     *
     * @param dirBean
     */
    //通知页面自动更新
    private void DirOnNext(DirBean dirBean) {
        List<DirBean.Entity> entities = dirBean.getData();
        simpleDirBeanList.clear();
        if (entities != null && entities.size() > 0) {
            for (DirBean.Entity entity : entities) {
                String dirname = entity.getDirname();
                String time = TimeHelper.getFormatedTime(entity.getTime());
                SimpleDirBean sdb = new SimpleDirBean();
                sdb.dirname.set(dirname);
                sdb.time.set(time);
                simpleDirBeanList.add(sdb);
            }
        }

    }
}
