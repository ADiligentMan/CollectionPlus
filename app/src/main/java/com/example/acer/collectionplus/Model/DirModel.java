package com.example.acer.collectionplus.Model;

import android.util.Log;

import com.example.acer.collectionplus.Adapter.DirAdapter;
import com.example.acer.collectionplus.Base.BaseBean;
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
    private MainFragment proxy ;
    private BaseLoadListener loadListener;

    public DirModel(){
        if(proxy==null){
            proxy = HttpUtils.getRetrofit().create(MainFragment.class);
        }

    }
    /**
     * 刷新后加载所有收藏夹
     *
     *
     * @param loadListener
     * @param params
     */
    @Override
    public void loadData(final BaseLoadListener<SimpleDirBean> loadListener, Map<String, String> params) {
        this.loadListener = loadListener;
        String username = SharedHelper.getInstance().getValue("username");

        Observable<DirBean> observable = proxy.getDirList(username);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<DirBean>() {

                    public void onNext(@NonNull DirBean dirBean) {
                        Log.i(TAG, "onNext: ");
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
     * 重名名收藏夹
     * @param oldname  旧名称
     * @param newname   新名称
     */
    public void renameDir(String oldname,String newname){
        String username = SharedHelper.getInstance().getValue("username");
        proxy.renameDir(username,oldname,newname)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        isSuccess(baseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadListener.loadFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 删除收藏夹
     * @param dirname 收藏夹名称
     */
    public void deleteDir(String dirname){
        String username = SharedHelper.getInstance().getValue("username");
        proxy.deleteDir(username,dirname)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        isSuccess(baseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadListener.loadFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 创建收藏夹
     * @param dirname
     */
    public void createDir(String dirname){
        String username = SharedHelper.getInstance().getValue("username");
        proxy.createDir(username,dirname,"非清单")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        isSuccess(baseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadListener.loadFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 判断后台处理是否成功
     * @param baseBean
     */
    private void isSuccess(BaseBean baseBean){
        boolean isSuccess = baseBean.isSuccess();
        String Info = baseBean.getInfo();
        Log.d(TAG, "isSuccess: "+isSuccess+":"+Info+":"+baseBean.getData());
        if((!isSuccess)&&(loadListener!=null)){
            loadListener.loadFailure("Back end fails-"+Info);
        }
    }

    /**
     * 把dirBean转化为SimpleDirBean
     *
     * @param dirBean
     */
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
