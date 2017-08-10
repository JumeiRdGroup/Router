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
package com.lzh.nonview.router;

import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.launcher.ActionLauncher;
import com.lzh.nonview.router.launcher.ActivityLauncher;
import com.lzh.nonview.router.module.RouteCreator;
import com.lzh.nonview.router.protocol.HostServiceWrapper;
import com.lzh.nonview.router.protocol.IRemoteFactory;
import com.lzh.nonview.router.route.RouteCallback;
import com.lzh.nonview.router.tools.Cache;

import java.util.concurrent.Executor;

/**
 * The entry class to holds all the global configurations.
 */
public final class RouterConfiguration {

    private RouteInterceptor interceptor;
    private RouteCallback callback;
    // default route remote factory
    private Class<? extends IRemoteFactory> remoteFactory = null;
    private Class<? extends ActivityLauncher> activityLauncher = null;
    private Class<? extends ActionLauncher> actionLauncher = null;

    public RouteInterceptor getInterceptor() {
        return interceptor;
    }

    public RouterConfiguration setInterceptor(RouteInterceptor interceptor) {
        this.interceptor = interceptor;
        return this;
    }

    public RouteCallback getCallback() {
        return callback;
    }

    public RouterConfiguration setCallback(RouteCallback callback) {
        this.callback = callback;
        return this;
    }

    public Class<? extends IRemoteFactory> getRemoteFactory() {
        return remoteFactory;
    }

    public RouterConfiguration setRemoteFactory(Class<? extends IRemoteFactory> remoteFactory) {
        this.remoteFactory = remoteFactory;
        return this;
    }

    public Class<? extends ActivityLauncher> getActivityLauncher() {
        return activityLauncher;
    }

    public RouterConfiguration setActivityLauncher(Class<? extends ActivityLauncher> activityLauncher) {
        this.activityLauncher = activityLauncher;
        return this;
    }

    public Class<? extends ActionLauncher> getActionLauncher() {
        return actionLauncher;
    }

    public RouterConfiguration setActionLauncher(Class<? extends ActionLauncher> actionLauncher) {
        this.actionLauncher = actionLauncher;
        return this;
    }

    /**
     * Add a route rule creator
     * @param creator Route rules creator.can't be null
     */
    public void addRouteCreator(RouteCreator creator) {
        Cache.addCreator(creator);
        HostServiceWrapper.registerRulesToHostService();
    }

    /**
     * To register an executor that
     * @param key The class of Executor
     * @param value The Executor instance associate with the key.
     * @see Cache#registerExecutors(Class, Executor)
     */
    public void registerExecutors(Class<? extends Executor> key, Executor value) {
        Cache.registerExecutors(key, value);
    }

    private static RouterConfiguration config = new RouterConfiguration();
    private RouterConfiguration() {}
    public static RouterConfiguration get() {
        return config;
    }
}
