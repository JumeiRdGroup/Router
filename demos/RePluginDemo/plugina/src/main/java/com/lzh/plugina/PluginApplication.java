package com.lzh.plugina;

import android.app.Application;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.anno.RouteConfig;
import com.lzh.router.RouterRuleCreator;
import com.lzh.router.replugin.plugin.PluginRouterConfiguration;

@RouteConfig(baseUrl = "plugina://")
public class PluginApplication extends Application{

    private static final String TAG = "ROUTER";
    @Override
    public void onCreate() {
        super.onCreate();

        PluginRouterConfiguration.init("com.lzh.replugindemo", "plugina", this);
        RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());
    }
}