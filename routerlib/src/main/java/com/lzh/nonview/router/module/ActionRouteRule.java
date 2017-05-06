package com.lzh.nonview.router.module;

import com.lzh.nonview.router.route.ActionSupport;

public class ActionRouteRule extends RouteRule<ActionRouteRule> {

    private final ActionSupport target;

    public ActionRouteRule(ActionSupport target) {
        super(target.getClass().getCanonicalName());
        this.target = target;
    }

    public ActionSupport getTarget() {
        return target;
    }
}
