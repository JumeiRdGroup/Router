package com.lzh.nonview.router.interceptors;

import java.util.List;

public interface RouteInterceptorAction<T> {

    /**
     * Add a interceptor to container
     * @param interceptor interceptor instance
     * @return The real type
     */
    T addInterceptor (RouteInterceptor interceptor);

    /**
     * Remove a interceptor from container
     * @param interceptor interceptor instance
     * @return The real type
     */
    T removeInterceptor (RouteInterceptor interceptor);

    /**
     * remove all of interceptors you has set before
     * @return The real type
     */
    T removeAllInterceptors ();

    /**
     * get all interceptors you has set before
     * @return all of interceptors
     */
    List<RouteInterceptor> getInterceptors ();
}


