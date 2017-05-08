package com.lzh.nonview.router.module;

import com.lzh.nonview.router.executors.MainThreadExecutor;
import com.lzh.nonview.router.route.ActionSupport;

import java.util.concurrent.Executor;

public class ActionRouteRule extends RouteRule<ActionRouteRule> {

    private Class<? extends Executor> clz = MainThreadExecutor.class;

    public <T extends ActionSupport> ActionRouteRule(Class<T> clz) {
        super(clz);
    }

    public ActionRouteRule setExecutorClass(Class<? extends Executor> clz) {
        if (clz != null) {
            this.clz = clz;
        }
        return this;
    }

    public Class<? extends Executor> getExecutorClz() {
        return clz;
    }
}
