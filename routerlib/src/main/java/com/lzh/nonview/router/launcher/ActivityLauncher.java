package com.lzh.nonview.router.launcher;

import android.content.Context;
import android.content.Intent;

import com.lzh.nonview.router.extras.ActivityRouteBundleExtras;
import com.lzh.nonview.router.module.ActivityRouteRule;

public abstract class ActivityLauncher extends Launcher<ActivityRouteRule, ActivityRouteBundleExtras>{

    public abstract Intent createIntent(Context context);
}
