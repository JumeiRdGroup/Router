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
package com.lzh.nonview.router.launcher;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.lzh.nonview.router.extras.ActivityRouteBundleExtras;
import com.lzh.nonview.router.module.ActivityRouteRule;

/**
 * The base class of <i><b>Activity Launcher</b></i>
 *
 * <p>
 *     The default impl is {@link DefaultActivityLauncher}
 * </p>
 */
public abstract class ActivityLauncher extends Launcher<ActivityRouteRule, ActivityRouteBundleExtras>{

    /**
     * This method will be invoked when you call {@link com.lzh.nonview.router.route.ActivityRoute#createIntent(Context)}
     *
     * @param context The context instance.
     * @return The new intent that created by the launcher
     */
    public abstract Intent createIntent(Context context);

    /**
     * The launch method for Fragment: {@link Fragment#startActivityForResult(Intent, int)}
     * @param fragment The fragment instance
     * @throws Exception a error occurs
     */
    public abstract void open(Fragment fragment) throws Exception;

    /**
     * The launch method for Support-v4 Fragment: {@link android.support.v4.app.Fragment#startActivityForResult(Intent, int)}
     * @param fragment The fragment instance
     * @throws Exception a error occurs
     */
    public abstract void open(android.support.v4.app.Fragment fragment) throws Exception;
}
