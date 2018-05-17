package com.example.acer.collectionplus.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import static android.support.constraint.Constraints.TAG;

public class SharedHelper {

    public static  final String TAG ="SharedHelper";
    private static final SharedHelper ourInstance = new SharedHelper();

    public static SharedHelper getInstance() {
        return ourInstance;
    }

    private SharedHelper() {
    }

    private Context mContext =null;
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor =null;

    /**
     * 初始化
     * @param context 这里使用ApplicationContext
     */
    public synchronized void initShared(Context context){
        //防止再次被赋值
        if(this.mContext ==null) {
            this.mContext = context;
            this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
            this.editor = preferences.edit();
        }
    }

    /**
     * 读取数据
     * @param key
     * @return
     */
    public String getValue(String key){
        if(preferences==null) {
            Log.e(TAG, "SharedPreferences is null" );
            return null;
        }
        return  preferences.getString(key,null);
    }

    /**
     * 存数据
     * @param key
     * @param value
     */
    public void setValue(String key,String value){
        if(preferences==null) {
            Log.e(TAG, "SharedPreferences is null" );
            return;
        }
        editor.putString(key ,value);
        editor.commit();
        editor.apply();
    }
}
