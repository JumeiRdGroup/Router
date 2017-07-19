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

import java.util.Map;

/**
 * An interface to define route rules to router lib
 * @author haoge
 */
public interface RouteCreator {

    /**
     * create route rules for ActivityRoute
     * @return A map that contains of all Activity route rules.
     */
    Map<String, ActivityRouteRule> createActivityRouteRules();

    /**
     * create route rules for ActionRoute
     * @return A map that contains of all Action route rules.
     */
    Map<String, ActionRouteRule> createActionRouteRules();

}
