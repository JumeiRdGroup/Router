package com.lzh.replugindemo;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.anno.RouteConfig;
import com.lzh.router.RouterRuleCreator;
import com.lzh.stub.ActivityManager;
import com.lzh.stub.router.HostRemoteFactory;
import com.lzh.stub.router.PluginActivityLauncher;
import com.lzh.stub.router.RouterLoader;
import com.qihoo360.replugin.RePluginApplication;

@RouteConfig(baseUrl = "host://")
public class HostApplication extends RePluginApplication{

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化路由操作
        RouterLoader.get().init(this);
        // 添加路由规则。
        RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());

        registerActivityLifecycleCallbacks(ActivityManager.get());

        RouterConfiguration.get().setRemoteFactory(new HostRemoteFactory());
    }
}
