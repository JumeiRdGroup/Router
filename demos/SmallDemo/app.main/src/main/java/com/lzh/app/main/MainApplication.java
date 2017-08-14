package com.lzh.app.main;

import android.app.Application;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.anno.RouteConfig;

@RouteConfig(baseUrl = "main://", pack = "com.lzh.app.main")
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());
    }
}
