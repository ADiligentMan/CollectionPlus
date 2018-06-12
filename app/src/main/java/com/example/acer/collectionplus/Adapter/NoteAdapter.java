package com.example.acer.collectionplus.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acer.collectionplus.Base.BaseAdapter;
import com.example.acer.collectionplus.Base.BaseViewHolder;
import com.example.acer.collectionplus.JavaBean.SimpleNoteBean;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.BR;

public class NoteAdapter extends BaseAdapter<SimpleNoteBean,BaseViewHolder> {
    public static final String  TAG ="NoteAdapter";
    public NoteAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        ViewDataBinding binding  = DataBindingUtil.inflate(inflater, R.layout.item_note,parent,false);
        return  new BaseViewHolder(binding);
    }

    @Override
    public void onBindVH(BaseViewHolder holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.mNoteBean,dataSet.get(position));
        binding.setVariable(BR.mNoteAdapter,this);
        binding.setVariable(BR.mNotePosition,position);
        binding.setVariable(BR.mNoteView,binding.getRoot());
    }

    /**
     * 点击展开或者合起
     * @param view
     * @param position
     */
    public void onClickOpenOrClose(View view ,int position){
        TextView note_open_or_close = (TextView) view.findViewById(R.id.note_open_or_close);
        TextView tv_note_content = (TextView)view.findViewById(R.id.tv_note_content);
        //点击展开
        if(note_open_or_close.getText().toString().equals("展开")){
            Log.d(TAG, "onClickOpenOrClose: ");
            note_open_or_close.setText("合起");
            tv_note_content.setMaxLines(100);
            tv_note_content.requestLayout();
            return ;
        }
        //点击合起
        if(note_open_or_close.getText().toString().equals("合起")){
            Log.d(TAG, "onClickOpenOrClose: ");
            note_open_or_close.setText("展开");
            tv_note_content.setMaxLines(2);
            tv_note_content.requestLayout();
            return ;
        }
    }

}
