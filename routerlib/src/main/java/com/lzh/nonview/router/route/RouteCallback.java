package com.lzh.nonview.router.route;

import android.content.Context;
import android.net.Uri;

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
     * @param clzName the activity name corresponding of
     */
    void notFound(Uri uri, String clzName);

    void onOpenSuccess(Uri uri,String clzName);

    void onOpenFailed(Uri uri,Exception e);
}
