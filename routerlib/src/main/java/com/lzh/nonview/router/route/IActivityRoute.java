package com.lzh.nonview.router.route;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by admin on 16/9/5.
 */
public interface IActivityRoute extends IRoute{

    /**
     * launch activity
     * @param context context
     */
    void open (Context context);

    /**
     * set request code for {@link android.app.Activity#startActivityForResult(Intent, int)}
     * @param requestCode request code
     * @return IActivityRoute
     */
    IActivityRoute requestCode(int requestCode);

    /**
     * The extras bundle data that contains in {@link ActivityRouteBundleExtras}
     * @return bundle
     */
    Bundle getExtras();

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
    IActivityRoute setExtras(Bundle extras);

    /**
     * replace {@link ActivityRouteBundleExtras} to {@link ActivityRoute}
     * @param extras {@link ActivityRouteBundleExtras}
     */
    void replaceBundleExtras(ActivityRouteBundleExtras extras);
}
