package com.example.acer.collectionplus.View;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.acer.collectionplus.Helper.SharedHelper;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.ViewModel.ChangeVM;
import com.example.acer.collectionplus.ViewModel.UserVM;
import com.example.acer.collectionplus.databinding.FragmentChangeBinding;

import java.util.HashMap;
import java.util.Map;


public class ChangeFragment extends Fragment implements IUserFragmentView {
    FragmentChangeBinding binding;
    public static final String TAG = "ChangeFragment";
    private ChangeVM changevm;
    private Map<String,String> usermap;
    private Button ok_Btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //创建对象
        this.changevm = new ChangeVM(this);
        binding =(FragmentChangeBinding) DataBindingUtil.inflate(inflater, R.layout.fragment_change,container,false);
        ok_Btn=binding.ChangeButton;

        //点击事件传入map
         ok_Btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //向usermap里添加数据
                 usermap=new HashMap<String, String>();
                 usermap.put("username",SharedHelper.getInstance().getValue("username"));
                 usermap.put("introduce",binding.changeIntroduce.getText().toString());
                 usermap.put("email",binding.changeEmail.getText().toString());
                 usermap.put("phone",binding.changePhone.getText().toString());
                 usermap.put("address",binding.changeAddress.getText().toString());
                 usermap.put("age",binding.changeAge.getText().toString());
                 usermap.put("gender",binding.changeGender.getText().toString());
                 Log.d("usermap",usermap.toString());
                 changevm.modifyData(usermap);

             }
         });

        






     


        return  binding.getRoot();


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
