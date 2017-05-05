package com.lzh.nonview.router.route;

import android.content.Context;
import android.os.Bundle;

import com.lzh.nonview.router.extras.ActivityRouteBundleExtras;

public interface IBaseRoute<T> {
    /**
     * launch activity or action
     * @param context context
     */
    void open (Context context);
    /**
     * reset bundle to {@link ActivityRouteBundleExtras}
     * @param extras bundle data
     * @return IActivityRoute
     */
    T addExtras(Bundle extras);
}
