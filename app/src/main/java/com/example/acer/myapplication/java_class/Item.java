package com.example.acer.myapplication.java_class;

import java.util.PriorityQueue;

/**
 * Created by asus on 2018/4/10.
 */

public class Item {
    //定义成员变量
    private  String link_name;
    private int link_image;
    private  String link_time;
    private String link_package;
     //构造方法
    public Item(String link_name,int link_image,String link_time,String link_package) {
        this.link_name=link_name;
        this.link_image=link_image;
        this.link_time=link_time;
        this.link_package=link_package;
    }

    public Item(String link_name) {
        this.link_name = link_name;
    }

    public String getLink_name() {
        return link_name;
    }

    public void setLink_name(String link_name) {
        this.link_name = link_name;
    }

    public int getLink_image() {
        return link_image;
    }

    public void setLink_image(int link_image) {
        this.link_image = link_image;
    }

    public String getLink_time() {
        return link_time;
    }

    public void setLink_time(String link_time) {
        this.link_time = link_time;
    }

    public String getLink_package() {
        return link_package;
    }

    public void setLink_package(String link_package) {
        this.link_package = link_package;
    }
}


