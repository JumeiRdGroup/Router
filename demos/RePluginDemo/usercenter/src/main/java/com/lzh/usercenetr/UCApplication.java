package com.lzh.usercenetr;

import android.app.Application;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.anno.RouteConfig;
import com.lzh.router.RouterRuleCreator;
import com.lzh.router.replugin.plugin.PluginRouterConfiguration;

@RouteConfig(baseUrl = "usercenter://")
public class UCApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());
        PluginRouterConfiguration.init("com.lzh.replugindemo", "usercenter", this);
    }
}
