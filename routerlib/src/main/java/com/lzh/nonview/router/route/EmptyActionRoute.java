package com.lzh.nonview.router.route;

import android.content.Context;
import android.net.Uri;

import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;

import java.util.ArrayList;
import java.util.List;

public class EmptyActionRoute implements IActionRoute, IRoute{
    private static EmptyActionRoute empty = new EmptyActionRoute();
    public static EmptyActionRoute get () {
        return empty;
    }
    @Override
    public IActionRoute addInterceptor(RouteInterceptor interceptor) {
        return this;
    }

    @Override
    public IActionRoute removeInterceptor(RouteInterceptor interceptor) {
        return this;
    }

    @Override
    public IActionRoute removeAllInterceptors() {
        return this;
    }

    @Override
    public List<RouteInterceptor> getInterceptors() {
        return new ArrayList<>();
    }

    @Override
    public void open(Context context, Uri uri) {}

    @Override
    public boolean canOpenRouter(Uri uri) {
        return false;
    }

    @Override
    public IRoute getRoute(Uri uri) {
        return this;
    }

    @Override
    public void resumeRoute(Context context, Uri uri, RouteBundleExtras extras) {}
}
