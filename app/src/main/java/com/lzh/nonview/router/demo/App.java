package com.lzh.nonview.router.demo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.route.ActivityRouteBundleExtras;
import com.lzh.nonview.router.route.RouteCallback;

/**
 * Created by admin on 16/9/6.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 对Router框架进行初始化
        Router.init(this);
        // 对Router设置Activity Route Callback,作辅助功能
        Router.setRouteCallback(new RouteCallback() {

            @Override
            public boolean interceptOpen(Uri uri, Context context, ActivityRouteBundleExtras extras) {
                if (DataManager.INSTANCE.isLogin()) return false;

                Toast.makeText(App.this, "未登录.请先登录", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(context,LoginActivity.class);
                loginIntent.putExtra("uri",uri);
                loginIntent.putExtra("extra",extras);
                context.startActivity(loginIntent);
                return true;
            }

            @Override
            public void notFound(Uri uri, String clzName) {
                Toast.makeText(App.this, clzName + " not find", Toast.LENGTH_SHORT).show();
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
}
