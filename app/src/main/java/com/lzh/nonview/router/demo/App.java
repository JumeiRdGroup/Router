package com.lzh.nonview.router.demo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.widget.Toast;

import com.haoge.studio.RouterRuleCreator;
import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.anno.RouteConfig;
import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.module.RouteCreator;
import com.lzh.nonview.router.module.RouteMap;
import com.lzh.nonview.router.route.ActivityRouteBundleExtras;
import com.lzh.nonview.router.route.RouteCallback;
import com.lzh.nonview.router.route.RouteInterceptor;

import java.util.HashMap;
import java.util.Map;

@RouteConfig(schema = "haoge",pack = "com.haoge.studio")
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 添加route规则创建器
        Router.addRouteCreator(new RouteInit());
        Router.addRouteCreator(new RouterRuleCreator());
        Router.setGlobalRouteInterceptor(new RouteInterceptor() {

            @Override
            public boolean intercept(Uri uri, ActivityRouteBundleExtras extras, Context context) {
                return !DataManager.INSTANCE.isLogin();
            }

            @Override
            public void onIntercepted(Uri uri, ActivityRouteBundleExtras extras, Context context) {
                Toast.makeText(App.this, "未登录.请先登录", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(context,LoginActivity.class);
                loginIntent.putExtra("uri",uri);
                loginIntent.putExtra("extras",extras);
                context.startActivity(loginIntent);
            }
        });

        // 对Router设置Activity Route Callback,作辅助功能
        Router.setGlobalRouteCallback(new RouteCallback() {

            @Override
            public void notFound(Uri uri, NotFoundException e) {
                Toast.makeText(App.this, e.getNotFoundName() + " not find", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOpenSuccess(Uri uri, String clzName) {
                // 可在此进行route追踪
                Toast.makeText(App.this, String.format("Launch activity %s success",clzName), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOpenFailed(Uri uri, Exception e) {
                Toast.makeText(App.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class RouteInit implements RouteCreator {

        @Override
        public Map<String, RouteMap> createRouteRules() {
            Map<String,RouteMap> routes = new HashMap<>();
            routes.put("jumei://main",
                    new RouteMap(ParamsActivity.class)
                    .addParam("price",RouteMap.FLOAT)
                    .addParam("bookName",RouteMap.STRING)
                    .addParam("books",RouteMap.STRING_LIST)
                    .addParam("prices",RouteMap.INT_LIST)
            );
            return routes;
        }
    }
}
