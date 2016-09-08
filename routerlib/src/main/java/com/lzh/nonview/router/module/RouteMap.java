package com.lzh.nonview.router.module;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 16/9/6.
 */
public class RouteMap {
    public static final int STRING = -1;
    public static final int BYTE = 0;
    public static final int SHORT = 1;
    public static final int INT = 2;
    public static final int LONG = 3;
    public static final int FLOAT = 4;
    public static final int DOUBLE = 5;
    public static final int BOOLEAN = 6;
    public static final int CHAR = 7;


    public RouteMap(String clzName) {
        this.clzName = clzName;
    }

    public <T extends Activity> RouteMap (Class<T> clz) {
        this.clzName = clz.getCanonicalName();
    }

    private String clzName;
    private Map<String,Integer> params = new HashMap<>();

    public String getClzName() {
        return clzName;
    }

    public Map<String,Integer> getParams() {
        return params;
    }

    public RouteMap addParam (String key,int type) {
        params.put(key,type);
        return this;
    }
}
