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


