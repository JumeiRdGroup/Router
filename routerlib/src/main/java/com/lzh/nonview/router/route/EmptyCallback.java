package com.lzh.nonview.router.route;

import android.net.Uri;

/**
 * Created by admin on 16/9/7.
 */
public class EmptyCallback implements RouteCallback {

    @Override
    public boolean interceptOpen(Uri uri) {
        return false;
    }

    @Override
    public void routeNotFound(Uri uri) {

    }

    @Override
    public void onOpenSuccess(Uri uri) {

    }

    @Override
    public void onOpenFailed(Uri uri, Exception e) {

    }
}
