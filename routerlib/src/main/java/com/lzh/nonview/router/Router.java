package com.lzh.nonview.router;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.module.RouteCreator;
import com.lzh.nonview.router.module.RouteMap;
import com.lzh.nonview.router.route.ActivityRoute;
import com.lzh.nonview.router.route.BrowserRoute;
import com.lzh.nonview.router.route.EmptyActivityRoute;
import com.lzh.nonview.router.route.IActivityRoute;
import com.lzh.nonview.router.route.IRoute;
import com.lzh.nonview.router.route.RouteCallback;
import com.lzh.nonview.router.route.RouteInterceptor;
import com.lzh.nonview.router.route.RouteInterceptorAction;


/**
 * Entry of router lib,you can call open to open immediately or getRoute to set some extra data later;
 * Created by lzh on 16/9/5.
 */
public final class Router implements RouteInterceptorAction{

    private Uri uri;
    private RouteCallback callback;

    private Router(Uri uri) {
        this.uri = Utils.completeUri(uri);
    }

    /**
     * create Router by url
     * @param url the url to create Router
     * @return new Router
     */
    public static Router create(String url) {
        return new Router(Uri.parse(url));
    }

    /**
     * create Router by uri
     * @param uri the uri to create Router
     * @return new Router
     */
    public static Router create(Uri uri) {
        return new Router(uri);
    }

    /**
     * Set call back to invoke when open in Router
     * @param callback if set to null,it will use global route callback that you set via {@link Router#setGlobalRouteCallback(RouteCallback)} before
     * @return Router itself
     */
    public Router setCallback (RouteCallback callback) {
        this.callback = callback;
        return this;
    }

    /**
     * Open route by rui.it checked scheme first.<br>
     *     if scheme set is http or https.should be opened by {@link BrowserRoute},
     *     or opened with {@link ActivityRoute} otherwise.
     * @param context context to startActivity
     */
    public void open(Context context) {
        ActivityRoute activityRoute;

        callback = callback == null ? RouteManager.get().getCallback() : callback;
        if (BrowserRoute.getInstance().canOpenRouter(uri)) {
            BrowserRoute.getInstance().open(context,uri);
        } else if ((activityRoute = new ActivityRoute()).canOpenRouter(uri)) {
            activityRoute.setCallback(callback);
            activityRoute.addInterceptor(RouteManager.get().getInterceptor());
            activityRoute.open(context,uri);
        } else {
            callback.notFound(uri,new NotFoundException(String.format("find route by uri %s failed:",uri),
                    NotFoundException.NotFoundType.SCHEME,uri.toString()));
        }
    }

    /**
     * Get route by uri,you should get a route by this way and set some extras data before open
     * @return A IRoute object.it would be {@link BrowserRoute} or {@link ActivityRoute}
     */
    public IRoute getRoute () {
        if (BrowserRoute.getInstance().canOpenRouter(uri)) {
            return BrowserRoute.getInstance().getRoute(uri);
        }
        return (IRoute) getActivityRoute();
    }

    /**
     * Get {@link ActivityRoute} by uri,you should get a route by this way and set some extras data before open
     * @return ActivityRoute
     */
    public IActivityRoute getActivityRoute () {
        ActivityRoute activityRoute;
        callback = callback == null ? RouteManager.get().getCallback() : callback;
        if ((activityRoute = new ActivityRoute()).canOpenRouter(uri)) {
            activityRoute.setCallback(callback);
            activityRoute.addInterceptor(RouteManager.get().getInterceptor());
            return (IActivityRoute) activityRoute.getRoute(uri);
        }
        callback.notFound(uri,
                new NotFoundException(String.format("find route by uri %s failed:",uri),
                        NotFoundException.NotFoundType.SCHEME,uri.toString()));
        // return a empty route to avoid NullPointException
        return EmptyActivityRoute.get();
    }

    /**
     * Set global route callback to invoked when open a uri
     * @param callback can't be null
     */
    public static void setGlobalRouteCallback (RouteCallback callback) {
        RouteManager.get().setCallback(callback);
    }

    public static void setGlobalRouteInterceptor (RouteInterceptor interceptor) {
        RouteManager.get().setInterceptor(interceptor);
    }

    /**
     * set to create route rules
     * @param creator Route rules creator.can't be null
     */
    public static void addRouteCreator(RouteCreator creator) {
        RouteManager.get().addCreator(creator);
    }

    @Override
    public void addInterceptor(RouteInterceptor interceptor) {

    }

    @Override
    public void removeInterceptor(RouteInterceptor interceptor) {

    }

    @Override
    public void removeAllInterceptors() {

    }
}
