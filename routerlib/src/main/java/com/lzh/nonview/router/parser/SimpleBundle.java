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
import android.text.TextUtils;

import com.lzh.nonview.router.module.RouteRule;

/**
 * A subclass of {@link BundleWrapper} to package data with baseType
 * @author lzh
 */
public final class SimpleBundle extends BundleWrapper {
    private String data;

    public SimpleBundle(int type) {
        super(type);
    }

    @Override
    public void set(String data) {
        // filter empty data
        if (TextUtils.isEmpty(data)) return;

        this.data = data;
    }

    @Override
    public void put(Bundle extras, String key) {
        if (TextUtils.isEmpty(data)) return;

        switch (type) {
            case RouteRule.BYTE:
                extras.putByte(key,Byte.parseByte(data));
                break;
            case RouteRule.SHORT:
                extras.putShort(key,Short.parseShort(data));
                break;
            case RouteRule.INT:
                extras.putInt(key,Integer.parseInt(data));
                break;
            case RouteRule.LONG:
                extras.putLong(key,Long.parseLong(data));
                break;
            case RouteRule.FLOAT:
                extras.putFloat(key,Float.parseFloat(data));
                break;
            case RouteRule.DOUBLE:
                extras.putDouble(key,Double.parseDouble(data));
                break;
            case RouteRule.CHAR:
                extras.putChar(key,data.charAt(0));
                break;
            case RouteRule.BOOLEAN:
                extras.putBoolean(key,Boolean.parseBoolean(data));
                break;
            case RouteRule.STRING:
            default://string
                extras.putString(key,data);
                break;
        }
    }}
