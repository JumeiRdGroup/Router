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
import android.os.Bundle;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.activityresult.ActivityResultCallback;
import com.lzh.nonview.router.launcher.ActivityLauncher;
import com.lzh.nonview.router.launcher.Launcher;
import com.lzh.nonview.router.module.ActivityRouteRule;
import com.lzh.nonview.router.tools.Constants;
import com.lzh.nonview.router.tools.Utils;

/**
 * A route tool to check route rule by uri and launch activity
 * Created by lzh on 16/9/5.
 */
public class ActivityRoute extends BaseRoute<IActivityRoute> implements IActivityRoute {

    @Override
    public Intent createIntent(Context context) {
        ActivityLauncher activityLauncher = (ActivityLauncher) launcher;
        activityLauncher.set(uri, bundle, callback.getExtras(), routeRule, remote);
        return activityLauncher.createIntent(context);
    }

    @Override
    public IActivityRoute requestCode(int requestCode) {
        this.callback.getExtras().setRequestCode(requestCode);
        return this;
    }

    @Override
    public IActivityRoute resultCallback(ActivityResultCallback callback) {
        this.callback.getExtras().putValue(Constants.KEY_RESULT_CALLBACK, callback);
        return this;
    }

    @Override
    public IActivityRoute setOptions(Bundle options) {
        this.callback.getExtras().putValue(Constants.KEY_ACTIVITY_OPTIONS, options);
        return this;
    }

    @Override
    public IActivityRoute setAnim(int enterAnim, int exitAnim) {
        this.callback.getExtras().setInAnimation(enterAnim);
        this.callback.getExtras().setOutAnimation(exitAnim);
        return this;
    }

    @Override
    public IActivityRoute addFlags(int flag) {
        this.callback.getExtras().addFlags(flag);
        return this;
    }

    @Override
    public void open(Fragment fragment) {
        try {
            Utils.checkInterceptor(uri, callback.getExtras(), fragment.getActivity(), getInterceptors());
            ActivityLauncher activityLauncher = (ActivityLauncher) launcher;
            activityLauncher.set(uri, bundle, callback.getExtras(), routeRule, remote);
            activityLauncher.open(fragment);
            callback.onOpenSuccess(routeRule);
        } catch (Throwable e) {
            callback.onOpenFailed(e);
        }

        callback.invoke(fragment.getActivity());
    }

    @Override
    protected Launcher obtainLauncher() throws Exception{
        ActivityRouteRule rule = (ActivityRouteRule) routeRule;
        Class<? extends ActivityLauncher> launcher = rule.getLauncher();
        if (launcher == null) {
            launcher = RouterConfiguration.get().getActivityLauncher();
        }
        return launcher.newInstance();
    }
}
