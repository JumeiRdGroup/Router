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
package com.lzh.nonview.router.protocol;

import android.content.Context;
import android.os.Bundle;

import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.module.RouteRule;

/**
 * <p>
 *     This factory used to create and provide a remote bundle data.
 * </p>
 *
 * <p>When need to register the routing rules to remote bridge service via {@link RouterConfiguration#startHostService(String, Context)},
 * the factory will be called to create a bundle and pass it to remote service from aidl interface.
 */
public interface IRemoteFactory {
    /**
     * Create a extra bundle data so that others process or plugin could compat.
     * @param application The application context.
     * @param rule The routing rule
     * @return new extra bundle or null.
     */
    Bundle createRemote(Context application, RouteRule rule);
}
