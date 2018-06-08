package com.example.acer.collectionplus.RetrofitInterface;

import com.example.acer.collectionplus.Constant.URLConstant;
import com.example.acer.collectionplus.JavaBean.UserDetailCollectBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UserDetailCollectHttp {
    @GET(URLConstant.URL_GET_USER_PUBLIC_COLLECT)
    Observable<UserDetailCollectBean> getPublicCollect();
}
