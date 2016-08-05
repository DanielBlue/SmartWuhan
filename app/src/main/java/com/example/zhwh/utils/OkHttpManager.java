package com.example.zhwh.utils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 毛琦 on 2016/7/30.
 */
public class OkHttpManager {

    public void send(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call= client.newCall(request);
        call.enqueue(callback);
    }
}
