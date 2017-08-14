package com.lzh.smalldemo;

import android.app.Application;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.anno.RouteConfig;
import com.lzh.router.host.RouterRuleCreator;

import net.wequick.small.Small;

// Small由于未做插件、宿主隔离。所以需要对不同的宿主、插件。指定一个特定的路由表的生成包名。避免冲突。
@RouteConfig(baseUrl = "host://", pack = "com.lzh.router.host")
public class HostApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Small.preSetUp(this);
        // 加载
        Small.setUp(this, new Small.OnCompleteListener() {
            @Override
            public void onComplete() {}
        });
        // 添加规则, Small未做隔离。所以不需要启动远程服务进行共享路由。
        RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());
    }
}
