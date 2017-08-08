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

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.lzh.nonview.router.RouteManager;
import com.lzh.nonview.router.Utils;
import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.extras.ActivityRouteBundleExtras;
import com.lzh.nonview.router.launcher.ActivityLauncher;
import com.lzh.nonview.router.launcher.DefaultActivityLauncher;
import com.lzh.nonview.router.launcher.Launcher;
import com.lzh.nonview.router.module.ActivityRouteRule;
import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.parser.URIParser;

/**
 * A route tool to check route rule by uri and launch activity
 * Created by lzh on 16/9/5.
 */
public class ActivityRoute extends BaseRoute<IActivityRoute, ActivityRouteBundleExtras> implements IActivityRoute {

    @Override
    public Intent createIntent(Context context) {
        ActivityLauncher activityLauncher = (ActivityLauncher) launcher;
        activityLauncher.set(uri, bundle, extras, (ActivityRouteRule) routeRule);
        return activityLauncher.createIntent(context);
    }

    @Override
    public IActivityRoute requestCode(int requestCode) {
        this.extras.setRequestCode(requestCode);
        return this;
    }

    @Override
    public IActivityRoute setAnim(int enterAnim, int exitAnim) {
        this.extras.setInAnimation(enterAnim);
        this.extras.setOutAnimation(exitAnim);
        return this;
    }

    @Override
    public IActivityRoute addFlags(int flag) {
        this.extras.addFlags(flag);
        return this;
    }

    @Override
    public void open(Fragment fragment) {
        try {
            Utils.checkInterceptor(uri, extras, fragment.getActivity(), getInterceptors());
            ActivityLauncher activityLauncher = (ActivityLauncher) launcher;
            activityLauncher.set(uri, bundle, extras, (ActivityRouteRule) routeRule);
            activityLauncher.open(fragment);
            callback.onOpenSuccess(uri, routeRule);
        } catch (Throwable e) {
            if (e instanceof NotFoundException) {
                callback.notFound(uri, (NotFoundException) e);
            } else {
                callback.onOpenFailed(this.uri,e);
            }
        }
    }

    @Override
    public void open(android.support.v4.app.Fragment fragment) {
        try {
            Utils.checkInterceptor(uri, extras, fragment.getActivity(), getInterceptors());
            ActivityLauncher activityLauncher = (ActivityLauncher) launcher;
            activityLauncher.set(uri, bundle, extras, (ActivityRouteRule) routeRule);
            activityLauncher.open(fragment);
            callback.onOpenSuccess(uri, routeRule);
        } catch (Throwable e) {
            if (e instanceof NotFoundException) {
                callback.notFound(uri, (NotFoundException) e);
            } else {
                callback.onOpenFailed(this.uri,e);
            }
        }
    }

    @Override
    protected ActivityRouteBundleExtras createExtras() {
        return new ActivityRouteBundleExtras();
    }

    @Override
    protected RouteRule obtainRouteMap() {
        return RouteManager.get().getRouteMapByUri(parser, RouteManager.TYPE_ACTIVITY_ROUTE);
    }

    @Override
    protected Launcher obtainLauncher() throws Exception{
        ActivityRouteRule rule = (ActivityRouteRule) routeRule;
        Class<? extends ActivityLauncher> launcher = rule.getLauncher();
        if (launcher == null) {
            launcher = DefaultActivityLauncher.class;
        }
        return launcher.newInstance();
    }

    public static boolean canOpenRouter(Uri uri) {
        try {
            return RouteManager.get().getRouteMapByUri(new URIParser(uri), RouteManager.TYPE_ACTIVITY_ROUTE) != null;
        } catch (Throwable e) {
            return false;
        }
    }
}
