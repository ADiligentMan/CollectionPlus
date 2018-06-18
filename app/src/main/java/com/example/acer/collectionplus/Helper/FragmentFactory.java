package com.example.acer.collectionplus.Helper;

import android.support.v4.app.Fragment;

import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.View.MainFragment;

public class FragmentFactory {
    public static Fragment createFragment(int checkedId){
        Fragment fragment = null;
        switch (checkedId){
            case R.id.bottom_home:
                break;
            case R.id.bottom_find:
                break;
            case R.id.bottom_mime:
                break;
        }
        return fragment;
    }

}
