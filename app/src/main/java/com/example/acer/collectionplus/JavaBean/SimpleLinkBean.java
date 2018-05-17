package com.example.acer.collectionplus.JavaBean;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

public class SimpleLinkBean {
    public ObservableField<String> dirname = new ObservableField<String>();
    public ObservableField<String> picPath = new ObservableField<String>();
    public ObservableField<String>  value = new ObservableField<String>();
    public ObservableBoolean read = new ObservableBoolean();
    public ObservableField<String> title = new ObservableField<String>();
    public ObservableField<String>  time = new ObservableField<String>();
    public ObservableBoolean isGone = new ObservableBoolean();
}
