package com.example.acer.collectionplus.SqlLiteUtils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.acer.collectionplus.JavaBean.HistoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2018/6/12.
 * 浏览历史数据库操作方法
 */

public class History_DBUtils {
private SQLiteDatabase db;

    public History_DBUtils(SQLiteDbHelper sqLiteDbHelper) {
        this.db=sqLiteDbHelper.getWritableDatabase();
    }




    //插入浏览历史记录
    public void insert_hsitory(int id,String value,String time){
        ContentValues values=new ContentValues();
        //插入数据
        values.put("id",id);
        values.put("value",value);
        values.put("time",time);
        this.db.insert("history",null,values);
        Log.d("插入数据","success");
    }

    //查询浏览记录
    public List<HistoryBean> queryhistory(){
        Log.d("查询历史","success");
        List<HistoryBean> historylist=new ArrayList<HistoryBean>();
        HistoryBean history_cursor=new HistoryBean();
        Cursor cursor=db.query("history",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                String value=cursor.getString(cursor.getColumnIndex("value"));
                String time=cursor.getString(cursor.getColumnIndex("time"));
                history_cursor.setValue(value);
                history_cursor.setTime(time);
                historylist.add(history_cursor);

            }while (cursor.moveToNext());
        }
        cursor.close();
        Log.d("historyList",historylist.toString());
        return historylist;


    }
}

