package com.lzh.nonview.router.route;

import android.content.Context;
import android.net.Uri;

import com.lzh.nonview.router.exception.NotFoundException;

/**
 * Created by admin on 16/9/5.
 */
public interface RouteCallback {

    /**
     * whether or not be interrupted when open activity by uri
     * @param uri uri
     * @return true if should be intercepted
     */
    boolean interceptOpen(Uri uri, Context context,ActivityRouteBundleExtras extras);

    /**
     * When class of activity with this uri is not found
     * @param uri uri
     * @param e the activity name corresponding of
     */
    void notFound(Uri uri, NotFoundException e);

    void onOpenSuccess(Uri uri,String clzName);

    void onOpenFailed(Uri uri,Exception e);
}
