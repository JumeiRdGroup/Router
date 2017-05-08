package com.lzh.nonview.router.module;

import com.lzh.nonview.router.route.ActionSupport;

public class ActionRouteRule extends RouteRule<ActionRouteRule> {

    public <T extends ActionSupport> ActionRouteRule(Class<T> clz) {
        super(clz);
    }
}
