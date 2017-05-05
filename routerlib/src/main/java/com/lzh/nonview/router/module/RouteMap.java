package com.lzh.nonview.router.module;

import android.app.Activity;

import com.lzh.nonview.router.route.ActionSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * A entity to contains some data for route
 * Created by lzh on 16/9/6.
 */
public class RouteMap<R> {
    // type for bundle
    public static final int STRING = -1;
    public static final int BYTE = 0;
    public static final int SHORT = 1;
    public static final int INT = 2;
    public static final int LONG = 3;
    public static final int FLOAT = 4;
    public static final int DOUBLE = 5;
    public static final int BOOLEAN = 6;
    public static final int CHAR = 7;

    public static final int INT_LIST = 8;
    public static final int STRING_LIST = 9;

    /**
     * @param clzName clzName must be a {@link Activity} or {@link ActionSupport} total name
     */
    public RouteMap(String clzName) {
        this.clzName = clzName;
    }

    private String clzName;
    private Map<String,Integer> params = new HashMap<>();

    public String getClzName() {
        return clzName;
    }

    public Map<String,Integer> getParams() {
        return params;
    }

    public R addParam (String key,int type) {
        params.put(key,type);
        //noinspection unchecked
        return (R) this;
    }


}
