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

import android.net.Uri;

import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.module.ActionRouteRule;
import com.lzh.nonview.router.module.ActivityRouteRule;
import com.lzh.nonview.router.module.RouteRule;

/**
 * The route callback to notify the status of routing event.
 * @author haoge
 */
public interface RouteCallback {

    /**
     * There are two types of not found exception:<br>
     *     <i><b>{@link NotFoundException#TYPE_SCHEMA}: </b>This uri can't match the corresponding routing</i><br>
     *     <i><b>{@link NotFoundException#TYPE_CLZ}: </b>The special routing event that matched with uri does not exist.</i>
     * @param uri uri the uri to open
     * @param e {@link NotFoundException}
     */
    void notFound(Uri uri, NotFoundException e);

    /**
     * This method will be invoked when the routing task opened successful
     *
     * @param uri the uri to open
     * @param rule The uri matching rule, it could be {@link ActionRouteRule} or {@link ActivityRouteRule}
     */
    void onOpenSuccess(Uri uri, RouteRule rule);

    /**
     * A callback method to notice that you occurs some exception.<br>
     *     exclude <i>{@link NotFoundException}</i>
     * @param uri the uri to open
     * @param e the exception
     */
    void onOpenFailed(Uri uri, Throwable e);


}
