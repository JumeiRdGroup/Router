package com.lzh.nonview.router.route;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lzh.nonview.router.extras.ActivityRouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptorAction;

public interface IActivityRoute extends RouteInterceptorAction<IActivityRoute> {

    /**
     * Set a callback to be invoked with route open
     * @param callback callback
     */
    void setCallback (RouteCallback callback);

    /**
     * launch activity
     * @param context context
     */
    void open (Context context);

    /**
     * Create intent by extras data and bundle that parsed by uri
     * @param context The context to create intent
     * @return Intent that contains of extras data and bundle that parsed by uri
     */
    Intent createIntent (Context context);

    /**
     * set request code for {@link android.app.Activity#startActivityForResult(Intent, int)}
     * @param requestCode request code
     * @return IActivityRoute
     */
    IActivityRoute requestCode(int requestCode);

    /**
     * set anim to apply to {@link android.app.Activity#overridePendingTransition(int, int)}
     * @param enterAnim enter animation
     * @param exitAnim exit animation
     * @return IActivityRoute
     */
    IActivityRoute setAnim (int enterAnim, int exitAnim);

    /**
     * reset bundle to {@link ActivityRouteBundleExtras}
     * @param extras bundle data
     * @return IActivityRoute
     */
    IActivityRoute addExtras(Bundle extras);

    /**
     * {@link Intent#addFlags(int)}
     * @param flag flag
     * @return IActivityRoute
     */
    IActivityRoute addFlags(int flag);

    /**
     * replace {@link ActivityRouteBundleExtras} to {@link ActivityRoute}
     * @param extras {@link ActivityRouteBundleExtras}
     * @return IActivityRoute
     */
    IActivityRoute replaceBundleExtras(ActivityRouteBundleExtras extras);
}
