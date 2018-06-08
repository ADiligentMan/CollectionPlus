package com.example.acer.collectionplus.View;

import android.view.View;

import com.example.acer.collectionplus.Base.IBaseView;

public interface IMainFragmentView extends IBaseView{
    /**
     * 点击收藏夹的事件处理（切换收藏夹）
     *
     * @param view dir Item
     * @param dirname 收藏夹名称
     * @param position 收藏夹位置
     */
     void changeDir(View view,String dirname, int position);

}
