package com.example.acer.collectionplus.SqlLiteUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by asus on 2018/6/12.
 * SQLlite操作类
 */

public class SQLiteDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "history.db";

    public static final int DB_VERSION = 1;

    public static final String TABLE_HISTORY = "history";



    //创建 students 表的 sql 语句
    private static final String HISTORY_CREATE_TABLE_SQL = "create table " + TABLE_HISTORY + "("
            + "id integer primary key autoincrement,"
            + "value varchar(20) not null,"
            + "time varchar(50) not null"
            + ");";
    //创建该对象
    public SQLiteDbHelper(Context context) {
        // 传递数据库名与版本号给父类
        super(context, DB_NAME, null, DB_VERSION);

    }

//自动调用oncreate方法
    @Override
    public void onCreate(SQLiteDatabase db) {

        // 在这里通过 db.execSQL 函数执行 SQL 语句创建所需要的表
        // 创建 history 表
        db.execSQL(HISTORY_CREATE_TABLE_SQL);
        Log.d("创建表","success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // 数据库版本号变更会调用 onUpgrade 函数，在这根据版本号进行升级数据库
        switch (oldVersion) {
            case 1:
                // do something
                break;

            default:
                break;
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // 启动外键
            db.execSQL("PRAGMA foreign_keys = 1;");


            String query = String.format("PRAGMA foreign_keys = %s", "ON");
            db.execSQL(query);
        }
    }

}

