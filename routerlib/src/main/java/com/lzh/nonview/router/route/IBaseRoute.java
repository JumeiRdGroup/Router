package com.lzh.nonview.router.route;

import android.content.Context;
import android.os.Bundle;

import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.interceptors.RouteInterceptorAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Base on {@link IRoute} and {@link RouteInterceptorAction}, it subclass could be one of the <br>
 *     {@link IActionRoute} and {@link IActivityRoute}
 * @param <T>
 */
public interface IBaseRoute<T> extends IRoute, RouteInterceptorAction<T>{
    /**
     * add extra bundle data to {@link RouteBundleExtras}
     * @param extras bundle data
     * @return {@link IBaseRoute}
     * @see IActionRoute
     * @see IActivityRoute
     */
    T addExtras(Bundle extras);

    IBaseRoute EMPTY = new IBaseRoute<IBaseRoute>() {
        @Override
        public IBaseRoute addExtras(Bundle extras) {
            return this;
        }

        @Override
        public void open(Context context) {}

        @Override
        public IBaseRoute addInterceptor(RouteInterceptor interceptor) {
            return this;
        }

        @Override
        public IBaseRoute removeInterceptor(RouteInterceptor interceptor) {
            return this;
        }

        @Override
        public IBaseRoute removeAllInterceptors() {
            return this;
        }

        @Override
        public List<RouteInterceptor> getInterceptors() {
            return new ArrayList<>();
        }
    };
}
