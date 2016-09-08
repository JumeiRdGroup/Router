package com.lzh.nonview.router;

import com.lzh.nonview.router.module.RouteCreator;
import com.lzh.nonview.router.module.RouteMap;
import com.lzh.nonview.router.route.RouteCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 16/9/8.
 */
public enum RouteManager {
    INSTANCE;
    RouteCallback callback;
    boolean shouldReload;
    List<RouteCreator> creatorList = new ArrayList<>();
    Map<String,RouteMap> routeMap = new HashMap<>();
    public void setCallback (RouteCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback should not be null");
        }
        this.callback = callback;
    }

    public void addCreator (RouteCreator creator) {
        if (creator == null) {
            throw new IllegalArgumentException("Route creator should not be null");
        }
        this.creatorList.add(creator);
        shouldReload = true;
    }

    public RouteCallback getCallback() {
        return callback;
    }

    public Map<String,RouteMap> getRouteMap() {
        if (shouldReload) {
            routeMap.clear();
            int count = creatorList == null ? 0 : creatorList.size();
            for (int i = 0; i < count; i++) {
                routeMap.putAll(creatorList.get(i).initRoute());
            }
            shouldReload = false;
        }
        return routeMap;
    }
}
