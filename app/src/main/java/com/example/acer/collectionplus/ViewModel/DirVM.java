package com.example.acer.collectionplus.ViewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.ViewDataBinding;
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
import com.example.acer.collectionplus.BR;
import com.example.acer.collectionplus.View.IMainFragmentView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DirVM implements BaseLoadListener<SimpleDirBean>{
    public static final String  TAG ="DirVM";
    IMainFragmentView view;
    DirModel dirModel;
    DirAdapter mAdapter;
    Context mContext;
    ViewDataBinding binding;

    public DirVM(Context mContext, IMainFragmentView view, DirAdapter mAdapter,ViewDataBinding binding){
        this.binding = binding;
        this.mContext = mContext;
        this.view =view;
        dirModel  = new DirModel();
        this.mAdapter = mAdapter;
        this.mAdapter.setModel(dirModel);
        binding.setVariable(BR.mDirVM,this);
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
     * 点击创建文件夹按钮
     */
    public void onClickAddDir() {
        Log.d(TAG, "onClickAddDir: ");
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(R.mipmap.ic_launcher2);
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
                //获取新建收藏夹的信息
                EditText et_name = view.findViewById(R.id.et_dirname);
                String dirname = et_name.getText().toString();
                String time = TimeHelper.getFormatedTime(new Date());
                //跟新前端显示
                SimpleDirBean bean = new SimpleDirBean();
                bean.dirname.set(et_name.getText().toString());
                bean.time.set(TimeHelper.getFormatedTime(new Date()));
                bean.isClicked.set(false);
                List<SimpleDirBean> beans = new ArrayList<>();
                beans.add(bean);
                mAdapter.loadMoreData(beans);
                //跟新后台
                dirModel.createDir(dirname);
            }
        });
        builder.setNegativeButton("取消",null);

        builder.create().show();

    }
}
