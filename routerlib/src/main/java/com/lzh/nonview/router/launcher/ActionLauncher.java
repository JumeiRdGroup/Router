package com.lzh.nonview.router.launcher;

import com.lzh.nonview.router.RouteManager;
import com.lzh.nonview.router.extras.ActionRouteBundleExtras;
import com.lzh.nonview.router.module.ActionRouteRule;

import java.util.concurrent.Executor;

public abstract class ActionLauncher extends Launcher<ActionRouteRule, ActionRouteBundleExtras> {

    protected Executor getExecutor() {
        return RouteManager.findOrCreateExecutor(rule.getExecutor());
    }
}
