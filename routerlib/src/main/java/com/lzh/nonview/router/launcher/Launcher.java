package com.lzh.nonview.router.launcher;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.module.RouteRule;

/**
 * The base launcher class.
 * @param <T> The route rule
 * @param <E> The route extras
 */
public abstract class Launcher<T extends RouteRule, E extends RouteBundleExtras> {
    protected Uri uri;
    protected Bundle bundle;
    protected E extras;
    protected T rule;

    public abstract void open(Context context) throws Exception;

    public final void set(Uri uri, Bundle bundle, E extras, T rule) {
        this.uri = uri;
        this.bundle = bundle;
        this.extras = extras;
        this.rule = rule;
    }
}
