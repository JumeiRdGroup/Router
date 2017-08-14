package com.lzh.remote;

import android.app.Application;

import com.lzh.nonview.router.anno.RouteConfig;

@RouteConfig(baseUrl = "remote://")
public class RemoteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
