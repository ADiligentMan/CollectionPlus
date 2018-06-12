package com.example.acer.collectionplus.JavaBean;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

public class SimpleNoteBean {
    public ObservableInt  ID = new ObservableInt();
    public ObservableInt linkID = new ObservableInt();
    public ObservableField<String> title = new ObservableField<String>();
    public ObservableField<String> username = new ObservableField<String>();
    public ObservableField<String> content = new ObservableField<String>();
    public ObservableField<String> time = new ObservableField<String>();
}
