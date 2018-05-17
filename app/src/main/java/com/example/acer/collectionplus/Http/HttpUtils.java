package com.example.acer.collectionplus.Http;

import com.example.acer.collectionplus.Constant.URLConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtils {
    private static Object syncRoot = new Object();
    private static final int DEFAULT_TIMEOUT = 8; //连接 超时的时间，单位：秒
    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).
            readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).
            writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).build();

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        //初始化retrofit的配置
        if (retrofit == null) {
            synchronized (syncRoot) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(URLConstant.URL_BASE)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();

                }
            }
        }

        return retrofit;
    }
}
