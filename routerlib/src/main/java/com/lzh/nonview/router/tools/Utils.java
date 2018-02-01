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

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.lzh.nonview.router.exception.InterceptorException;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.parser.BundleWrapper;
import com.lzh.nonview.router.parser.SimpleBundle;
import com.lzh.nonview.router.parser.URIParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class Utils {

    /** Flag: indicate that <i><b><a href="https://github.com/yjfnypeu/Parceler">Parceler</a></b></i> is supported.*/
    public static final boolean PARCELER_SUPPORT;

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

    public static boolean checkInterceptor(Uri uri, RouteBundleExtras extras, Context context, List<RouteInterceptor> interceptors) {
        for (RouteInterceptor interceptor : interceptors) {
            if (interceptor.intercept(uri,extras,context)) {
                interceptor.onIntercepted(uri,extras,context);
                throw new InterceptorException(interceptor);
            }
        }
        return false;
    }

    public static Bundle parseRouteMapToBundle(URIParser parser, RouteRule routeRule) {
        Map<String, Integer> keyMap = routeRule.getParams();
        Bundle bundle = new Bundle();
        Map<String, String> params = parser.getParams();
        Set<String> keySet = params.keySet();
        Map<String,BundleWrapper> wrappers = new HashMap<>();
        for (String key : keySet) {
            Integer type = keyMap.get(key);
            type = type == null ? RouteRule.STRING : type;

            BundleWrapper wrapper = wrappers.get(key);
            if (wrapper == null) {
                wrapper = createBundleWrapper(type);
                wrappers.put(key,wrapper);
            }
            wrapper.set(params.get(key));
        }
        keySet = wrappers.keySet();
        for (String key : keySet) {
            wrappers.get(key).put(bundle,key);
        }
        return bundle;
    }

    /**
     * create {@link BundleWrapper} instance by type.
     * <p>
     *     When <i>type</i> between -1 and 7,should create subclass of {@link SimpleBundle} with type<br>
     *     Otherwise,should create of {@link SimpleBundle} with type {@link RouteRule#STRING}
     * </p>
     * @return The type to indicate how tyce should be use to create wrapper instance
     */
    private static BundleWrapper createBundleWrapper (int type) {
        switch (type) {
            case RouteRule.STRING:
            case RouteRule.BYTE:
            case RouteRule.SHORT:
            case RouteRule.INT:
            case RouteRule.LONG:
            case RouteRule.FLOAT:
            case RouteRule.DOUBLE:
            case RouteRule.BOOLEAN:
            case RouteRule.CHAR:
                return new SimpleBundle(type);
            default:
                return new SimpleBundle(RouteRule.STRING);
        }
    }

    static {
        boolean isSupport = true;
        try {
            Class parceler = Class.forName("com.lzh.compiler.parceler.Parceler");
            parceler.getMethod("toEntity", Object.class, Bundle.class);
            isSupport = true;
        } catch (Throwable e) {
            isSupport = false;
        } finally {
            PARCELER_SUPPORT = isSupport;
        }
    }
}
