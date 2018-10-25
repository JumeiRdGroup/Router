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
package com.lzh.nonview.router.tools;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.lzh.nonview.router.exception.InterceptorException;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.parser.URIParser;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class Utils {

    /**
     * Adjust if the scheme is http or https
     * @param scheme scheme for uri
     * @return return true if is http or https
     */
    public static boolean isHttp (String scheme) {
        return "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme);
    }

    public static String format(String url) {
        if (url.endsWith("/")){
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

    /**
     * <p>
     * Check and completed the uri when it was build without a 'scheme'.
     * in this case. the default scheme '<b>http</b>' will be added.
     * </p>
     * @param uri source uri
     * @return complete uri
     */
    public static Uri completeUri(Uri uri) {
        if (TextUtils.isEmpty(uri.getScheme())) {
            return Uri.parse("http://" + uri.toString());
        }
        return uri;
    }

    public static void checkInterceptor(Uri uri, RouteBundleExtras extras, Context context, List<RouteInterceptor> interceptors) {
        for (RouteInterceptor interceptor : interceptors) {
            if (interceptor.intercept(uri,extras,context)) {
                extras.putValue(Constants.KEY_RESUME_CONTEXT, context);
                interceptor.onIntercepted(uri,extras,context);
                throw new InterceptorException(interceptor);
            }
        }
    }

    public static Bundle parseToBundle(URIParser parser) {
        Bundle bundle = new Bundle();
        Map<String, String> params = parser.getParams();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            bundle.putString(key, params.get(key));
        }
        return bundle;
    }

    public static boolean isValid(Activity activity) {
        return activity != null
                && !activity.isFinishing()
                && !(Build.VERSION.SDK_INT >= 17 && activity.isDestroyed());
    }
}
