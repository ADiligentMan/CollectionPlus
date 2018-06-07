package com.example.acer.collectionplus.View;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.acer.collectionplus.R;

public class UserDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_info, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.toolbarId);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        return rootView;
    }
}
