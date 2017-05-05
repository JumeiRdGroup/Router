package com.lzh.nonview.router.route;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lzh.nonview.router.RouteManager;
import com.lzh.nonview.router.Utils;
import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.interceptors.RouteInterceptorAction;
import com.lzh.nonview.router.module.RouteMap;
import com.lzh.nonview.router.parser.URIParser;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRoute<T, E extends RouteBundleExtras> implements IRoute, IBaseRoute<T>, RouteInterceptorAction<T> {
    protected URIParser parser;
    protected Bundle bundle;
    protected E extras;
    protected RouteCallback callback = RouteCallback.EMPTY;
    protected Uri uri;
    protected RouteMap routeMap = null;

    public final IRoute create(Uri uri, RouteCallback callback) {
        try {
            this.uri = uri;
            this.callback = callback;
            this.parser = new URIParser(uri);
            this.extras = createExtras();
            this.routeMap = obtainRouteMap();
            this.bundle = Utils.parseRouteMapToBundle(parser, routeMap);
            return this;
        } catch (Throwable e) {
            callback.onOpenFailed(uri,e);
            return IRoute.EMPTY;
        }
    }

    // =========Unify method of IBaseRoute
    @Override
    public final void open(Context context) {
        try {
            Utils.checkInterceptor(uri, extras,context,getInterceptors());
            realOpen(context);
            callback.onOpenSuccess(uri,routeMap.getClzName());
        } catch (Throwable e) {
            if (e instanceof NotFoundException) {
                callback.notFound(uri, (NotFoundException) e);
            } else {
                callback.onOpenFailed(this.uri,e);
            }
        }
    }

    @Override
    public T addExtras(Bundle extras) {
        this.extras.addExtras(extras);
        return (T) this;
    }

    // =============RouteInterceptor operation===============
    public T addInterceptor(RouteInterceptor interceptor) {
        if (extras != null) {
            extras.addInterceptor(interceptor);
        }
        return (T) this;
    }

    @Override
    public T removeInterceptor(RouteInterceptor interceptor) {
        if (extras != null) {
            extras.removeInterceptor(interceptor);
        }
        return (T) this;
    }

    @Override
    public T removeAllInterceptors() {
        if (extras != null) {
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
        if (extras != null) {
            interceptors.addAll(extras.getInterceptors());
        }

        return interceptors;
    }

    // ========getter/setter============
    public T replaceExtras(E extras) {
        if (extras != null) {
            this.extras = extras;
        }
        return (T) this;
    }

    // ============abstract methods============
    protected abstract @NonNull E createExtras();

    protected abstract @Nullable RouteMap obtainRouteMap();

    protected abstract void realOpen(Context context) throws Throwable;

}
