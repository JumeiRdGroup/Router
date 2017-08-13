package com.lzh.plugina;

import android.app.Application;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.anno.RouteConfig;
import com.lzh.router.RouterRuleCreator;
import com.lzh.stub.ActivityManager;
import com.lzh.stub.router.PluginRemoteFactory;
import com.lzh.stub.router.RouterLoader;

@RouteConfig(baseUrl = "plugina://")
public class PluginApplication extends Application{

    private static final String TAG = "ROUTER";
    @Override
    public void onCreate() {
        super.onCreate();
        
        RouterLoader.get().init(this);
        RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());
        // 对插件设置自己的远程数据提供器。通过此接口指定提供的别名。便于当插件未加载时。通过此别名指定应该加载的插件。
        RouterConfiguration.get().setRemoteFactory(new PluginRemoteFactory("plugina"));
    }
}
