package com.example.acer.collectionplus.View;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

/**
 * Created by asus on 2018/5/31.
 */

public class BaseFragment extends Fragment {
    private Activity activity;

    public Context getContext(){
        if(activity == null){
            return MyApplication.getInstance();
        }
        return activity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity = getActivity();
    }
}