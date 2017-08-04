/*
 * Copyright (C) 2017 Haoge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzh.nonview.router.route;

import android.net.Uri;

import com.lzh.nonview.router.RouteManager;
import com.lzh.nonview.router.executors.MainThreadExecutor;
import com.lzh.nonview.router.extras.ActionRouteBundleExtras;
import com.lzh.nonview.router.launcher.ActionLauncher;
import com.lzh.nonview.router.launcher.DefaultActionLauncher;
import com.lzh.nonview.router.launcher.Launcher;
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
    protected Launcher obtainLauncher() throws Exception{
        ActionRouteRule rule = (ActionRouteRule) routeRule;
        Class<? extends ActionLauncher> launcher = rule.getLauncher();
        if (launcher == null) {
            launcher = DefaultActionLauncher.class;
        }
        return launcher.newInstance();
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
     * @see RouteManager#registerExecutors(Class, Executor)
     */
    @Deprecated
    public static void registerExecutors(Class<? extends Executor> key, Executor value) {
        RouteManager.registerExecutors(key, value);
    }
}
