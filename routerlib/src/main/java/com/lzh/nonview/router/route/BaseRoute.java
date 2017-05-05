package com.lzh.nonview.router.route;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

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

    // ========Unify method of IRoute=======
    @Override
    public final void open(Context context, Uri uri) {
        try {
            Utils.checkInterceptor(uri, getExtras(),context,getInterceptors());

            BaseRoute route = (BaseRoute) getRoute(uri);
            route.realOpen(context);

            callback.onOpenSuccess(uri,routeMap.getClzName());
        } catch (Throwable e) {
            if (e instanceof NotFoundException) {
                callback.notFound(uri, (NotFoundException) e);
            } else {
                callback.onOpenFailed(uri,e);
            }
        }
    }

    @Override
    public final boolean canOpenRouter(Uri uri) {
        try {
            return getRouteMap(uri) != null;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public final IRoute getRoute(Uri uri) {
        try {
            return getRouteInternal(uri);
        } catch (Throwable e) {
            callback.onOpenFailed(uri,e);
            return EmptyRoute.get();
        }
    }

    private IRoute getRouteInternal(Uri uri) {
        this.uri = uri;
        this.parser = new URIParser(uri);
        this.extras = getExtras();
        this.parser = new URIParser(uri);
        this.routeMap = getRouteMap(uri);
        this.bundle = getBundle();
        return this;
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

    // ========getter/setter============
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

    public T setExtras(E extras) {
        if (extras != null) {
            this.extras = extras;
        }
        return (T) this;
    }

    protected E getExtras() {
        if (extras == null) {
            extras = createExtras();
        }
        return extras;
    }

    // ============abstract methods============
    protected abstract E createExtras();

    protected abstract RouteMap getRouteMap(Uri uri);

    protected abstract void realOpen(Context context) throws Throwable;

}
