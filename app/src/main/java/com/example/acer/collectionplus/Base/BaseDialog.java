package com.example.acer.collectionplus.Base;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public abstract class BaseDialog<T extends  Dialog> {
    protected T mDialog;
    protected Activity activity;
    public BaseDialog( Activity activity){
        this.activity = activity;

    }

    public void show( String msg) {
        closeSelf();
        createDialog(msg);
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    /**
     * 关闭加载框
     */
    public void closeSelf() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog =null;
        }
    }
    /**
     *  创建一个dialog
     */
    protected abstract void createDialog(String msg);
}
