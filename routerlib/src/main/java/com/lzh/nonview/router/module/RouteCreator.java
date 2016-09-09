package com.lzh.nonview.router.module;

import java.util.Map;

/**
 * A interface to define route rules to router lib
 * Created by lzh on 16/9/8.
 */
public interface RouteCreator {

    /**
     * create route rules for ActivityRoute
     * @return A map that contains of all route rules.
     */
    Map<String,RouteMap> createRouteRules();
}
