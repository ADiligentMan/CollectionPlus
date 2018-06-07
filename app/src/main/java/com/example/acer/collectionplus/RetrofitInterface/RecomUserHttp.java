package com.example.acer.collectionplus.RetrofitInterface;

import com.example.acer.collectionplus.Constant.URLConstant;
import com.example.acer.collectionplus.JavaBean.RecomUserBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecomUserHttp {
    @GET(URLConstant.URL_GET_RECOM_USER)
    Observable<RecomUserBean> getRecomUser();
}
