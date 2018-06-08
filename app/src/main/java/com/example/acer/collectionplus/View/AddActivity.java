package com.example.acer.collectionplus.View;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.acer.collectionplus.Adapter.ClassAdapter;
import com.example.acer.collectionplus.BR;
import com.example.acer.collectionplus.Helper.ToastUtils;
import com.example.acer.collectionplus.R;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.acer.collectionplus.Helper.DialogHelper;
import com.example.acer.collectionplus.ViewModel.AddVM;
import com.example.acer.collectionplus.databinding.ActivityAddBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddActivity extends AppCompatActivity  implements IAddView{

    private AddVM mAddVM;
    private ClassAdapter mAdapter;
    ActivityAddBinding binding;
    private String content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_add);
        binding.titlebar.title.set("编辑链接");
        binding.setVariable(BR.mView,this);
        initRecyclerView();
        initContent();
        mAddVM = new AddVM(mAdapter,this,binding,content);
    }

    /**
     * 初始化content ,content有可能来自于复制，有可能来自于分享。
     */
    private void initContent(){
        Intent intent = getIntent();
        //如果是复制链接
        content = intent.getStringExtra("content");
        // 如果是分享链接到收藏+
        String action = intent.getAction();
        String type = intent.getType();
        boolean isLink ;
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                content = intent.getStringExtra(Intent.EXTRA_TEXT);
            }
        }

    }



    /**
     * 初始化RecycerView
     */
    private void initRecyclerView(){
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.rvClass.setLayoutManager(manager);
        mAdapter = new ClassAdapter(this);
        binding.rvClass.setAdapter(mAdapter);

    }




    /**
     * 启动AddActivity
     * @param context 一个activity的实例
     * @param content  剪贴板中的内容,链接或者文字
     */
    public static void actionStart(Context context, String content ){
        Intent intent  = new Intent(context,AddActivity.class);
        intent.putExtra("content",content);
        context.startActivity(intent);
    }


    @Override
    public void loadStart(int loadType) {

    }

    @Override
    public void loadComplete() {

    }

    @Override
    public void loadFailure(String message) {
        ToastUtils.show(this,message);
    }

    @Override
    public void onClickFinish() {
        mAddVM.saveLink();
        finish();
    }

    /**
     * Call this when your activity is done and should be closed.  The
     * ActivityResult is propagated back to whoever launched you via
     * onActivityResult().
     */
    @Override
    public void finish() {
        super.finish();
        DialogHelper.getInstance().close();
    }
}
