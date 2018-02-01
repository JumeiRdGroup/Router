package com.lzh.nonview.router.demo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.haoge.studio.RouterRuleCreator;
import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.FastJsonConverter;
import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.anno.RouteConfig;
import com.lzh.nonview.router.demo.action.SayHelloAction;
import com.lzh.nonview.router.demo.interceptors.ToastInterceptor;
import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.launcher.DefaultActivityLauncher;
import com.lzh.nonview.router.module.ActionRouteRule;
import com.lzh.nonview.router.module.ActivityRouteRule;
import com.lzh.nonview.router.module.RouteCreator;
import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.protocol.IRemoteFactory;
import com.lzh.nonview.router.route.RouteCallback;

import java.util.HashMap;
import java.util.Map;

@RouteConfig(
        baseUrl = "haoge://",
        pack = "com.haoge.studio")
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        // 添加route规则创建器。RouteInit为用户手动创建的路由规则。
        // RouterRuleCreator为用户使用注解后。自动生成的规则。均使用addRouteCreator将规则加入
        RouterConfiguration.get().addRouteCreator(new RouteInit());
        RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());

        // 设置全局路由拦截器。所有的路由操作均会在此被判断是否被拦截并做相应处理。
        RouterConfiguration.get().setInterceptor(new DefaultInterceptor());

        // 对Router设置Activity Route Callback,作辅助功能
        RouterConfiguration.get().setCallback(new DefaultCallback());

        // 启动远程服务。一般在插件化环境下使用。
//        RouterConfiguration.get().startHostService("com.lzh.nonview.router.demo", this);
        // 对应于启动远程服务操作。可设置此远程数据创建者。也应在插件化环境下使用。
//        RouterConfiguration.get().setRemoteFactory(new RemoteFactory());
        // 当默认的动作路由启动方式不能满足你项目需要时。通过定制此接口来做替换
//        RouterConfiguration.get().setActionLauncher(CustomActionLauncher.class);
        // 当默认的页面路由启动方式不能满足你项目需要时，通过定制此接口来做替换
//        RouterConfiguration.get().setActivityLauncher(CustomActivityLauncher.class);

        Parceler.setDefaultConverter(FastJsonConverter.class);
    }

    private class RouteInit implements RouteCreator {

        @Override
        public Map<String, ActivityRouteRule> createActivityRouteRules() {
            Map<String,ActivityRouteRule> routes = new HashMap<>();
            routes.put("jumei://main",
                    new ActivityRouteRule(ParamsActivity.class)
                            .addParam("price", RouteRule.FLOAT)
                            .addParam("bookName", RouteRule.STRING)
                            .setInterceptors(ToastInterceptor.class)
                            .setLauncher(DefaultActivityLauncher.class)
            );
            return routes;
        }

        @Override
        public Map<String, ActionRouteRule> createActionRouteRules() {
            Map<String, ActionRouteRule> routes = new HashMap<>();
            routes.put("haoge://action/support", new ActionRouteRule(SayHelloAction.class));
            return routes;
        }
    }

    private static class DefaultInterceptor implements RouteInterceptor {

        @Override
        public boolean intercept(Uri uri, RouteBundleExtras extras, Context context) {
            return !DataManager.INSTANCE.isLogin();
        }

        @Override
        public void onIntercepted(Uri uri, RouteBundleExtras extras, Context context) {
            Toast.makeText(context, "未登录.请先登录", Toast.LENGTH_SHORT).show();
            Intent loginIntent = new Intent(context,LoginActivity.class);
            loginIntent.putExtra("uri",uri);
            loginIntent.putExtra("extras",extras);
            context.startActivity(loginIntent);
        }
    }

    private class DefaultCallback implements RouteCallback {

        @Override
        public void notFound(Uri uri, NotFoundException e) {
            Toast.makeText(App.this, e.getNotFoundName() + " not find", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onOpenSuccess(Uri uri, RouteRule rule) {
            // 可在此进行route追踪
            Toast.makeText(App.this, String.format("Launch routing task %s success", rule.getRuleClz()), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onOpenFailed(Uri uri, Throwable e) {
            Toast.makeText(App.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private static class RemoteFactory implements IRemoteFactory {

        @Override
        public Bundle createRemote(Context application, RouteRule rule) {
            Bundle bundle = new Bundle();
            bundle.putString("package name", application.getPackageName());
            return bundle;
        }
    }

}
