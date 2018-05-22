package com.example.acer.collectionplus.View;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.acer.collectionplus.Adapter.UserAdapter;

import com.example.acer.collectionplus.R;

import com.example.acer.collectionplus.ViewModel.UserVM;
import com.example.acer.collectionplus.databinding.FragmentMainBinding;
import com.example.acer.collectionplus.databinding.FragmentUserBinding;


public class UserFragment extends Fragment implements IUserFragmentView
{

    public static final String TAG = "UserFragment";
    private  FragmentUserBinding binding;
    private UserAdapter userAdapter;
    private UserVM userVM;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_user,container,false);
       // initDirRecyclView();.
       // initLinkRecycleView();
        this.userVM = new UserVM(this,userAdapter);
        //将binding传给VM层

         userVM.getbinding(binding);
        return binding.getRoot();
    }
    @Override
    public void loadStart(int loadType) {
    }

    @Override
    public void loadComplete() {

    }

    @Override
    public void loadFailure(String message) {

    }
}
