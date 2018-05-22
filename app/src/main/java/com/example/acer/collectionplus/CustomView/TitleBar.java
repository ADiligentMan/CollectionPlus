package com.example.acer.collectionplus.CustomView;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.example.acer.collectionplus.BR;
import com.example.acer.collectionplus.R;

public class TitleBar extends LinearLayout {
    public ObservableField<String> title = new ObservableField<String>();
    private ViewDataBinding binding;
    private Context context;
    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context= context;
        LayoutInflater inflater = LayoutInflater.from(context);
         binding = DataBindingUtil.inflate(inflater,R.layout.back_title_bar,this,false);
         binding.setVariable(BR.customView,this);
    }

    public void finishCurrentActivity(){
        ((Activity)this.context).finish();
    }
}
