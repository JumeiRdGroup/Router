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

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.interceptors.RouteInterceptorAction;
import com.lzh.nonview.router.launcher.Launcher;
import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.parser.URIParser;
import com.lzh.nonview.router.tools.Cache;
import com.lzh.nonview.router.tools.Utils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class BaseRoute<T extends IBaseRoute> implements IRoute, IBaseRoute<T>, RouteInterceptorAction<T> {
    protected Bundle bundle;
    InternalCallback callback;
    protected Uri uri;
    protected Bundle remote;
    protected RouteRule routeRule = null;
    protected Launcher launcher;

    public final IRoute create(Uri uri, RouteRule rule, Bundle remote, InternalCallback callback) {
        try {
            this.uri = uri;
            this.remote = remote;
            this.callback = callback;
            this.routeRule = rule;
            this.bundle = Utils.parseRouteMapToBundle(new URIParser(uri), routeRule);
            this.bundle.putParcelable(Router.RAW_URI, uri);
            this.launcher = obtainLauncher();
            return this;
        } catch (Throwable e) {
            callback.onOpenFailed(e);
            return new EmptyRoute(callback);
        }
    }

    // =========Unify method of IBaseRoute
    @Override
    public final void open(Context context) {
        try {
            Utils.checkInterceptor(uri, callback.getExtras(), context,getInterceptors());
            launcher.set(uri, bundle, callback.getExtras(), routeRule, remote);
            launcher.open(context);
//            realOpen(context);
            callback.onOpenSuccess(routeRule);
        } catch (Throwable e) {
            callback.onOpenFailed(e);
        }

        callback.invoke();
    }

    @Override
    public T addExtras(Bundle extras) {
        this.callback.getExtras().addExtras(extras);
        return (T) this;
    }

    // =============RouteInterceptor operation===============
    public T addInterceptor(RouteInterceptor interceptor) {
        if (callback.getExtras() != null) {
            callback.getExtras().addInterceptor(interceptor);
        }
        return (T) this;
    }

    @Override
    public T removeInterceptor(RouteInterceptor interceptor) {
        if (callback.getExtras() != null) {
            callback.getExtras().removeInterceptor(interceptor);
        }
        return (T) this;
    }

    @Override
    public T removeAllInterceptors() {
        if (callback.getExtras() != null) {
            callback.getExtras().removeAllInterceptors();
        }
        return (T) this;
    }

    @Override
    public List<RouteInterceptor> getInterceptors() {

        List<RouteInterceptor> interceptors = new ArrayList<>();
        // add global interceptor
        if (RouterConfiguration.get().getInterceptor() != null) {
            interceptors.add(RouterConfiguration.get().getInterceptor());
        }

        // add extra interceptors
        if (callback.getExtras() != null) {
            interceptors.addAll(callback.getExtras().getInterceptors());
        }

        // add interceptors in rule
        for (Class<RouteInterceptor> interceptor : routeRule.getInterceptors()) {
            if (interceptor != null) {
                try {
                    interceptors.add(interceptor.newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(String.format("The interceptor class [%s] should provide a default empty construction", interceptor));
                }
            }
        }

        return interceptors;
    }

    // ========getter/setter============
    public void replaceExtras(RouteBundleExtras extras) {
        this.callback.setExtras(extras);
    }

    public static RouteRule findRule(Uri uri, int type) {
        return Cache.getRouteMapByUri(new URIParser(uri), type);
    }

    // ============abstract methods============
    protected abstract Launcher obtainLauncher() throws Exception;

}
