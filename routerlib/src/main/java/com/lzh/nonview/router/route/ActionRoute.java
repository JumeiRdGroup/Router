package com.lzh.nonview.router.route;

import android.content.Context;
import android.net.Uri;

import com.lzh.nonview.router.Utils;
import com.lzh.nonview.router.extras.ActionRouteBundleExtras;
import com.lzh.nonview.router.extras.RouteBundleExtras;

public class ActionRoute extends BaseRoute<IActionRoute, ActionRouteBundleExtras> implements IActionRoute{
    @Override
    public void open(Context context, Uri uri) {

    }

    @Override
    public boolean canOpenRouter(Uri uri) {
        return false;
    }

    @Override
    public IRoute getRoute(Uri uri) {
        return null;
    }

    @Override
    public void resumeRoute(Context context, Uri uri, RouteBundleExtras extras) {

    }

    @Override
    protected ActionRouteBundleExtras createExtras() {
        return new ActionRouteBundleExtras();
    }
}
