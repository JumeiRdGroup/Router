/*
 * Copyright (C) 2017 Haoge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzh.nonview.router.route;

import android.os.Bundle;

import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.interceptors.RouteInterceptorAction;

import java.util.List;

/**
 * Base on {@link IRoute} and {@link RouteInterceptorAction}, it subclass could be one of the <br>
 *     {@link IActionRoute} and {@link IActivityRoute}
 * @param <T> The real type
 */
public interface IBaseRoute<T extends IBaseRoute> extends IRoute, RouteInterceptorAction<T>{
    /**
     * add extra bundle data to {@link RouteBundleExtras}
     * @param extras bundle data
     * @return {@link IBaseRoute}
     * @see IActionRoute
     * @see IActivityRoute
     */
    T addExtras(Bundle extras);

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

    @SuppressWarnings("unchecked")
    class EmptyBaseRoute<T extends IBaseRoute> extends EmptyRoute implements IBaseRoute<T> {

        public EmptyBaseRoute(InternalCallback internal) {
            super(internal);
        }

        @Override
        public T addExtras(Bundle extras) {
            internal.getExtras().addExtras(extras);
            return (T) this;
        }

        @Override
        public T addInterceptor(RouteInterceptor interceptor) {
            internal.getExtras().addInterceptor(interceptor);
            return (T) this;
        }

        @Override
        public T removeInterceptor(RouteInterceptor interceptor) {
            internal.getExtras().removeInterceptor(interceptor);
            return (T) this;
        }

        @Override
        public T removeAllInterceptors() {
            internal.getExtras().removeAllInterceptors();
            return (T) this;
        }

        @Override
        public List<RouteInterceptor> getInterceptors() {
            return internal.getExtras().getInterceptors();
        }
    }
}
