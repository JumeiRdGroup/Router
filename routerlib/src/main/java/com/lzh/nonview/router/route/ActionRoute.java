package com.lzh.nonview.router.route;

import android.content.Context;
import android.net.Uri;

/**
 * Created by haoge on 2017/5/3.
 */

public class ActionRoute implements IRoute{
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
}
