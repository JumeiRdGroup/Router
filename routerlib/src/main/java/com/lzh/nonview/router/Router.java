/*
 * Copyright (C) HaoGe <a href="https://github.com/yjfnypeu"/>
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

import android.content.Context;
import android.net.Uri;

import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.extras.ActionRouteBundleExtras;
import com.lzh.nonview.router.extras.ActivityRouteBundleExtras;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.module.RouteCreator;
import com.lzh.nonview.router.route.ActionRoute;
import com.lzh.nonview.router.route.ActivityRoute;
import com.lzh.nonview.router.route.BrowserRoute;
import com.lzh.nonview.router.route.IActionRoute;
import com.lzh.nonview.router.route.IActivityRoute;
import com.lzh.nonview.router.route.IBaseRoute;
import com.lzh.nonview.router.route.IRoute;
import com.lzh.nonview.router.route.RouteCallback;


/**
 * Entry of Router。
 *
 * @author haoge
 */
public final class Router{

    /**
     * The key of raw uri. you can obtains it uri by this key, for eg:
     * <pre>
     *      <i><b>bundle.getParcelable(Router.RAW_URI)</b></i>
     * </pre>
     */
    public static final String RAW_URI = "_ROUTER_RAW_URI_KEY_";

    private Uri uri;
    private RouteCallback callback;
    private boolean isCallNotFound = false;

    private Router(Uri uri) {
        this.uri = Utils.completeUri(uri);
    }

    /**
     * Create Router by url string
     * @param url The url to create Router
     * @return new Router
     */
    public static Router create(String url) {
        return new Router(Uri.parse(url));
    }

    /**
     * Create Router by uri
     * @param uri the uri to create Router
     * @return new Router
     */
    public static Router create(Uri uri) {
        return new Router(uri);
    }

    /**
     * Set a callback to notify the user when the routing were success or failure.
     * @param callback The callback you set.
     * @return Router itself
     *
     * @see Router#getCallback()
     */
    public Router setCallback (RouteCallback callback) {
        this.callback = callback;
        return this;
    }

    /**
     * Obtain a callback put to use. will not be null.
     * @return if you had not set yet, it will returns a global callback obtain from {@link RouteManager#getCallback()}
     */
    private RouteCallback getCallback () {
        return callback == null ? RouteManager.get().getCallback() : callback;
    }

    /**
     * Restore a Routing event from last uri and extras.
     * @param uri last uri
     * @param extras last extras
     * @return The restored routing find by {@link Router#getRoute()}
     */
    public static IRoute resume(Uri uri, RouteBundleExtras extras) {
        IRoute route = Router.create(uri).getRoute();
        if (route instanceof ActivityRoute && extras instanceof ActivityRouteBundleExtras) {
            ((ActivityRoute) route).replaceExtras((ActivityRouteBundleExtras) extras);
        } else if (route instanceof ActionRoute && extras instanceof ActionRouteBundleExtras) {
            ((ActionRoute) route).replaceExtras((ActionRouteBundleExtras) extras);
        }
        return route;
    }

    /**
     * launch routing task.
     * @param context context to launched
     */
    public void open(Context context) {
        getRoute().open(context);
    }

    /**
     * Get route by uri, you should get a route by this way and set some extras data before open
     * @return
     *  An IRoute object.it will be {@link BrowserRoute}, {@link ActivityRoute} or {@link ActionRoute}.<br>
     *  and it also will be {@link IRoute#EMPTY} if it not found
     */
    public IRoute getRoute () {
        if (ActionRoute.canOpenRouter(uri)) {
            return new ActionRoute().create(uri, getCallback());
        } else if (ActivityRoute.canOpenRouter(uri)) {
            return new ActivityRoute().create(uri, getCallback());
        } else if (BrowserRoute.canOpenRouter(uri)) {
            return BrowserRoute.getInstance().setUri(uri);
        } else {
            notifyNotFound(String.format("find Route by %s failed:",uri));
            return IRoute.EMPTY;
        }
    }

    /**
     * <p>
     * Get {@link IBaseRoute} by uri, it could be one of {@link IActivityRoute} or {@link IActionRoute}.
     * and you can add some {@link android.os.Bundle} data and {@link RouteInterceptor} into it.
     * </p>
     * @return returns an {@link IBaseRoute} finds by uri or {@link IBaseRoute#EMPTY} for not found
     */
    public IBaseRoute getBaseRoute() {
        IRoute route = getRoute();
        if (route instanceof IBaseRoute) {
            return (IBaseRoute) route;
        }

        notifyNotFound(String.format("find BaseRoute by %s failed:",uri));
        return IBaseRoute.EMPTY;
    }

    /**
     * Get {@link IActivityRoute} by uri,you should get a route by this way and set some extras data before open
     * @return returns an {@link IActivityRoute} finds by uri or {@link IActivityRoute#EMPTY} for not found.
     */
    public IActivityRoute getActivityRoute() {
        IRoute route = getRoute();
        if (route instanceof IActivityRoute) {
            return (IActivityRoute) route;
        }

        // return an empty route to avoid NullPointException
        notifyNotFound(String.format("find Activity Route by %s failed:",uri));
        return IActivityRoute.EMPTY;
    }

    /**
     * Get {@link IActionRoute} by uri,you should get a route by this way and set some extras data before open
     * @return returns an {@link IActionRoute} finds by uri or {@link IActionRoute#EMPTY} for not found.
     */
    public IActionRoute getActionRoute() {
        IRoute route = getRoute();
        if (route instanceof IActivityRoute) {
            return (IActionRoute) route;
        }

        notifyNotFound(String.format("find Action Route by %s failed:",uri));
        // return a empty route to avoid NullPointException
        return IActionRoute.EMPTY;
    }

    /**
     * Set a global route callback to invoked when open a uri
     * @param callback can't be null
     */
    public static void setGlobalRouteCallback (RouteCallback callback) {
        RouteManager.get().setCallback(callback);
    }

    public static void setGlobalRouteInterceptor (RouteInterceptor interceptor) {
        RouteManager.get().setInterceptor(interceptor);
    }

    /**
     * Set to create route rules
     * @param creator Route rules creator.can't be null
     */
    public static void addRouteCreator(RouteCreator creator) {
        RouteManager.get().addCreator(creator);
    }

    private void notifyNotFound(String msg) {
        if (!isCallNotFound) {
            getCallback().notFound(uri, new NotFoundException(msg, NotFoundException.TYPE_SCHEMA, uri.toString()));
        }
        // ensure the method notFound called once.
        isCallNotFound = true;
    }
}
