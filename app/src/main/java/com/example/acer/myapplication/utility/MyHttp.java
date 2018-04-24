package com.example.acer.myapplication.utility;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyHttp {
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static String Url;
    private static Context context;
    private static final String TAG = "MyHttp";
    private static Map<String,String> cache = new LinkedHashMap<String,String>();

    public static void setParam(String key,String value){
        cache.put(key,value);
    }

    public static void setParams(Map<String,String> map){
        cache = map;
    }

    public static  void setUrl(String url){
        Url = url;
    }
    public static void setContext(Context contexth){context=contexth; Log.d(TAG, "CONTEXT:"+contexth);}

    public static void post(AsyncHttpResponseHandler handler){
        StringBuffer sb = new StringBuffer("");
        RequestParams params = new RequestParams();


        //构造出请求url,用于测试
        for(Map.Entry<String,String> entry:cache.entrySet()){
            params.put(entry.getKey(),entry.getValue());
            sb.append(entry.getKey()+"="+entry.getValue()+"&");
        }

        String s = sb.substring(0,sb.length()-1);
        Log.d("wangpeng","request:"+Url+"?"+s);

        client.post(Url,params,handler);
        //清空cache,准备下一次请求。
        cache.clear();
    }


}
