package com.example.acer.collectionplus.RetrofitInterface;

import com.example.acer.collectionplus.Constant.URLConstant;
import com.example.acer.collectionplus.JavaBean.UserDetailCollectBean;
import com.example.acer.collectionplus.JavaBean.UserDetailInfoBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UserDetailInfoHttp {
    @GET(URLConstant.URL_GET_USER_DETAIL_INFO)
    Observable<UserDetailInfoBean> getUserDetailInfo();
}
