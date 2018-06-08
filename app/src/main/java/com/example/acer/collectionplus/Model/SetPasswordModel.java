package com.example.acer.collectionplus.Model;

import android.util.Log;

import com.example.acer.collectionplus.Base.BaseBean;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Helper.SharedHelper;
import com.example.acer.collectionplus.Http.HttpUtils;
import com.example.acer.collectionplus.JavaBean.ResultBean;
import com.example.acer.collectionplus.RetrofitInterface.MainFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SetPasswordModel  {
    public static final String TAG="SetPasswordModel";
    List<ResultBean> list=new ArrayList<ResultBean>();
    ResultBean result=new ResultBean();
    public void loadData(final BaseLoadListener<ResultBean> loadListener, Map<String, String> params){
        String password=params.get("password");
        String email = SharedHelper.getInstance().getValue("email");
        Log.i(TAG,"邮箱为"+email);

        //向服务器发送数据??
        Observable<BaseBean> observable = HttpUtils.getRetrofit().create(MainFragment.class).getResultPassword(email,password);
        //两个线程，一个用来给服务器发送请求，一个是主线程负责界面更新
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BaseBean>(){
                    @Override
                    public void onNext(BaseBean baseBean) {
                        Log.i(TAG, "onNext: ");
                        result.success1=baseBean.isSuccess();
                        result.info1=baseBean.getInfo();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: "+e.getMessage());
                        loadListener.loadFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                        // loadListener.loadComplete();
                        list.add(result);
                        loadListener.loadSuccess(list);
                    }
                });
    }
}
