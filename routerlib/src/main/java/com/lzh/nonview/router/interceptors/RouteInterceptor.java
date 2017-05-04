package com.lzh.nonview.router.interceptors;

import android.content.Context;
import android.net.Uri;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.extras.ActivityRouteBundleExtras;

/**
 * A interceptor interface
 * @author lzh
 */
public interface RouteInterceptor{

    /**
     * Whether or not be interrupted when open activity by uri
     * @param uri uri the uri to open
     * @param context context
     * @param extras some extras data for route,
     *               sometimes is null when you not use
     *               {@link Router#getActivityRoute()}  to set some extras data into it
     * @return true if should be intercepted
     */
    boolean intercept (Uri uri, ActivityRouteBundleExtras extras, Context context);

    /**
     * This method should be invoked when you has been intercepted
     * @param uri uri the uri to open
     * @param context context
     * @param extras some extras data for route,
     *               sometimes is null when you not use
     *               {@link Router#getActivityRoute()}  to set some extras data into it
     */
    void onIntercepted(Uri uri, ActivityRouteBundleExtras extras, Context context);
}
