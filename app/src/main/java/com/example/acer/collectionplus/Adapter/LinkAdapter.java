package com.example.acer.collectionplus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.example.acer.collectionplus.BR;
import com.example.acer.collectionplus.Base.BaseAdapter;
import com.example.acer.collectionplus.Base.BaseViewHolder;
import com.example.acer.collectionplus.JavaBean.SimpleLinkBean;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.SqlLiteUtils.History_DBUtils;
import com.example.acer.collectionplus.SqlLiteUtils.SQLiteDbHelper;
import com.example.acer.collectionplus.View.NoteActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class LinkAdapter extends BaseAdapter<SimpleLinkBean,BaseViewHolder>{

    private String history_time;
    private SQLiteDbHelper sqLiteDbHelper;
    private Context context;
    private History_DBUtils hisdao;
    private int id=0;
    public LinkAdapter(Context context) {
        super(context);
        this.context=context;
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
        ViewDataBinding binding = DataBindingUtil.inflate(inflater,R.layout.item_link,parent,false);
        if(sqLiteDbHelper==null){
            sqLiteDbHelper = new SQLiteDbHelper(context);
            hisdao=new History_DBUtils(sqLiteDbHelper);
        }
        return new BaseViewHolder(binding);


    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindVH(BaseViewHolder holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.mBean,dataSet.get(position));
        binding.setVariable(BR.mAdapter,this);
        binding.setVariable(BR.position,position);
    }

    /**
     * 点击小三角，隐藏按钮，更换小三角图标
     * @param position
     */
    public void clickSmallTtiangle(int position){
        SimpleLinkBean simpleLinkBean = dataSet.get(position);
        if(simpleLinkBean.isGone.get()){
            simpleLinkBean.isGone.set(false);
        }else {
            simpleLinkBean.isGone.set(true);
        }
    }

    /**
     * 点击网页后开启一个活动
     *
     * @param value 网页
     * @param position 点击的位置
     *
     */
    public void clickLink(String value,int position){
        Log.d("点击事件","success");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(value));
        mContext.startActivity(intent);

//获取系统的日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        history_time= simpleDateFormat.format(date);

        //获取主键
        hisdao.insert_hsitory(id,value,history_time);
    }

    /**
     *
     * @param position
     */
   public void onClickNote(int position){
        String linkID = dataSet.get(position).ID.get();
        NoteActivity.actionStart(mContext,linkID);
    }
}
