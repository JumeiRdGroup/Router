package com.lzh.nonview.router.parser;

import android.os.Bundle;

/**
 * A wrapper class for bundle
 * Created by lzh on 16/11/10.
 */
public abstract class BundleWrapper {
    protected int type;

    /**
     * Create a wrapper instance form specially type.
     * @param type The type value defined in {@link com.lzh.nonview.router.module.RouteMap}
     */
    public BundleWrapper(int type) {
        this.type = type;
    }

    /**
     * add a extra data to wrapper class
     * @param data extra data
     */
    public abstract void set(String data);

    /**
     * Put value in extras you had already set before pass by {@link BundleWrapper#set(String)}
     * @param extras The bundle instance to put in
     * @param key The key to put value
     */
    public abstract void put(Bundle extras, String key);
}
