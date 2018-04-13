package com.example.acer.myapplication.activity.service;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.acer.myapplication.R;
import com.example.acer.myapplication.utility.MyHttp;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;
import java.util.Map;

import static com.example.acer.myapplication.activity.BaseActivity.TAG;

/**
 * Created by admin on 2018/4/12.
 */
public class ColService {

    public final static int GETFOLDERNAME_RESULT_OK = 1;

    /** 该方法从服务器得到收藏夹名称的列表，并加入到result中的data数据中，
     *  data是List<String>的一个实例。
     *
     * @param context 用来得到请求URL
     * @param params 请求参数，已map的形式传入。
     * @param handler 用于异步消息处理的对象。
     * @param result 返回结果
     */
    public void getFolderName(Context context, Map<String, String> params, final Handler handler, final resultEnd result) {
        MyHttp.setUrl(UrlService.getUrl(context, R.string.URL_GETCOLLECTION));  //设置utl

        for (Map.Entry<String, String> entry : params.entrySet()) {                  //设置参数
            MyHttp.setParam(entry.getKey(), entry.getValue());
        }

        MyHttp.post(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                Log.d("wangpeng", "onSuccess: " + content);
                //json的反序列化
                JSONObject jsonObject = JSONObject.parseObject(content);
                Boolean success = jsonObject.getBoolean("success");
                String info = jsonObject.getString("info");

                //得到list的json字符串表示
                String data = jsonObject.getString("data");
                Log.d(TAG, "onSuccess: " + data);
                //得到收藏夹名的list
                List<String> nameList = JSON.parseArray(data, String.class);
                //设置返回结果
                List<String> list = (List<String>) result.getData();
                for (int i = 0; i < nameList.size(); i++) {
                    list.add(nameList.get(i));
                }

                result.setSuccess(success);
                result.setInfo(info);
                //发送消息，通知主线程更新界面。
                Message message = new Message();
                message.what = GETFOLDERNAME_RESULT_OK;
                handler.sendMessage(message);
            }

            //连接服务器失败
            @Override
            public void onFailure(Throwable error, String content) {
                Log.d("wangpeng", "onFailure: " + content);
                result.setAvaibleNet(false);
            }
        });
    }


}

