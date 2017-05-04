package com.lzh.nonview.router.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;

import java.util.ArrayList;
import java.util.List;

public final class EmptyActivityRoute implements IActivityRoute,IRoute{
    private static EmptyActivityRoute EMPTY = new EmptyActivityRoute();
    private EmptyActivityRoute(){}
    public static EmptyActivityRoute get () {
        return EMPTY;
    }

    @Override
    public void open(Context context) {
        // empty
    }

    @Override
    public Intent createIntent(Context context) {
        // avoid NullPointException
        return new Intent();
    }

    @Override
    public IActivityRoute requestCode(int requestCode) {
        // empty
        return this;
    }

    @Override
    public IActivityRoute setAnim(int enterAnim, int exitAnim) {
        // empty
        return this;
    }

    @Override
    public IActivityRoute addExtras(Bundle extras) {
        return this;
    }

    @Override
    public IActivityRoute addFlags(int flag) {
        return this;
    }

    @Override
    public void open(Context context, Uri uri) {
        // empty
    }

    @Override
    public boolean canOpenRouter(Uri uri) {
        return true;
    }

    @Override
    public IRoute getRoute(Uri uri) {
        return this;
    }

    @Override
    public void resumeRoute(Context context, Uri uri, RouteBundleExtras extras) {
        // empty
    }

    @Override
    public IActivityRoute addInterceptor(RouteInterceptor interceptor) {
        // empty
        return this;
    }

    @Override
    public IActivityRoute removeInterceptor(RouteInterceptor interceptor) {
        // empty
        return this;
    }

    @Override
    public IActivityRoute removeAllInterceptors() {
        // empty
        return this;
    }

    @Override
    public List<RouteInterceptor> getInterceptors() {
        // Avoid cause NullPointException
        return new ArrayList<>();
    }
}
