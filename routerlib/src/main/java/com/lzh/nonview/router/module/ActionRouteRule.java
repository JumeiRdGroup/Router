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
package com.lzh.nonview.router.module;

import com.lzh.nonview.router.executors.MainThreadExecutor;
import com.lzh.nonview.router.route.ActionSupport;

import java.util.concurrent.Executor;

public class ActionRouteRule extends RouteRule<ActionRouteRule> {

    private Class<? extends Executor> clz = MainThreadExecutor.class;

    public <T extends ActionSupport> ActionRouteRule(Class<T> clz) {
        super(clz);
    }

    public ActionRouteRule setExecutorClass(Class<? extends Executor> clz) {
        if (clz != null) {
            this.clz = clz;
        }
        return this;
    }

    public Class<? extends Executor> getExecutorClz() {
        return clz;
    }
}
