package com.example.acer.collectionplus.ViewModel;

import android.content.Context;

import com.example.acer.collectionplus.Adapter.NoteAdapter;
import com.example.acer.collectionplus.Base.BaseBean;
import com.example.acer.collectionplus.Constant.MainConstant;
import com.example.acer.collectionplus.Helper.SharedHelper;
import com.example.acer.collectionplus.Helper.TimeHelper;
import com.example.acer.collectionplus.JavaBean.NoteBean;
import com.example.acer.collectionplus.JavaBean.SimpleNoteBean;
import com.example.acer.collectionplus.Model.NoteModel;
import com.example.acer.collectionplus.View.INoteView;
import com.example.acer.collectionplus.databinding.ActivityNoteBinding;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteVM {
    private NoteAdapter mAdapter;
    private Context mContext;
    private NoteModel mModel;
    private INoteView mView;
    private String mlinkID;
    private ActivityNoteBinding mBinding;
    private int loadType;

    /**
     *
     * @param aContext
     * @param aAdapter
     * @param aView
     * @param binding
     * @param linkID
     */
    public NoteVM(Context aContext, NoteAdapter aAdapter, INoteView aView, ActivityNoteBinding binding,String linkID){
        this.mAdapter = aAdapter;
        this.mContext = aContext;
        this.mView = aView;
        this.mlinkID = linkID;
        this.mBinding = binding;
        this.mModel = new NoteModel();
        loadType = MainConstant.LoadData.FIRST_LOAD;
        initGetNotes();
    }

    /**
     * 初次加载笔记
     */
    private void initGetNotes(){
        mModel.getNotes(mlinkID,new NoteModel.Listener<NoteBean>(){
            @Override
            protected void onStart() {
               mView.loadStart(loadType);
            }

            @Override
            public void onNext(NoteBean mBean) {
                boolean isSuccess = mBean.isSuccess();
                List<SimpleNoteBean> listBean  = new ArrayList<SimpleNoteBean>();
                if(isSuccess){
                    for(NoteBean.Entity entity:mBean.getData()){
                        SimpleNoteBean snb = new SimpleNoteBean();
                        snb.ID.set(entity.getID());
                        snb.linkID.set(entity.getLinkID());
                        snb.content.set(entity.getContent());
                        snb.time.set(TimeHelper.getFormatedTime(entity.getTime()));
                        snb.title.set(entity.getTitle());
                        snb.username.set(entity.getUsername());
                        listBean.add(snb);
                    }
                    mAdapter.refreshData(listBean);
                }else{
                    mView.loadFailure(mBean.getInfo());
                }
            }

            @Override
            public void onError(String errorMsg) {
                mView.loadFailure(errorMsg);
            }

            @Override
            public void onComplete() {
                mView.loadComplete();
            }
        });
    }

    /**
     * 下拉刷新
     */
    public void onFresh(){
        loadType = MainConstant.LoadData.REFRESH;
        this.initGetNotes();
    }

    /**
     *
     * @param note_title
     * @param note_content
     */
    public void createNote(String note_title,String note_content){
        Map<String,String> map  =  new HashMap<String, String>();
        String username = SharedHelper.getInstance().getValue("username");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());

        map.put("username",username);
        map.put("time",time);
        map.put("title",note_title);
        map.put("content",note_content);
        map.put("linkID",mlinkID);

        mModel.createNote(map ,new NoteModel.Listener< BaseBean>(){
            @Override
            public void onNext(BaseBean mBean) {
                boolean isSuccess  = mBean.isSuccess();
                if(!isSuccess){
                    mView.loadFailure(mBean.getInfo());
                }
            }

            @Override
            public void onError(String errorMsg) {
                    mView.loadFailure(errorMsg);
            }

            @Override
            public void onComplete() {

            }
        });

    }


}
