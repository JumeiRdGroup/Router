package com.lzh.nonview.router.module;

import com.lzh.nonview.router.route.ActionSupport;

public class ActionRouteMap extends RouteMap<ActionRouteMap>{

    private ActionSupport target;

    public ActionRouteMap(ActionSupport target) {
        super(target.getClass().getCanonicalName());
        this.target = target;
    }

    public ActionSupport getTarget() {
        return target;
    }
}
