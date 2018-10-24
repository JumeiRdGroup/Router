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

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.lzh.nonview.router.tools.Utils;

/**
 * A route tool to open uri by browser
 * Created by lzh on 16/9/5.
 */
public class BrowserRoute implements IRoute {

    Uri uri;

    private static final BrowserRoute route = new BrowserRoute();

    public static BrowserRoute getInstance () {
        return route;
    }

    @Override
    public void open(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static boolean canOpenRouter(Uri uri) {
        return Utils.isHttp(uri.getScheme());
    }

    public IRoute setUri(Uri uri) {
        this.uri = uri;
        return this;
    }

}
