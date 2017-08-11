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
import android.os.Bundle;

import com.lzh.nonview.router.interceptors.RouteInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Base on the {@link IBaseRoute}
 * </p>
 */
public interface IActionRoute extends IBaseRoute<IActionRoute>{

    IActionRoute EMPTY = new IActionRoute() {
        @Override
        public IActionRoute addInterceptor(RouteInterceptor interceptor) {
            return this;
        }

        @Override
        public IActionRoute removeInterceptor(RouteInterceptor interceptor) {
            return this;
        }

        @Override
        public IActionRoute removeAllInterceptors() {
            return this;
        }

        @Override
        public List<RouteInterceptor> getInterceptors() {
            return new ArrayList<>();
        }

        @Override
        public void open(Context context) {}

        @Override
        public IActionRoute addExtras(Bundle extras) {
            return this;
        }
    };

    class EmptyActionRoute extends EmptyBaseRoute<IActionRoute> implements IActionRoute {

        public EmptyActionRoute(InternalCallback internal) {
            super(internal);
        }
    }
}
