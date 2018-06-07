package com.example.acer.collectionplus.RetrofitInterface;

import com.example.acer.collectionplus.Base.BaseBean;
import com.example.acer.collectionplus.Constant.URLConstant;
import com.example.acer.collectionplus.JavaBean.DirBean;
import com.example.acer.collectionplus.JavaBean.UserBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by asus on 2018/5/19.
 */

public interface UserFragmentImpl {
    @FormUrlEncoded
    @POST(URLConstant.URL_MODIFYUSER)
    Observable<BaseBean>  modifyUser(@FieldMap Map<String ,String> usermap);
    //请求用户数据
    @GET(URLConstant.URL_USERINFO)
    Observable<UserBean> getUserInfo(@Query("username") String username);
    //修改个人信息



}
