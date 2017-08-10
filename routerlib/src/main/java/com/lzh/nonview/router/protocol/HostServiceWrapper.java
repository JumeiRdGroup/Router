package com.lzh.nonview.router.protocol;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.text.TextUtils;

import com.lzh.nonview.router.module.RemoteRule;
import com.lzh.nonview.router.tools.Cache;
import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.route.ActionRoute;
import com.lzh.nonview.router.route.ActivityRoute;
import com.lzh.nonview.router.route.IRoute;
import com.lzh.nonview.router.route.RouteCallback;

import java.util.HashMap;
import java.util.Map;

public class HostServiceWrapper {

    private static String hostPackage;
    static Context context;
    private static IService service;

    private static ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            HostServiceWrapper.service = IService.Stub.asInterface(service);
            // register rules to remote service.
            registerRulesToHostService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            HostServiceWrapper.service = null;
        }
    };

    /**
     * Set a host package name. it will be used to bind a remote service in host app.
     *
     * @param hostPackage the package name of host.
     * @param context the context to start remote service.
     */
    public static void startHostService(String hostPackage, Context context) {
        if (service != null) {
            throw new RuntimeException("You've bind a remote service before");
        }
        if (TextUtils.isEmpty(hostPackage)) {
            throw new IllegalArgumentException("Please provide a valid host package name.");
        }
        HostServiceWrapper.hostPackage = hostPackage;
        HostServiceWrapper.context = context.getApplicationContext();

        Intent intent = new Intent();
        intent.setPackage(hostPackage);
        intent.setAction("com.lzh.router.action.HOST");
        context.bindService(intent, connection, Service.BIND_AUTO_CREATE);
    }

    public static IRoute create(Uri uri, RouteCallback.InternalCallback callback) {
        try {
            return createWithThrow(uri, callback);
        } catch (Exception e) {
            return IRoute.EMPTY;
        }
    }

    private static IRoute createWithThrow(Uri uri, RouteCallback.InternalCallback callback) throws Exception{
        RemoteRule rule;
        if ((rule = service.getActivityRule(uri)) != null) {
            return new ActivityRoute().create(uri, rule.getRule(), rule.getExtra(), callback);
        } else if ((rule = service.getActionRule(uri)) != null) {
            return new ActionRoute().create(uri, rule.getRule(), rule.getExtra(), callback);
        } else {
            return IRoute.EMPTY;
        }
    }

    public static void registerRulesToHostService() {
        try {
            if (service == null) {
                return;
            }
            service.addActionRules(transform(Cache.getActionRules()));
            service.addActivityRules(transform(Cache.getActivityRules()));
        } catch (Exception e) {
            // ignore
        }
    }

    private static Map<String, RemoteRule> transform(Map<String, ? extends RouteRule> source){
        Map<String, RemoteRule> dest = new HashMap<>();
        for (String route : source.keySet()) {
            RouteRule rule = source.get(route);
            RemoteRule remote = RemoteRule.create(rule, rule.getFactory() == null ? null : rule.getFactory().createRemote(context, rule));
            dest.put(route, remote);
        }
        return dest;
    }

}
