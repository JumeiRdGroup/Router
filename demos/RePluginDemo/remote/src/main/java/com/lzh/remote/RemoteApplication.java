package com.lzh.remote;

import android.app.Application;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.anno.RouteConfig;
import com.lzh.router.RouterRuleCreator;
import com.lzh.router.replugin.plugin.PluginRouterConfiguration;

@RouteConfig(baseUrl = "remote://")
public class RemoteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PluginRouterConfiguration.init("com.lzh.replugindemo", "remote", this);
        RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());
    }
}
