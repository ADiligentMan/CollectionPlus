package com.example.acer.collectionplus.RetrofitInterface;

import com.example.acer.collectionplus.Base.BaseBean;
import com.example.acer.collectionplus.JavaBean.NoteBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NoteAPI {
    @GET("user/getnotes.do")
    Observable<NoteBean> getNotes(@Query("username") String username,@Query("linkID") String linkID);

    @FormUrlEncoded
    @POST("user/createnote.do")
    Observable<BaseBean> createNote(@FieldMap Map<String,String> map);

    @GET("user/deletenote.do")
    Observable<BaseBean> deleteNote(@Query("username") String username,@Query("ID")String ID);

    @FormUrlEncoded
    @POST("user/modifynote.do")
    Observable<BaseBean> modifyNote(@FieldMap Map<String ,String > map);
}
