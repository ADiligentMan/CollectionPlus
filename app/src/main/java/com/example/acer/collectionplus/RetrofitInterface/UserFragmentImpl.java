package com.example.acer.collectionplus.RetrofitInterface;

import com.example.acer.collectionplus.Constant.URLConstant;
import com.example.acer.collectionplus.JavaBean.DirBean;
import com.example.acer.collectionplus.JavaBean.UserBean;

import io.reactivex.Observable;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by asus on 2018/5/19.
 */

public interface UserFragmentImpl {
    //请求用户数据
    @GET(URLConstant.URL_USERINFO)
   Observable<UserBean> getUserInfo(@Query("username") String username);

}
