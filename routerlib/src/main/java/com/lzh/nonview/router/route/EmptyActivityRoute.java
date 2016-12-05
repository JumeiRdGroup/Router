package com.lzh.nonview.router.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public final class EmptyActivityRoute implements IActivityRoute,IRoute{
    private static EmptyActivityRoute EMPTY = new EmptyActivityRoute();
    private EmptyActivityRoute(){}
    public static EmptyActivityRoute get () {
        return EMPTY;
    }

    @Override
    public void setCallback(RouteCallback callback) {

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
    public IActivityRoute replaceBundleExtras(ActivityRouteBundleExtras extras) {
        // empty
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
    public void addInterceptor(RouteInterceptor interceptor) {
        // empty
    }

    @Override
    public void removeInterceptor(RouteInterceptor interceptor) {
        // empty
    }

    @Override
    public void removeAllInterceptors() {
        // empty
    }
}
