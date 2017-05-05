package com.lzh.nonview.router.module;

import java.util.Map;

/**
 * A interface to define route rules to router lib
 * Created by lzh on 16/9/8.
 */
public interface RouteCreator {

    /**
     * create route rules for ActivityRoute
     * @return A map that contains of all Activity route rules.
     */
    Map<String, ActivityRouteMap> createActivityRouteRules();

    /**
     * create route rules for ActionRoute
     * @return A map that contains of all Action route rules.
     */
    Map<String, ActionRouteMap> createActionRouteRules();

}
