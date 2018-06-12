package com.example.acer.collectionplus.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.acer.collectionplus.Adapter.NoteAdapter;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Constant.MainConstant;
import com.example.acer.collectionplus.Helper.DialogHelper;
import com.example.acer.collectionplus.Helper.ToastUtils;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.ViewModel.NoteVM;
import com.example.acer.collectionplus.databinding.ActivityNoteBinding;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

public class NoteActivity extends AppCompatActivity implements INoteView, View.OnClickListener{
    public static final String TAG ="NoteActivity";
    ActivityNoteBinding binding;
    NoteAdapter mAdaper;
    NoteVM noteVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_note);
        init();
        initNoteRecycleView();

    }

    /**
     * 初始化变量
     */
    private void init(){
        mAdaper = new NoteAdapter(this);
        Intent  intent = getIntent();
        String linkID = intent.getStringExtra("linkID");
        noteVM = new NoteVM(this,mAdaper,this,binding,linkID);
        binding.tvBackName.setOnClickListener(this);
        binding.tvAddNote.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_add_note:
                popUpDialog();
               // ToastUtils.show(this,"点击了加号！");
                break;
            case R.id.tv_back_name:
                finish();
                break;

        }
    }
    private void initNoteRecycleView() {

        binding.xrvNote.setRefreshProgressStyle(ProgressStyle.BallClipRotate);
        binding.xrvNote.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        binding.xrvNote.setArrowImageView(R.drawable.pull_down_arrow);
        //binding.xrvLink.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        binding.xrvNote.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                noteVM.onFresh();
            }

            @Override
            public void onLoadMore() {
                binding.xrvNote.loadMoreComplete(); //结束加载
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.xrvNote.setLayoutManager(layoutManager);
        binding.xrvNote.setAdapter(mAdaper);

    }
    @Override
    public void loadStart(int loadType) {
        if(loadType== MainConstant.LoadData.FIRST_LOAD){
            DialogHelper.getInstance().show(this,"加载中...",DialogHelper.LOAD_DIALOG);
        }
    }

    @Override
    public void loadComplete() {
        DialogHelper.getInstance().close();
        binding.xrvNote.loadMoreComplete(); //结束加载
        binding.xrvNote.refreshComplete();  //结束加载
        binding.xrvNote.refreshComplete();  //结束刷新
    }

    @Override
    public void loadFailure(String message) {
        DialogHelper.getInstance().close();
        binding.xrvNote.loadMoreComplete(); //结束加载
        binding.xrvNote.refreshComplete();  //结束加载
        binding.xrvNote.refreshComplete();  //结束刷新
        ToastUtils.show(this,message);
    }

    /**
     *
     * @param context activity
     * @param linkID 链接的ID
     */
    public static void actionStart(Context context, String linkID){
        Intent intent = new Intent(context,NoteActivity.class);
        intent.putExtra("linkID",linkID);
        context.startActivity(intent);
    }

    /**
     * 弹出收藏夹
     */
    private void popUpDialog(){
        Log.d(TAG, "popUpDialog: ");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher2);
        builder.setTitle("新建笔记");
        final View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_note,null,false);
        final EditText et_note_title = (EditText)view.findViewById(R.id.et_note_title);
        final EditText et_note_content = (EditText)view.findViewById(R.id.et_note_content);
        builder.setView(view);
        builder.setPositiveButton("完成", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String note_title = et_note_title.getText().toString();
                        String note_content = et_note_content.getText().toString();
                        noteVM.createNote(note_title,note_content);
                    }
        });
        builder.setNegativeButton("取消",null);

        builder.create().show();
    }

}
