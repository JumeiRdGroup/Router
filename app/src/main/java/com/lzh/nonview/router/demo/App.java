package com.lzh.nonview.router.demo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.module.RouteCreator;
import com.lzh.nonview.router.module.RouteMap;
import com.lzh.nonview.router.route.ActivityRouteBundleExtras;
import com.lzh.nonview.router.route.RouteCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 16/9/6.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 添加route规则创建器
        Router.addRouteCreator(new RouteInit());
        // 对Router设置Activity Route Callback,作辅助功能
        Router.setRouteCallback(new RouteCallback() {

            @Override
            public boolean interceptOpen(Uri uri, Context context, ActivityRouteBundleExtras extras) {
                // 拦截方法,返回true.表示此open事件被拦截.不继续运行,false不拦截
                if (DataManager.INSTANCE.isLogin()) return false;

                Toast.makeText(App.this, "未登录.请先登录", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(context,LoginActivity.class);
                loginIntent.putExtra("uri",uri);
                loginIntent.putExtra("extra",extras);
                context.startActivity(loginIntent);
                return true;
            }

            @Override
            public void notFound(Uri uri, NotFoundException e) {
                Toast.makeText(App.this, e.getNotFoundName() + " not find", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOpenSuccess(Uri uri, String clzName) {
                Toast.makeText(App.this, String.format("Launch activity %s success",clzName), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOpenFailed(Uri uri, Exception e) {

            }
        });
    }

    class RouteInit implements RouteCreator {

        @Override
        public Map<String, RouteMap> initRoute() {
            Map<String,RouteMap> routes = new HashMap<>();
            routes.put("jumei://main",
                    new RouteMap(ParamsActivity.class.getCanonicalName())
                    .addParam("price",RouteMap.FLOAT)
                    .addParam("bookName",RouteMap.STRING)
            );
            routes.put("jumeimail://main",
                    new RouteMap("com.lzh.nonview.demo.RegisterActivity")
                    );
            return routes;
        }
    }
}
