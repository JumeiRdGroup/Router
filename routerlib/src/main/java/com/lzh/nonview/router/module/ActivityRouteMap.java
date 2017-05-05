package com.lzh.nonview.router.module;


import android.app.Activity;
import android.support.annotation.NonNull;

public class ActivityRouteMap extends RouteMap<ActivityRouteMap>{

    public <T extends Activity> ActivityRouteMap(@NonNull Class<T> clz) {
        super(clz.getCanonicalName());
    }


}
