package com.lzh.remote;

import android.app.Application;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.anno.RouteConfig;
import com.lzh.router.RouterRuleCreator;
import com.lzh.stub.router.PluginRemoteFactory;
import com.lzh.stub.router.RouterLoader;

@RouteConfig(baseUrl = "remote://")
public class RemoteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RouterLoader.get().init(this);
        RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());
        RouterConfiguration.get().setRemoteFactory(new PluginRemoteFactory("remote"));
    }
}
