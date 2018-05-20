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

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.activityresult.ActivityResultCallback;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.tools.Constants;

import java.util.Random;

/**
 * The base launcher class.
 * @param <T> The route rule
 */
public abstract class Launcher<T extends RouteRule> {
    private static Random sCodeGenerator = new Random();

    protected Uri uri;
    protected Bundle bundle;
    protected RouteBundleExtras extras;
    protected T rule;
    protected Bundle remote;

    protected Activity resumeContext;
    protected ActivityResultCallback resultCallback;

    /**
     * Requires to open with this launcher.
     * @param context context
     * @throws Exception Some error occurs.
     */
    public abstract void open(Context context) throws Exception;

    /**
     * Set all of extras data to used.
     * @param uri The route uri.
     * @param bundle The bundle data that is parsed by uri params.
     * @param extras The extras data you set via {@link Router#getRoute()}
     * @param rule The rule that associate with the uri.
     */
    public final void set(Uri uri, Bundle bundle, RouteBundleExtras extras, T rule, Bundle remote) {
        this.uri = uri;
        this.bundle = bundle;
        this.extras = extras;
        this.rule = rule;
        this.remote = remote;

        resumeContext = extras.getValue(Constants.KEY_RESUME_CONTEXT);
        resultCallback = extras.getValue(Constants.KEY_RESULT_CALLBACK);

        int requestCode = extras.getRequestCode();
        if (resultCallback != null && requestCode == -1) {
            extras.setRequestCode(sCodeGenerator.nextInt(0x0000ffff));
        }
    }

}
