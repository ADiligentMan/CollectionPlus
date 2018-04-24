package com.example.acer.myapplication.java_class;

import java.util.Date;
import java.util.PriorityQueue;

/**
 * Created by asus on 2018/4/10.
 */

public class Item {
    //定义成员变量
    private String picPath;
    private String value;
    private boolean read;
    private String title;
    private String source;
    private String time;
    private String type;

    public Item(){}
    //构造方法
    public Item(String picPath,String value,boolean read,String title,String source,String time,String type) {
        this.picPath=picPath;
        this.value=value;
        this.read=read;
        this.title=title;
        this.source=source;
        this.time=time;
        this.type=type;

    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
//    public String getDirname() {
//        return dirname;
//    }
//
//    public void setDirname(String dirname) {
//        this.dirname = dirname;
//    }

}


