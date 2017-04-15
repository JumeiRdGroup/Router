package com.lzh.nonview.router.exception;

import com.lzh.nonview.router.route.RouteInterceptor;

public class InterceptorException extends RuntimeException {

    private RouteInterceptor interceptor;
    public InterceptorException(RouteInterceptor interceptor) {
        super("This route action has been intercepted");
        this.interceptor = interceptor;
    }

    public RouteInterceptor getInterceptor() {
        return interceptor;
    }
}
