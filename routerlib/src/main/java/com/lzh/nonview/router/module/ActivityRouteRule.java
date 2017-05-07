package com.lzh.nonview.router.module;


import android.app.Activity;

public class ActivityRouteRule extends RouteRule<ActivityRouteRule> {

    public <T extends Activity> ActivityRouteRule(Class<T> clz) {
        super(clz.getCanonicalName());
    }


}
