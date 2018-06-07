package com.example.acer.collectionplus.RetrofitInterface;

import com.example.acer.collectionplus.Constant.URLConstant;
import com.example.acer.collectionplus.JavaBean.RecomLinkBean;
import com.example.acer.collectionplus.JavaBean.RecomUserBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RecomLinkHttp {
    @GET(URLConstant.URL_GET_RECOM_LINK)
    Observable<RecomLinkBean> getRecomLink();
}
