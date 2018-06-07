package com.example.acer.collectionplus.View;

import com.example.acer.collectionplus.Base.IBaseView;

public interface ISetPasswordView extends IBaseView {
    String getFirstCode();
    String getSecondCode();
    void setErrorView(String msg);
    void setErrorEnsure(String msg);
}
