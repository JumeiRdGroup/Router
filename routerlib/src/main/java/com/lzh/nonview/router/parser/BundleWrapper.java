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
package com.lzh.nonview.router.parser;

import android.os.Bundle;

import com.lzh.nonview.router.module.RouteRule;

/**
 * A wrapper class for bundle
 * Created by lzh on 16/11/10.
 */
public abstract class BundleWrapper {
    final int type;

    /**
     * Create a wrapper instance form specially type.
     * @param type The type value defined in {@link RouteRule}
     */
    BundleWrapper(int type) {
        this.type = type;
    }

    /**
     * add a extra data to wrapper class
     * @param data extra data
     */
    public abstract void set(String data);

    /**
     * Put value in extras you had already set before pass by {@link BundleWrapper#set(String)}
     * @param extras The bundle instance to put in
     * @param key The key to put value
     */
    public abstract void put(Bundle extras, String key);
}
