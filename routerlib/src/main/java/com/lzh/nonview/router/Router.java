package com.lzh.nonview.router;

import android.content.Context;
import android.net.Uri;

import com.lzh.nonview.router.parser.RouterParser;
import com.lzh.nonview.router.route.ActivityRoute;
import com.lzh.nonview.router.route.BrowserRoute;
import com.lzh.nonview.router.route.EmptyActivityRoute;
import com.lzh.nonview.router.route.IRoute;
import com.lzh.nonview.router.route.RouteCallback;


/**
 * Created by admin on 16/9/5.
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
        if (BrowserRoute.getInstance().canOpenRouter(uri)) {
            BrowserRoute.getInstance().open(context,uri);
        } else if ((activityRoute = new ActivityRoute()).canOpenRouter(uri)) {
            activityRoute.setCallback(callback == null ? Router.callback : callback);
            activityRoute.open(context,uri);
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
        if (BrowserRoute.getInstance().canOpenRouter(uri)) {
            return BrowserRoute.getInstance().getRoute(uri);
        } else if ((activityRoute = new ActivityRoute()).canOpenRouter(uri)) {
            activityRoute.setCallback(callback == null ? Router.callback : callback);
            return activityRoute.getRoute(uri);
        }
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
