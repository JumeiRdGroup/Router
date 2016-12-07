package com.lzh.nonview.router.route;

import java.util.List;

public interface RouteInterceptorAction<T> {

    /**
     * Add a interceptor to container
     * @param interceptor interceptor instance
     */
    T addInterceptor (RouteInterceptor interceptor);

    /**
     * Remove a interceptor from container
     * @param interceptor interceptor instance
     */
    T removeInterceptor (RouteInterceptor interceptor);

    /**
     * remove all of interceptors you has set before
     */
    T removeAllInterceptors ();

    List<RouteInterceptor> getInterceptors ();
}


