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

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.lzh.nonview.router.launcher.Launcher;
import com.lzh.nonview.router.protocol.IRemoteFactory;
import com.lzh.nonview.router.route.ActionSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * An entity to contains some data for route
 *
 * @author haoge
 */
@SuppressWarnings("unchecked")
public class RouteRule<R extends RouteRule, L extends Launcher> implements Parcelable{
    /** Associate with {@link Bundle#putString(String, String)}*/
    public static final int STRING = -1;
    /** Associate with {@link Bundle#putByte(String, byte)} */
    public static final int BYTE = 0;
    /** Associate with {@link Bundle#putShort(String, short)} */
    public static final int SHORT = 1;
    /** Associate with {@link Bundle#putInt(String, int)} */
    public static final int INT = 2;
    /** Associate with {@link Bundle#putLong(String, long)} */
    public static final int LONG = 3;
    /** Associate with {@link Bundle#putFloat(String, float)} */
    public static final int FLOAT = 4;
    /** Associate with {@link Bundle#putDouble(String, double)} */
    public static final int DOUBLE = 5;
    /** Associate with {@link Bundle#putBoolean(String, boolean)} */
    public static final int BOOLEAN = 6;
    /** Associate with {@link Bundle#putChar(String, char)} */
    public static final int CHAR = 7;

    /** Associate with {@link Bundle#putIntegerArrayList(String, ArrayList)} */
    public static final int INT_LIST = 8;
    /** Associate with {@link Bundle#putStringArrayList(String, ArrayList)} */
    public static final int STRING_LIST = 9;

    public RouteRule(String clzName) {
        this.clzName = clzName;
    }

    /** The class name must be subclass of {@link Activity} or {@link ActionSupport}*/
    private String clzName;
    private Map<String,Integer> params = new HashMap<>();
    private Class<? extends L> launcher;
    private IRemoteFactory factory;

    public String getRuleClz() {
        return clzName;
    }

    public Map<String,Integer> getParams() {
        return params;
    }

    /**
     * Specify required type when parsing the Url parameters
     * @param key the key in Url params.
     * @param type the required type. default is {@link RouteRule#STRING}
     * @return RouteRule
     */
    public R addParam (String key, int type) {
        params.put(key,type);
        return (R) this;
    }

    public IRemoteFactory getFactory() {
        return factory;
    }

    public void setFactory(IRemoteFactory factory) {
        this.factory = factory;
    }

    public R setLauncher(Class<? extends L> launcher) {
        this.launcher = launcher;
        return (R) this;
    }

    public Class<? extends L> getLauncher() {
        return launcher;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(clzName);
        dest.writeMap(params);
    }

    protected RouteRule(Parcel in) {
        clzName = in.readString();
        params = in.readHashMap(getClass().getClassLoader());
    }

    public static final Creator<RouteRule> CREATOR = new Creator<RouteRule>() {
        @Override
        public RouteRule createFromParcel(Parcel in) {
            return new RouteRule(in);
        }

        @Override
        public RouteRule[] newArray(int size) {
            return new RouteRule[size];
        }
    };
}
