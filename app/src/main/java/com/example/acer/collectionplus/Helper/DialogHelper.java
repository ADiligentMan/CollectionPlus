package com.example.acer.collectionplus.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.acer.collectionplus.Base.BaseDialog;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.View.AddActivity;
import com.example.acer.collectionplus.ViewModel.MainVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mj
 * on 2017/5/22.
 */

public class DialogHelper {
    //dialog的类型。1加载框，2添加链接框，3确认框，4重命名框
    public static  final int LOAD_DIALOG = 1;
    public static final int ADD_DIALOG =2;
    public static final int CONFIRM_DIALOG=3;
    public static final int RENAME_DIALOG=4;
    public static DialogHelper getInstance() {
        return DialogHelper.instance;
    }


    private  static DialogHelper instance = new DialogHelper();


    private BaseDialog currDialog;
    private DialogHelper(){

    }

    /**
     *
     * @param activity
     * @param msg 显示的信息
     * @param type Dialog的类型
     */
    public void show( Activity activity,String msg,int type) {
       if(type == LOAD_DIALOG){
           currDialog = new LaodDialog(activity);
       }else if(type==ADD_DIALOG){
           currDialog = new AddDialog(activity);
       }
        currDialog.show(msg);
    }

    /**
     * 关闭Diolog
     */
    public void close(){
        if(currDialog!=null) {
            currDialog.closeSelf();
        }
    }

    /**
     * 加载框
     */
    public class LaodDialog extends BaseDialog<ProgressDialog>{
        public LaodDialog(Activity activity) {
            super(activity);
        }

        @Override
        protected void createDialog( String msg) {
            mDialog = new ProgressDialog(activity);
            mDialog.setCancelable(false);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setMessage(msg);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                   mDialog = null;
                }
            });
        }
    }

    /**
     * 剪贴板内容改变后弹出的框。
     */
    public class  AddDialog extends BaseDialog<Dialog>{
        public AddDialog(Activity activity) {
            super(activity);
        }

        @Override
        protected void createDialog(final String msg) {
             mDialog = new Dialog(activity, R.style.BottomDialog);
            View contentView = LayoutInflater.from(activity).inflate(R.layout.bottomdialog, null);
            mDialog.setCancelable(false);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    closeSelf();
                }
            });



            TextView textView = contentView.findViewById(R.id.newCopy);
            textView.setText(msg);

            mDialog.setContentView(contentView);
            ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
            layoutParams.width = activity.getResources().getDisplayMetrics().widthPixels;
            contentView.setLayoutParams(layoutParams);
            mDialog.getWindow().setGravity(Gravity.BOTTOM);
            mDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);

            // 保存和放弃的监听事件
            Button okBtn = contentView.findViewById(R.id.save);
            Button cancelBtn = contentView.findViewById(R.id.quit);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeSelf();
                }
            });
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //启动添加信息的界面。
                    AddActivity.actionStart(activity,msg);
                }
            });
        }
    }

}

