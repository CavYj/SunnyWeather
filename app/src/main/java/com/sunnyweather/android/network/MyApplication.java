package com.sunnyweather.android.network;

import android.app.Application;

import com.sunnyweather.network.NetworkApi;

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        //初始化
        NetworkApi.init(new NetworkRequiredInfo(this));
    }
}
