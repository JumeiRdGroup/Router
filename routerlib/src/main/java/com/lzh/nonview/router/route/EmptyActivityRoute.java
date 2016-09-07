package com.lzh.nonview.router.route;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by admin on 16/9/7.
 */
public class EmptyActivityRoute implements IActivityRoute{

    @Override
    public void open(Context context) {
        // empty
    }

    @Override
    public IActivityRoute requestCode(int requestCode) {
        // empty
        return this;
    }

    @Override
    public Bundle getExtras() {
        return new Bundle();
    }

    @Override
    public IActivityRoute setAnim(int enterAnim, int exitAnim) {
        // empty
        return this;
    }

    @Override
    public IActivityRoute setExtras(Bundle extras) {
        return this;
    }

    @Override
    public void replaceBundleExtras(ActivityRouteBundleExtras extras) {

    }

    @Override
    public void open(Context context, Uri uri) {
        // empty
    }

    @Override
    public boolean canOpenRouter(Uri uri) {
        // empty
        return false;
    }

    @Override
    public IRoute getRoute(Uri uri) {
        // empty
        return this;
    }
}
