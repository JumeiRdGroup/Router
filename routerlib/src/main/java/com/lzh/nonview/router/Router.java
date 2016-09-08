package com.lzh.nonview.router;

import android.content.Context;
import android.net.Uri;

import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.module.RouteCreator;
import com.lzh.nonview.router.route.ActivityRoute;
import com.lzh.nonview.router.route.BrowserRoute;
import com.lzh.nonview.router.route.EmptyActivityRoute;
import com.lzh.nonview.router.route.IRoute;
import com.lzh.nonview.router.route.RouteCallback;


/**
 * Entry of router lib,you can call open to open immediately or getRoute to set some extra data later;
 * Created by lzh on 16/9/5.
 */
public class Router {

    /**
     * {@link Router#open(Uri, Context, RouteCallback)}
     */
    public static void open (String url,Context context) {
        open(url,context,null);
    }

    /**
     * {@link Router#open(Uri, Context, RouteCallback)}
     */
    public static void open (String url, Context context,RouteCallback callback) {
        open(Uri.parse(url),context,callback);
    }

    /**
     * Open route by rui.it checked scheme first.<br>
     *     if scheme set is http or https.should be opened by browser,
     *     or opened with ActivityRoute otherwise.
     * @param uri The uri to check to open
     * @param context context to startActivity
     * @param callback if set to null,should use global route callback that you had set by {@link Router#setRouteCallback(RouteCallback)} before
    */
    public static void open (Uri uri, Context context,RouteCallback callback) {
        ActivityRoute activityRoute;
        callback = callback == null ? RouteManager.INSTANCE.getCallback() : callback;
        if (BrowserRoute.getInstance().canOpenRouter(uri)) {
            BrowserRoute.getInstance().open(context,uri);
        } else if ((activityRoute = new ActivityRoute()).canOpenRouter(uri)) {
            activityRoute.setCallback(callback);
            activityRoute.open(context,uri);
        } else {
            callback.notFound(uri,new NotFoundException(String.format("find route by uri %s failed:",uri),
                    NotFoundException.NotFoundType.SCHEME,uri.toString()));
        }
    }

    /**
     * {@link Router#getRoute(Uri, RouteCallback)}
     */
    public static IRoute getRoute (String url) {
        return getRoute(url,null);
    }

    /**
     * {@link Router#getRoute(Uri, RouteCallback)}
     */
    public static IRoute getRoute (String url,RouteCallback callback) {
        return getRoute(Uri.parse(url),callback);
    }

    /**
     * Get route by uri,you should get a route by this way and set some extras data before open
     * @param uri The uri to check to open
     * @param callback if set to null,should use global route callback that you had set by {@link Router#setRouteCallback(RouteCallback)} before
     * @return A IRoute object.it would be {@link BrowserRoute} or {@link ActivityRoute}
     */
    public static IRoute getRoute (Uri uri,RouteCallback callback) {
        ActivityRoute activityRoute;
        callback = callback == null ? RouteManager.INSTANCE.getCallback() : callback;
        if (BrowserRoute.getInstance().canOpenRouter(uri)) {
            return BrowserRoute.getInstance().getRoute(uri);
        } else if ((activityRoute = new ActivityRoute()).canOpenRouter(uri)) {
            activityRoute.setCallback(callback);
            return activityRoute.getRoute(uri);
        }
        callback.notFound(uri,
                new NotFoundException(String.format("find route by uri %s failed:",uri),
                        NotFoundException.NotFoundType.SCHEME,uri.toString()));
        // return a empty route to avoid NullPointException
        return new EmptyActivityRoute();
    }

    /**
     * Set global route callback to invoked when open a uri
     * @param callback can't be null
     */
    public static void setRouteCallback (RouteCallback callback) {
        RouteManager.INSTANCE.setCallback(callback);
    }

    /**
     * set to create route rules
     * @param creator Route rules creator.can't be null
     */
    public static void addRouteCreator(RouteCreator creator) {
        RouteManager.INSTANCE.addCreator(creator);
    }

}
