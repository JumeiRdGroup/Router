package com.lzh.nonview.router;

import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.module.ActionRouteMap;
import com.lzh.nonview.router.module.ActivityRouteMap;
import com.lzh.nonview.router.module.RouteCreator;
import com.lzh.nonview.router.module.RouteMap;
import com.lzh.nonview.router.parser.URIParser;
import com.lzh.nonview.router.route.ActionSupport;
import com.lzh.nonview.router.route.RouteCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A container to manage some global config.
 * Created by lzh on 16/9/8.
 */
public final class RouteManager {
    private final static RouteManager manager = new RouteManager();
    private RouteManager() {}
    public static RouteManager get() {
        return manager;
    }

    /**
     * A global route interceptor
     */
    private RouteInterceptor globalInterceptor;
    /**
     * global route callback
     */
    private RouteCallback globalCallback;
    // provide a empty callback to make it more safely
    private RouteCallback EmptyCallback = RouteCallback.EMPTY;
    private boolean shouldReload;// if should be reload routeMap.
    /**
     * A container to contains all of route rule creator,compat with some complex scene;
     */
    private List<RouteCreator> creatorList = new ArrayList<>();
    /**
     * A map to contains all of route rule created by creatorList
     */
    private Map<String,ActivityRouteMap> activityRouteMap = new HashMap<>();
    private Map<String,ActionRouteMap> actionRouteMap = new HashMap<>();
    public static final int TYPE_ACTIVITY_ROUTE = 0;
    public static final int TYPE_ACTION_ROUTE = 1;
    private Map<String, ActionSupport> supportMap = new HashMap<>();

    void setCallback (RouteCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback should not be null");
        }
        this.globalCallback = callback;
    }

    void setInterceptor (RouteInterceptor interceptor) {
        this.globalInterceptor = interceptor;
    }

    void addCreator (RouteCreator creator) {
        if (creator == null) {
            throw new IllegalArgumentException("Route creator should not be null");
        }
        this.creatorList.add(creator);
        shouldReload = true;
    }

    /**
     * obtain a callback put to use. will not be null.
     * @return if you had not set yet, it will returns {@link RouteCallback#EMPTY}
     */
    RouteCallback getCallback() {
        return globalCallback == null ? EmptyCallback : globalCallback;
    }

    public RouteInterceptor getGlobalInterceptor() {
        return globalInterceptor;
    }

    private Map<String, ActionRouteMap> getActionRouteMap() {
        obtainRouteRulesIfNeed();
        return actionRouteMap;
    }

    private Map<String,ActivityRouteMap> getActivityRouteMap() {
        obtainRouteRulesIfNeed();
        return activityRouteMap;
    }

    public RouteMap getRouteMapByUri(URIParser parser, int type) {
        String route = parser.getScheme() + "://" + parser.getHost();
        Map routes;
        if (type == TYPE_ACTIVITY_ROUTE) {
            routes = getActivityRouteMap();
        } else {
            routes = getActionRouteMap();
        }
        String wrap = Utils.wrapScheme(route);
        if (routes.containsKey(wrap)) {
            return (RouteMap) routes.get(wrap);
        }
        String unWrap = Utils.unwrapScheme(wrap);
        return (RouteMap) routes.get(unWrap);
    }

    private void obtainRouteRulesIfNeed() {
        if (shouldReload) {
            activityRouteMap.clear();
            actionRouteMap.clear();
            int count = creatorList == null ? 0 : creatorList.size();
            for (int i = 0; i < count; i++) {
                addAll(activityRouteMap, creatorList.get(i).createActivityRouteRules());
                addAll(actionRouteMap, creatorList.get(i).createActionRouteRules());
            }
            shouldReload = false;
        }
    }

    private <T, R> void addAll(Map<T, R> src, Map<T, R> target) {
        if (target != null && src != null) {
            src.putAll(target);
        }
    }

}
