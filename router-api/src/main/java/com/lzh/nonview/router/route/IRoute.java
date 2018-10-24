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

/**
 * The top interface of routing operations. The subclass could be:<br>
 *     {@link BrowserRoute} / {@link IActionRoute} or {@link IActivityRoute}
 */
public interface IRoute {

    /**
     * open route with uri by context
     * @param context The context to launch routing event
     */
    void open(Context context);

    class EmptyRoute implements IRoute{
        protected InternalCallback internal;

        public EmptyRoute(InternalCallback internal) {
            this.internal = internal;
        }

        public InternalCallback getInternal() {
            return internal;
        }

        @Override
        public void open(Context context) {
            internal.invoke(context);
        }
    }
}
