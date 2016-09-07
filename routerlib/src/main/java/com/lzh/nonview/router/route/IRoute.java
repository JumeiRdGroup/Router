package com.lzh.nonview.router.route;

import android.content.Context;
import android.net.Uri;

/**
 * Created by admin on 16/9/5.
 */
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
}
