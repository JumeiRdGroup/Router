package com.lzh.nonview.router.route;

import android.content.Context;
import android.net.Uri;

import com.lzh.nonview.router.extras.RouteBundleExtras;

public interface IRoute {

    /**
     * open route with uri by context
     * @param context The context to invoked startActivity
     * @param uri The uri to open
     */
    void open(Context context, Uri uri);

    /**
     * if this uri should be opened by this route
     * @param uri The uri to open
     * @return whether or not can open this uri
     */
    boolean canOpenRouter(Uri uri);

    /**
     * get route by uri if route should be opened by this uri
     * @param uri The uri to open
     * @return route
     */
    IRoute getRoute(Uri uri);

    void resumeRoute(Context context, Uri uri, RouteBundleExtras extras);

    IRoute EMPTY = new IRoute() {
        @Override
        public void open(Context context, Uri uri) {
            // do empty
        }

        @Override
        public boolean canOpenRouter(Uri uri) {
            return false;
        }

        @Override
        public IRoute getRoute(Uri uri) {
            return this;
        }

        @Override
        public void resumeRoute(Context context, Uri uri, RouteBundleExtras extras) {}
    };
}
