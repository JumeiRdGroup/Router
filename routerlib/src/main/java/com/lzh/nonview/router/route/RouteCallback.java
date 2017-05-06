package com.lzh.nonview.router.route;

import android.net.Uri;

import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.exception.NotFoundException.NotFoundType;
import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.module.ActionRouteRule;
import com.lzh.nonview.router.module.ActivityRouteRule;

/**
 * The route callback to notify the status of routing event.
 * @author haoge
 */
public interface RouteCallback {

    /**
     * There are two types of not found exception:<br>
     * <pre>
     *     <li><b>{@link NotFoundType#SCHEME}: </b>This uri can't match the corresponding routing</li><br>
     *     <li><b>{@link NotFoundType#CLZ}: </b>The special routing event that matched with uri does not exist.</li>
     * </pre>
     * @param uri uri the uri to open
     * @param e {@link NotFoundException}
     */
    void notFound(Uri uri, NotFoundException e);

    /**
     * This method will be invoked when the routing task opened successful
     *
     * @param uri the uri to open
     * @param rule The uri matching rule, it could be {@link ActionRouteRule} or {@link ActivityRouteRule}
     */
    void onOpenSuccess(Uri uri,RouteRule rule);

    /**
     * A callback method to notice that you occurs some exception.<br>
     *     exclude <i>{@link NotFoundException}</i>
     * @param uri the uri to open
     * @param e the exception
     */
    void onOpenFailed(Uri uri,Throwable e);

    RouteCallback EMPTY = new RouteCallback() {

        @Override
        public void notFound(Uri uri, NotFoundException e) {}

        @Override
        public void onOpenSuccess(Uri uri, RouteRule rule) {}

        @Override
        public void onOpenFailed(Uri uri, Throwable e) {}
    };
}
