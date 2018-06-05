package com.example.acer.collectionplus.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.acer.collectionplus.Base.BaseBean;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Http.HttpUtils;
import com.example.acer.collectionplus.JavaBean.ResultBean;
import com.example.acer.collectionplus.RetrofitInterface.MainFragment;
import com.example.acer.collectionplus.ViewModel.EnsureCodeVM;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class EnsureCodeModel {
    public static final String TAG="EnsureCodeModel";

    List<ResultBean> list=new ArrayList<ResultBean>();
    ResultBean result=new ResultBean();;
    public void loadData(final BaseLoadListener<ResultBean> loadListener, Map<String, String> params){
        String email=params.get("email");

        //向服务器发送数据??
        Observable<BaseBean> observable = HttpUtils.getRetrofit().create(MainFragment.class).getResultEnsureCode(email);
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
                        list.add(result);
                        loadListener.loadSuccess(list);
                        loadListener.loadComplete();
                    }
                });
        }

        //倒计时事件
    public void onCode(final EnsureCodeVM loadListener){
        //倒计时为60秒
        final long count=60;

        //从0秒开始，每延时1秒执行一次
        Observable.interval(0,1, TimeUnit.SECONDS)
        .take(count+1) //take()超过多少停止执行
        .map(new Function<Long, Long>() {//类型转换
            @Override
            public Long apply(Long aLong) throws Exception {
                return count-aLong;
            }

        })
        .observeOn(AndroidSchedulers.mainThread())//事件在主线程中消费
        .doOnSubscribe(new Consumer<Disposable>() {//在执行的过程中做的事件
            @Override
            public void accept(Disposable disposable) throws Exception {
                    loadListener.buttonCode();

            }
        })
        .subscribe(new Observer<Long>() {//另一个订阅事件\
                       @Override
                       public void onSubscribe(Disposable d) {
                       }

                       @Override
                       public void onNext(Long aLong) {
                           loadListener.buttonReCode(aLong);
                       }

                       @Override
                       public void onError(Throwable e) {
                        //   stopCoding = 1;

                       }

                       @Override
                       public void onComplete() {
                           loadListener.buttonComplete();
                       }
                   }
        );




    }

}
