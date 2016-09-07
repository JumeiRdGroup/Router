package com.lzh.nonview.router.route;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by admin on 16/9/5.
 */
public interface IActivityRoute extends IRoute{

    /**
     * 启动新页面
     * @param context
     */
    void open (Context context);

    IActivityRoute requestCode(int requestCode);

    Bundle getExtras();

    IActivityRoute setAnim (int enterAnim, int exitAnim);

    IActivityRoute setExtras (Bundle extras);
}
