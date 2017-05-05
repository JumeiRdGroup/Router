package com.lzh.nonview.router.module;

import android.support.annotation.NonNull;

import com.lzh.nonview.router.route.ActionSupport;

public class ActionRouteMap extends RouteMap<ActionRouteMap>{

    public <T extends ActionSupport> ActionRouteMap(@NonNull Class<T> clz) {
        super(clz.getCanonicalName());
    }
}
