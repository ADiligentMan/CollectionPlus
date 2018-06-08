package com.example.acer.collectionplus.RetrofitInterface;

import com.example.acer.collectionplus.Base.BaseBean;
import com.example.acer.collectionplus.Constant.URLConstant;
import com.example.acer.collectionplus.JavaBean.DirBean;
import com.example.acer.collectionplus.JavaBean.LinkBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainFragment {
    /**
     * 请求收藏列表
     * @param username  用户名
     * @param dirname 收藏夹名称
     * @param pagenumber 页数
     * @return
     */
    @GET(URLConstant.URL_LINKLIST)
    Observable<LinkBean>  getLinkList(@Query("username") String username,@Query("dirname") String dirname,@Query("page")String pagenumber);

    /**
     * 请求收藏夹的列表
     *
     * @param username 用户名
     * @return
     */
    @GET(URLConstant.URL_DIRLIST)
    Observable<DirBean> getDirList(@Query("username") String username);

    @GET(URLConstant.URL_LOGIN)
    Observable<BaseBean> getResult(@Query("username") String username, @Query("password") String password);

    //发送验证码
    @GET(URLConstant.URL_SENDEMAIL)
    Observable<BaseBean> getResultEnsureCode(@Query("email") String email,@Query("type") String type);

    //注册

    @GET(URLConstant.URL_REGISTER)
    Observable<BaseBean> getResultSign(@Query("username") String username, @Query("password") String password,@Query("email") String email, @Query("activeCode") String activeCode);

    //忘记密码1
    @GET(URLConstant.URL_CHECKENSURECODE)
    Observable<BaseBean> getResultCheckEn(@Query("email") String email, @Query("activeCode") String activeCode);

    //忘记密码
    @GET(URLConstant.URL_MODIFYPASSWORD)
    Observable<BaseBean> getResultPassword(@Query("email") String email, @Query("password") String password);

    //重命名收藏夹
    @GET("user/renamedir.do")
    Observable<BaseBean> renameDir(@Query("username")String username,@Query("olddirname") String olddirname,@Query("newdirname") String newdirname);

    //新建收藏夹
    @GET("user/createdir.do")
    Observable<BaseBean> createDir(@Query("username")String username,@Query("dirname") String dirname,@Query("type") String type);

    //删除收藏夹
    @GET("user/deletedir.do")
    Observable<BaseBean> deleteDir(@Query("username")String username,@Query("dirname") String dirname);
}



