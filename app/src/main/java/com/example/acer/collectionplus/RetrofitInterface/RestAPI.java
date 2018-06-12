package com.example.acer.collectionplus.RetrofitInterface;



import com.example.acer.collectionplus.Base.BaseBean;
import com.example.acer.collectionplus.Constant.URLConstant;
import com.example.acer.collectionplus.JavaBean.DirBean;
import com.example.acer.collectionplus.JavaBean.LinkBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestAPI {
    @FormUrlEncoded
    @POST("user/saveLink.do")
    Observable<BaseBean> saveLink(@FieldMap  Map<String, String> fields);

    @GET(URLConstant.URL_DIRLIST)
    Observable<DirBean> getDirList(@Query("username") String username);


}
