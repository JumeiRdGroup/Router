package com.lzh.stub.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.route.RouteCallback;
import com.lzh.stub.ActivityManager;
import com.lzh.stub.RouterBridgeActivity;
import com.qihoo360.replugin.RePlugin;

/**
 * 所有宿主及插件所共同配置的路由回调。
 *
 * 用于在启动路由时。当路由未找到。判断是否已启动对应插件。若未启动。则启动插件并恢复路由。
 */
public class PluginCallback implements RouteCallback{

    @Override
    public void notFound(Uri uri, NotFoundException e) {
        log("not found with " + uri.toString());
        checkAndLaunch(uri);
    }

    @Override
    public void onOpenSuccess(Uri uri, RouteRule rule) {
        log("success with " + rule.getRuleClz());
    }

    @Override
    public void onOpenFailed(Uri uri, Throwable e) {
        log("open failed cause by " + e.getMessage());
    }

    private void checkAndLaunch(Uri uri) {
        // 约定各个插件使用单独的scheme作为别名。
        String scheme = uri.getScheme();
        if (TextUtils.isEmpty(scheme)) {
            // 无效uri。
            return;
        }

        Context top = ActivityManager.get().top();
        if (RouterConfiguration.get().isRegister(scheme)) {
            // 判断此插件是否被注册过。未被注册说明插件未启动。
            Toast.makeText(top, "当前版本无此路由规则。请升级！", Toast.LENGTH_SHORT).show();
            return;
        }

        // 取出缓存数据并放入intent中传递
        Intent intent = RePlugin.createIntent(scheme, RouterBridgeActivity.class.getCanonicalName());
        intent.putExtra("uri", uri);
        intent.putExtra("extras", RouterConfiguration.get().restoreExtras(uri));
        RePlugin.startActivity(top, intent);
    }

    void log(String message) {
        Log.e("Plugin Callback", message);
    }
}
