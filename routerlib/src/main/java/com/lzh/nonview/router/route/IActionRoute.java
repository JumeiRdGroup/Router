package com.lzh.nonview.router.route;

import android.content.Context;
import android.os.Bundle;

import com.lzh.nonview.router.interceptors.RouteInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Base on the {@link IBaseRoute}
 * </p>
 */
public interface IActionRoute extends IBaseRoute<IActionRoute>{

    IActionRoute EMPTY = new IActionRoute() {
        @Override
        public IActionRoute addInterceptor(RouteInterceptor interceptor) {
            return this;
        }

        @Override
        public IActionRoute removeInterceptor(RouteInterceptor interceptor) {
            return this;
        }

        @Override
        public IActionRoute removeAllInterceptors() {
            return this;
        }

        @Override
        public List<RouteInterceptor> getInterceptors() {
            return new ArrayList<>();
        }

        @Override
        public void open(Context context) {}

        @Override
        public IActionRoute addExtras(Bundle extras) {
            return this;
        }
    };
}
