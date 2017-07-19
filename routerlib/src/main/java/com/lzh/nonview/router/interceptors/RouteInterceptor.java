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

import android.content.Context;
import android.net.Uri;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.extras.RouteBundleExtras;

/**
 * An interceptor interface
 * @author haoge
 */
public interface RouteInterceptor{

    /**
     * Whether or not be interrupted when open activity by uri
     * @param uri uri the uri to open
     * @param context context
     * @param extras some extras data for route,
     *               sometimes is null when you not use
     *               {@link Router#getBaseRoute()} to set some extras data into it
     * @return true if should be intercepted
     */
    boolean intercept (Uri uri, RouteBundleExtras extras, Context context);

    /**
     * This method should be invoked when you has been intercepted
     * @param uri uri the uri to open
     * @param context context
     * @param extras some extras data for route,
     *               sometimes is null when you not use
     *               {@link Router#getBaseRoute()} to set some extras data into it
     */
    void onIntercepted(Uri uri, RouteBundleExtras extras, Context context);
}
