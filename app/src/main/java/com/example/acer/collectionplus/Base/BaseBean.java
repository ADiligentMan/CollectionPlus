package com.example.acer.collectionplus.Base;

import java.util.List;

public class BaseBean <T>{
    protected String info;
    protected boolean success;
    protected List<T> data;
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
