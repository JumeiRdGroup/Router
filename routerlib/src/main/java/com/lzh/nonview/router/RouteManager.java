/*
 * Copyright (C) 2017 Haoge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzh.nonview.router;

import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.module.ActionRouteRule;
import com.lzh.nonview.router.module.ActivityRouteRule;
import com.lzh.nonview.router.module.RouteCreator;
import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.parser.URIParser;
import com.lzh.nonview.router.route.ActionSupport;
import com.lzh.nonview.router.route.RouteCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A single data manager to manage some route configurations.
 * @author haoge
 */
public final class RouteManager {
    private final static RouteManager manager = new RouteManager();
    private RouteManager() {}
    public static RouteManager get() {
        return manager;
    }

    /**
     * global route interceptor
     */
    private RouteInterceptor globalInterceptor;
    /**
     * global route callback
     */
    private RouteCallback globalCallback;
    // provide a empty callback to make it more safer
    private RouteCallback EmptyCallback = RouteCallback.EMPTY;
    private boolean shouldReload;// if should be reload routeMap.
    /**
     * A container to contains all of route rule creator,compat with some complex scene;
     */
    private List<RouteCreator> creatorList = new ArrayList<>();
    /**
     * A map to contains all of route rule created by creatorList
     */
    private Map<String,ActivityRouteRule> activityRouteMap = new HashMap<>();
    private Map<String,ActionRouteRule> actionRouteMap = new HashMap<>();
    public static final int TYPE_ACTIVITY_ROUTE = 0;
    public static final int TYPE_ACTION_ROUTE = 1;
    private Map<String, ActionSupport> supportMap = new HashMap<>();

    /**
     * Set a global route callback to notify users the status of routing event.
     * @param callback route callback
     * @see RouteCallback
     */
    void setCallback (RouteCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback should not be null");
        }
        this.globalCallback = callback;
    }

    /**
     * Set a global route interceptor to handle routing event whether should be intercepted.
     * @param interceptor {@link RouteInterceptor}
     */
    void setInterceptor (RouteInterceptor interceptor) {
        this.globalInterceptor = interceptor;
    }

    /**
     * <p>
     * Add a {@link RouteCreator} who contains some route rules to be used.
     * this method could be invoked multiple-times. so that you can put multiple route rules from difference modules
     * </p>
     * @param creator {@link RouteCreator}
     */
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
    public RouteCallback getCallback() {
        return globalCallback == null ? EmptyCallback : globalCallback;
    }

    public RouteInterceptor getGlobalInterceptor() {
        return globalInterceptor;
    }

    private Map<String, ActionRouteRule> getActionRouteMap() {
        obtainRouteRulesIfNeed();
        return actionRouteMap;
    }

    private Map<String,ActivityRouteRule> getActivityRouteMap() {
        obtainRouteRulesIfNeed();
        return activityRouteMap;
    }

    public RouteRule getRouteMapByUri(URIParser parser, int type) {
        String route = parser.getScheme() + "://" + parser.getHost();
        Map routes;
        if (type == TYPE_ACTIVITY_ROUTE) {
            routes = getActivityRouteMap();
        } else {
            routes = getActionRouteMap();
        }
        String wrap = Utils.wrapScheme(route);
        if (routes.containsKey(wrap)) {
            return (RouteRule) routes.get(wrap);
        }
        String unWrap = Utils.unwrapScheme(wrap);
        return (RouteRule) routes.get(unWrap);
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
