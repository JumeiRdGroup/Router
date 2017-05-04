package com.lzh.nonview.router.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.lzh.nonview.router.Utils;
import com.lzh.nonview.router.extras.RouteBundleExtras;

/**
 * A route tool to open uri by browser
 * Created by lzh on 16/9/5.
 */
public class BrowserRoute implements IRoute {

    private static BrowserRoute route = new BrowserRoute();

    public static BrowserRoute getInstance () {
        return route;
    }

    @Override
    public void open(Context context, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public boolean canOpenRouter(Uri uri) {
        return Utils.isHttp(uri.getScheme());
    }

    @Override
    public IRoute getRoute(Uri uri) {
        return this;
    }

    @Override
    public void resumeRoute(Context context, Uri uri, RouteBundleExtras extras) {

    }
}
