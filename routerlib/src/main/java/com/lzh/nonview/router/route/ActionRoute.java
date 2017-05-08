package com.lzh.nonview.router.route;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.lzh.compiler.parceler.Parceler;
import com.lzh.nonview.router.RouteManager;
import com.lzh.nonview.router.Utils;
import com.lzh.nonview.router.executors.MainThreadExecutor;
import com.lzh.nonview.router.extras.ActionRouteBundleExtras;
import com.lzh.nonview.router.module.ActionRouteRule;
import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.parser.URIParser;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class ActionRoute extends BaseRoute<IActionRoute, ActionRouteBundleExtras> implements IActionRoute {

    private final static Map<Class<? extends Executor>, Executor> container = new HashMap<>();

    @Override
    protected ActionRouteBundleExtras createExtras() {
        return new ActionRouteBundleExtras();
    }

    @Override
    protected RouteRule obtainRouteMap() {
        return RouteManager.get().getRouteMapByUri(parser, RouteManager.TYPE_ACTION_ROUTE);
    }

    @Override
    protected void realOpen(final Context context) throws Throwable {
        ActionRouteRule rule = (ActionRouteRule) routeRule;
        final ActionSupport support = (ActionSupport) rule.getRuleClz().newInstance();
        final Bundle data = new Bundle();
        data.putAll(bundle);
        data.putAll(extras.getExtras());
        if (Utils.PARCELER_SUPPORT) {
            Parceler.toEntity(support, data);// inject data
        }
        findOrCreateByClass(rule.getExecutorClz()).execute(new ActionRunnable(support, context, data));
    }

    public static boolean canOpenRouter(Uri uri) {
        try {
            return RouteManager.get().getRouteMapByUri(new URIParser(uri), RouteManager.TYPE_ACTION_ROUTE) != null;
        } catch (Throwable e) {
            return false;
        }
    }

    private static Executor findOrCreateByClass(Class<? extends Executor> key) {
        try {
            Executor executor = container.get(key);
            if (executor == null) {
                executor = key.newInstance();
                registerExecutors(key, executor);
            }
            return executor;
        } catch (Throwable t) {
            // provided MainThreadExecutor if occurs an error.
            return new MainThreadExecutor();
        }
    }

    /**
     * To register an Executor to be used.
     * @param key The class of Executor
     * @param value The Executor instance associate with the key.
     */
    @SuppressWarnings("WeakerAccess")
    public static void registerExecutors(Class<? extends Executor> key, Executor value) {
        if (key == null || value == null) {
            return;
        }

        container.remove(key);
        container.put(key, value);
    }

    private static class ActionRunnable implements Runnable {

        ActionSupport support;
        Context context;
        Bundle data;

        ActionRunnable(ActionSupport support, Context context, Bundle data) {
            this.support = support;
            this.context = context;
            this.data = data;
        }

        @Override
        public void run() {
            support.onRouteTrigger(context, data);
        }
    }
}
