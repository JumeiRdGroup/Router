package com.lzh.nonview.router.route;

import android.net.Uri;

/**
 * Created by admin on 16/9/5.
 */
public interface RouteCallback {

    /**
     *
     * @param uri
     * @return true if
     */
    boolean interceptOpen(Uri uri);

    void routeNotFound(Uri uri);

    void onOpenSuccess(Uri uri);

    void onOpenFailed(Uri uri,Exception e);
}
