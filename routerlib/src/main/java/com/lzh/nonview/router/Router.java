package com.lzh.nonview.router;

import android.content.Context;
import android.net.Uri;

import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.parser.RouterParser;
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

    private static RouteCallback callback;

    public static void open (String url,Context context) {
        open(url,context,null);
    }

    public static void open (String url, Context context,RouteCallback callback) {
        open(Uri.parse(url),context,callback);
    }

    public static void open (Uri uri, Context context,RouteCallback callback) {
        ActivityRoute activityRoute;
        callback = callback == null ? Router.callback : callback;
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

    public static IRoute getRoute (String url) {
        return getRoute(url,null);
    }

    public static IRoute getRoute (String url,RouteCallback callback) {
        return getRoute(Uri.parse(url),callback);
    }

    public static IRoute getRoute (Uri uri,RouteCallback callback) {
        ActivityRoute activityRoute;
        callback = callback == null ? Router.callback : callback;
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

    public static void setRouteCallback (RouteCallback callback) {
        Router.callback = callback;
    }

    public static void init(Context context) {
        try {
            RouterParser.INSTANCE.parse(context);
        } catch (Exception e) {
            throw new RuntimeException("Router init failed:Cause by:" + e.getMessage(),e);
        }
    }

}
