package com.lzh.nonview.router.route;

import android.content.Context;

/**
 * The top interface of routing operations. The subclass could be:<br>
 *     {@link BrowserRoute} / {@link IActionRoute} or {@link IActivityRoute}
 */
public interface IRoute {

    /**
     * open route with uri by context
     * @param context The context to launch routing event
     */
    void open(Context context);

    IRoute EMPTY = new IRoute() {
        @Override
        public void open(Context context) {}
    };
}
