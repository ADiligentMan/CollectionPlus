package com.example.acer.collectionplus.ViewModel;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.acer.collectionplus.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.CLIPBOARD_SERVICE;

public class MainVM {
    private Context mContext;
    private String currClip;  //当前在剪切板中的内容。
    private boolean clipIsChanged = false; //剪切板是否变化
    /**
     * 注册剪贴板监听器。
     */
    public MainVM(Context context){
        this.mContext =context;
        setClipboard();
    }

    private void setClipboard() {
        mContext.getSystemService(CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {

            // 剪切板发生变化的事件监听
            @Override
            public void onPrimaryClipChanged() {
                ClipData clipData = clipboardManager.getPrimaryClip();
                ClipData.Item item = clipData.getItemAt(0);
                String newCopy = item.getText().toString();
                currClip = newCopy;
                clipIsChanged = true;
            }
        });
    }

    /**
     * 剪贴板是否变换
     *
     * @return
     */
    public boolean chipIsChanged() {
        return clipIsChanged;
    }

    /**
     * 得到当前剪贴板的内容。
     *
     * @return
     */
    public String getCurrClip() {
        return this.currClip;
    }

    /**
     * 回到初始状态，即处理完一次剪贴板变化后，回到初始状态
     */
    public void backToStartState(){
        this.clipIsChanged =false;
    }

}