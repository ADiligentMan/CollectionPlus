package com.example.acer.myapplication.activity.service;

/**
 * Created by admin on 2018/4/12.
 */

public class resultEnd {
    private Object data;             //返回的数据
    private boolean isAvaibleNet;  //网络是否可用
    private boolean success;        //返回是否成功
    private String info;              //信息

    public resultEnd() {
        this.isAvaibleNet = true;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isAvaibleNet() {
        return isAvaibleNet;
    }

    public void setAvaibleNet(boolean avaibleNet) {
        isAvaibleNet = avaibleNet;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
