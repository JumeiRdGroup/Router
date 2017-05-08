package com.lzh.nonview.router.route;

import android.content.Context;
import android.os.Bundle;

/**
 * <p>You can inherit from this class to create action routing event.
 *
 * @author haoge
 */
public abstract class ActionSupport {

    /**
     * The callback method to received routing bundle data.
     * @param context The context who launch the routing event.
     * @param bundle The extras bundle data from {@link IBaseRoute#addExtras(Bundle)} and url parameters.
     */
    public abstract void onRouteTrigger(Context context, Bundle bundle);
}
