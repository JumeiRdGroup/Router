package com.lzh.replugindemo;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.anno.RouteConfig;
import com.lzh.router.RouterRuleCreator;
import com.lzh.stub.ActivityManager;
import com.lzh.stub.router.HostRemoteFactory;
import com.lzh.stub.router.PluginActivityLauncher;
import com.lzh.stub.router.RouterLoader;
import com.qihoo360.replugin.RePluginApplication;
import com.qihoo360.replugin.RePluginCallbacks;
import com.qihoo360.replugin.RePluginConfig;

import org.json.JSONObject;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateParser;

// 指定生成路由的baseUrl。此baseUrl会与使用RouteRule所指定的path所组合。形成一个完整的路由地址。
// 生成的路由表。参考下方添加路由规则的RouterRuleCreator类。
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

    @Override
    protected RePluginCallbacks createCallbacks() {
        return new RePluginCallback(this);
    }
}
