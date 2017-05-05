package com.lzh.nonview.router.route;

import android.net.Uri;
import android.os.Bundle;

import com.lzh.nonview.router.RouteManager;
import com.lzh.nonview.router.Utils;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.interceptors.RouteInterceptorAction;
import com.lzh.nonview.router.module.RouteMap;
import com.lzh.nonview.router.parser.URIParser;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRoute<T, E extends RouteBundleExtras> implements IRoute, RouteInterceptorAction<T> {
    protected URIParser parser;
    protected Bundle bundle;
    protected E extras;
    protected RouteCallback callback;
    protected Uri uri;
    protected RouteMap routeMap = null;

    public T addInterceptor(RouteInterceptor interceptor) {
        if (getExtras() != null) {
            extras.addInterceptor(interceptor);
        }
        return (T) this;
    }

    @Override
    public T removeInterceptor(RouteInterceptor interceptor) {
        if (getExtras() != null) {
            extras.removeInterceptor(interceptor);
        }
        return (T) this;
    }

    @Override
    public T removeAllInterceptors() {
        if (getExtras() != null) {
            extras.removeAllInterceptors();
        }
        return (T) this;
    }

    @Override
    public List<RouteInterceptor> getInterceptors() {
        List<RouteInterceptor> interceptors = new ArrayList<>();
        // add global interceptor
        if (RouteManager.get().getGlobalInterceptor() != null) {
            interceptors.add(RouteManager.get().getGlobalInterceptor());
        }

        // add extra interceptors
        if (getExtras() != null) {
            interceptors.addAll(extras.getInterceptors());
        }

        return interceptors;
    }

    public void setCallback (RouteCallback callback) {
        if (callback != null) {
            this.callback = callback;
        }
    }

    public Bundle getBundle() {
        if (bundle == null) {
            bundle = Utils.parseRouteMapToBundle(parser, routeMap);
        }
        return bundle;
    }

    protected E getExtras() {
        if (extras == null) {
            extras = createExtras();
        }
        return extras;
    }

    protected abstract E createExtras();

}
