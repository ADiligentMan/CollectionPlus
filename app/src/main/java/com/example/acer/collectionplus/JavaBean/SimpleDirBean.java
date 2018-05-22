package com.example.acer.collectionplus.JavaBean;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

public class SimpleDirBean {
    public ObservableField<String> dirname = new ObservableField<String>();
    public ObservableField<String> time = new ObservableField<String>();
    public ObservableBoolean isClicked = new ObservableBoolean();
}
