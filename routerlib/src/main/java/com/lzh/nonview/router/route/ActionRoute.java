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

import com.lzh.nonview.router.tools.Cache;
import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.extras.ActionRouteBundleExtras;
import com.lzh.nonview.router.launcher.ActionLauncher;
import com.lzh.nonview.router.launcher.DefaultActionLauncher;
import com.lzh.nonview.router.launcher.Launcher;
import com.lzh.nonview.router.module.ActionRouteRule;

import java.util.concurrent.Executor;

public class ActionRoute extends BaseRoute<IActionRoute, ActionRouteBundleExtras> implements IActionRoute {

    @Override
    protected ActionRouteBundleExtras createExtras() {
        return new ActionRouteBundleExtras();
    }

    @Override
    protected Launcher obtainLauncher() throws Exception{
        ActionRouteRule rule = (ActionRouteRule) routeRule;
        Class<? extends ActionLauncher> launcher = rule.getLauncher();
        if (launcher == null) {
            launcher = DefaultActionLauncher.class;
        }
        return launcher.newInstance();
    }

    /**
     * @deprecated This method will be delete in the future. consider to use {@link Router#registerExecutors(Class, Executor)} instead
     * @see Router#registerExecutors(Class, Executor)
     */
    @SuppressWarnings("unused")
    @Deprecated
    public static void registerExecutors(Class<? extends Executor> key, Executor value) {
        Cache.registerExecutors(key, value);
    }
}
