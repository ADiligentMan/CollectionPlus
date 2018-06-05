package com.example.acer.collectionplus.Adapter;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.acer.collectionplus.BR;
import com.example.acer.collectionplus.Base.BaseAdapter;
import com.example.acer.collectionplus.Base.BaseViewHolder;
import com.example.acer.collectionplus.JavaBean.SimpleDirBean;
import com.example.acer.collectionplus.JavaBean.SimpleLinkBean;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.View.IMainFragmentView;

import java.util.List;

public class DirAdapter extends BaseAdapter<SimpleDirBean,BaseViewHolder> {
    public static  final String TAG = "DirAdapter";
    //项菜单打开和关闭
    public static final  int OPEN = 1;
    public static final int CLOSE = 2;
    private int status = CLOSE;
    public View lastOpenedView;

    private IMainFragmentView view;
    public DirAdapter(Context context,IMainFragmentView view) {
        super(context);
        this.view = view;
    }
    /**
     * 创建ViewHoler
     *
     * @param parent
     * @param viewType
     *
     * @return
     */
    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
       ViewDataBinding binding  = DataBindingUtil.inflate(inflater, R.layout.item_dir,parent,false);
       return  new BaseViewHolder(binding);
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindVH(BaseViewHolder holder, int position) {
        Log.d(TAG, "onBindVH: "+position );
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.dirBean ,dataSet.get(position));
        binding.setVariable(BR.mView,this.view);
        binding.setVariable(BR.position,position);
        binding.setVariable(BR.mAdapter,this);
    }

    /**
     * 刷新数据
     *
     * @param data
     */
    @Override
    public void refreshData(List<SimpleDirBean> data) {
        dataSet.clear();
        SimpleDirBean simpleDirBean = new SimpleDirBean();
        simpleDirBean.dirname.set("all");
        simpleDirBean.time.set("");
        dataSet.add(simpleDirBean);
        dataSet.addAll(data);
        notifyDataSetChanged();
    }



    /**
     * 移除某一项
     * @param position
     */
    public void removeItem(int position){

        dataSet.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 点击删除收藏夹
     * @param position
     */
    public void onClickDelete(final int position){
        Log.d(TAG, "onClickDelete: "+position);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setTitle("慎重考虑哦！");
        builder.setMessage("你确定要删除这个收藏夹吗？");
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
                removeItem(position);
            }
        });
        builder.setNegativeButton("取消",null);

        builder.create().show();
        //点击删除或者重命名后关闭ItemMenu
        colseItemMenu();
    }

    /**
     * 点击重命名收藏夹
     * @param position
     */
    public void onClickRename(final int position){
        Log.d(TAG, "onClickRename: "+position);
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
                EditText et_name = view.findViewById(R.id.et_dirname);
               dataSet.get(position).dirname.set(et_name.getText().toString());
            }
        });
        builder.setNegativeButton("取消",null);

        builder.create().show();
        //点击删除或者重命名后关闭ItemMenu
        colseItemMenu();

    }

    /**
     * 长按收藏夹
     * @return
     */
    public boolean onLongClick(View view){
        //如果处于打开状态，则关闭。
        if(this.status==OPEN) {
            colseItemMenu();
            this.status=CLOSE;
            return  true;
        }
        //如果处于关闭状态，则打开。
        if(this.status==CLOSE){
            lastOpenedView = view;
            openItemMenu(view);
            this.status =OPEN;
            return  true;
        }
        return  true;
    }

    /**
     * 关闭ItemMenu
     * @param view
     */
    public void openItemMenu(View view){
        if(this.status==CLOSE){
            View deleView = view.findViewById(R.id.delete_dir);
            View renameView = view.findViewById(R.id.rename_dir);
            int width = deleView.getWidth()+renameView.getWidth();
            ObjectAnimator.ofInt(view,"scrollX", view.getScrollX(),width).setDuration(500).start();
        }
    }

    /**
     * 打开ItemMenu
     *
     */
    public void colseItemMenu(){
        if(this.status==OPEN&&lastOpenedView!=null) {
            View deleView = lastOpenedView.findViewById(R.id.delete_dir);
            View renameView = lastOpenedView.findViewById(R.id.rename_dir);
            int width = deleView.getWidth() + renameView.getWidth();
            ObjectAnimator.ofInt(lastOpenedView, "scrollX", lastOpenedView.getScrollX(), 0).setDuration(500).start();
        }
    }


    public void setStatus(int status){
        this.status = status;
    }

    public int getStatus(){
        return this.status;
    }


    public void addDir(SimpleDirBean sdb){
         dataSet.add(sdb);
         notifyDataSetChanged();
    }


}

