package com.lzh.nonview.router.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class EmptyActivityRoute implements IActivityRoute,IRoute{

    @Override
    public void open(Context context) {
        // empty
    }

    @Override
    public Intent createIntent(Context context) {
        return new Intent();
    }

    @Override
    public IActivityRoute requestCode(int requestCode) {
        // empty
        return this;
    }

    @Override
    public IActivityRoute setAnim(int enterAnim, int exitAnim) {
        // empty
        return this;
    }

    @Override
    public IActivityRoute addExtras(Bundle extras) {
        return this;
    }

    @Override
    public IActivityRoute addFlags(int flag) {
        return null;
    }

    @Override
    public IActivityRoute replaceBundleExtras(ActivityRouteBundleExtras extras) {
        // empty
        return this;
    }

    @Override
    public void open(Context context, Uri uri) {
        // empty
    }

    @Override
    public boolean canOpenRouter(Uri uri) {
        return true;
    }

    @Override
    public IRoute getRoute(Uri uri) {
        return this;
    }
}
