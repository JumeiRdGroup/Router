package com.lzh.nonview.router.route;

public interface RouteInterceptorAction {

    /**
     * Add a interceptor to container
     * @param interceptor interceptor instance
     */
    void addInterceptor (RouteInterceptor interceptor);

    /**
     * Remove a interceptor from container
     * @param interceptor interceptor instance
     */
    void removeInterceptor (RouteInterceptor interceptor);

    /**
     * remove all of interceptors you has set before
     */
    void removeAllInterceptors ();
}
