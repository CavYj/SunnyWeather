package com.sunnyweather.android.network;

import android.app.Application;

import com.sunnyweather.network.INetworkRequiredInfo;

public class NetworkRequiredInfo implements INetworkRequiredInfo {
    @Override
    public String getAppVersionName() {
        return null;
    }

    @Override
    public String getAppVersionCode() {
        return null;
    }

    @Override
    public boolean isDebug() {
        return false;
    }

    @Override
    public Application getApplicationContext() {
        return null;
    }
}
