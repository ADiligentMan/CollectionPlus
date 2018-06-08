package com.example.acer.collectionplus.View;

import com.example.acer.collectionplus.Base.IBaseView;

public interface IEnsureCodeView  extends IBaseView {
    String getEmail();
    void serEmailError();
    void setResend(Long aLong);
    void DisabledCode();
    void completeCode();
    String getActiveCode();
    void setNextError();
    int getViewType();
}
