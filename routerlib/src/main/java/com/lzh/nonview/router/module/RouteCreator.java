package com.lzh.nonview.router.module;

import java.util.Map;

/**
 * An interface to define route rules to router lib
 * @author haoge
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
