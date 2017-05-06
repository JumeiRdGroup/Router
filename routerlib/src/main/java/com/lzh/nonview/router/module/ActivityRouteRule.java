package com.lzh.nonview.router.module;


import android.app.Activity;
import android.support.annotation.NonNull;

public class ActivityRouteRule extends RouteRule<ActivityRouteRule> {

    public <T extends Activity> ActivityRouteRule(@NonNull Class<T> clz) {
        super(clz.getCanonicalName());
    }


}
