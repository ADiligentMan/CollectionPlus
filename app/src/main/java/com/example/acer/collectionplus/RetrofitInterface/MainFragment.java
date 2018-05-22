package com.example.acer.collectionplus.RetrofitInterface;

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

}
