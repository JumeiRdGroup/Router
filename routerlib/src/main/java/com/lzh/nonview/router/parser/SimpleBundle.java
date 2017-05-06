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
