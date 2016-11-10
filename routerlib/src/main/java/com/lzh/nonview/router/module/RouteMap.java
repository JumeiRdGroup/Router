package com.lzh.nonview.router.module;

import android.app.Activity;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * A entity to contains some data for route
 * Created by lzh on 16/9/6.
 */
public class RouteMap {
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

    public static final int INT_LIST = 17;
    public static final int STRING_LIST = 18;

    /**
     * @param clzName clzName must be a activity total name
     */
    public RouteMap(String clzName) {
        this.clzName = clzName;
    }

    /**
     * @param clz class extends form activity
     * @param <T> class extends form activity
     */
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
