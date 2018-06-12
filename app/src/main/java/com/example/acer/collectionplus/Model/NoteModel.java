package com.example.acer.collectionplus.Model;

import android.util.Log;

import com.example.acer.collectionplus.Base.BaseBean;
import com.example.acer.collectionplus.Helper.SharedHelper;
import com.example.acer.collectionplus.Http.HttpUtils;
import com.example.acer.collectionplus.JavaBean.NoteBean;
import com.example.acer.collectionplus.RetrofitInterface.NoteAPI;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NoteModel {
    public static final String TAG = "NoteModel";
    private NoteAPI proxy;

    public NoteModel(){
        if(proxy==null){
            proxy= HttpUtils.getRetrofit().create(NoteAPI.class);
        }
    }
    //获取所有笔记
    public void getNotes(final String linkID, final Listener<NoteBean> listener){
        String username = SharedHelper.getInstance().getValue("username");
        proxy.getNotes(username,linkID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<NoteBean>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        Log.d(TAG, "onStart: ");
                        listener.onStart();
                    }

                    @Override
                    public void onNext(NoteBean noteBean) {
                        Log.d(TAG, "onNext: ");
                        listener.onNext(noteBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                        listener.onComplete();
                    }
                });
    }

    //创建一个笔记
    public void createNote(Map<String,String> map, final Listener<BaseBean> listener){
        proxy.createNote(map)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        listener.onNext(baseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
    }

    public  abstract static class Listener<T extends BaseBean>{
        protected void onStart(){

        };
        public abstract void onNext(T mBean);
        public abstract void onError(String errorMsg);
        public abstract void onComplete();
    }


}
