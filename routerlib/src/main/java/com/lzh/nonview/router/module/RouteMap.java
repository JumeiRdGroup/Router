package com.lzh.nonview.router.module;

import java.util.Map;

/**
 * Created by admin on 16/9/6.
 */
public class RouteMap {

    private String clzName;
    private Map<String,String> params;

    public String getClzName() {
        return clzName;
    }

    public void setClzName(String clzName) {
        this.clzName = clzName;
    }

    public Map<String,String> getParams() {
        return params;
    }

    public void setParams(Map<String,String> params) {
        this.params = params;
    }
}
