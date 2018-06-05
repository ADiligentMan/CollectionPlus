package com.example.acer.collectionplus.ViewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.acer.collectionplus.Adapter.DirAdapter;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Helper.TimeHelper;
import com.example.acer.collectionplus.JavaBean.SimpleDirBean;
import com.example.acer.collectionplus.Model.DirModel;
import com.example.acer.collectionplus.Model.IDirModel;
import com.example.acer.collectionplus.Model.ILinkModel;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.View.IMainFragmentView;

import java.util.Date;
import java.util.List;

public class DirVM implements BaseLoadListener<SimpleDirBean>{
    IMainFragmentView view;
    IDirModel dirModel;
    DirAdapter mAdapter;
    Context mContext;
    public DirVM(Context mContext,IMainFragmentView view, DirAdapter mAdapter){
        this.mContext = mContext;
        this.view =view;
        dirModel  = new DirModel();
        this.mAdapter = mAdapter;
        initData();
    }

    private void initData(){
        dirModel.loadData(this,null);
    }
    public  void onRefreshData(){
        //刷新数据
        dirModel.loadData(this,null);
    }
    /**
     * 加载数据成功
     *
     * @param list
     */
    @Override
    public void loadSuccess(List<SimpleDirBean> list) {
        //更新数据
        mAdapter.refreshData(list);
    }

    /**
     * 加载失败
     *
     * @param message
     */
    @Override
    public void loadFailure(String message) {
        //加载失败时，提示
        view.loadFailure(message);
    }

    /**
     * 开始加载
     */
    @Override
    public void loadStart() {
        //不做任何操作，因为不需要更新界面。
    }

    /**
     * 加载结束
     */
    @Override
    public void loadComplete() {
        //结束刷新，更新界面
        view.loadComplete();
    }

    /**
     * 点击创建收藏夹按钮
     */
    public void onClickAddDir(){

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setTitle("请输入新名称");
        final View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_rename_dir,null,false);
        builder.setView(view);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){
            /**
             * This method will be invoked when a button in the dialog is clicked.
             *
             * @param dialog the dialog that received the click
             * @param which  the button that was clicked (ex.
             *               {@link DialogInterface#BUTTON_POSITIVE}) or the position
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //创建一个收藏夹。
                EditText et_name = view.findViewById(R.id.et_dirname);
                String time = TimeHelper.getFormatedTime(new Date());
                SimpleDirBean sdb = new SimpleDirBean();
                sdb.dirname.set(et_name.getText().toString());
                sdb.time.set(time);
                mAdapter.addDir(sdb);
            }
        });
        builder.setNegativeButton("取消",null);

        builder.create().show();

    }
}
