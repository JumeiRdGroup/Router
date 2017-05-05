package com.lzh.nonview.router.route;

import android.content.Context;
import android.os.Bundle;

import com.lzh.nonview.router.extras.ActivityRouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.interceptors.RouteInterceptorAction;

import java.util.ArrayList;
import java.util.List;

public interface IBaseRoute<T> extends IRoute, RouteInterceptorAction<T>{
    /**
     * reset bundle to {@link ActivityRouteBundleExtras}
     * @param extras bundle data
     * @return IActivityRoute
     */
    T addExtras(Bundle extras);

    IBaseRoute EMPTY = new IBaseRoute<IBaseRoute>() {
        @Override
        public IBaseRoute addExtras(Bundle extras) {
            return this;
        }

        @Override
        public void open(Context context) {

        }

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
