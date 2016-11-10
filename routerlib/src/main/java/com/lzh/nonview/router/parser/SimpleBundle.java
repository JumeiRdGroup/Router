package com.lzh.nonview.router.parser;

import android.os.Bundle;
import android.text.TextUtils;

import com.lzh.nonview.router.module.RouteMap;
/**
 * A subclass of {@link BundleWrapper} to package data with baseType
 * @author lzh
 */
public class SimpleBundle extends BundleWrapper {
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
            case RouteMap.BYTE:
                extras.putByte(key,Byte.parseByte(data));
                break;
            case RouteMap.SHORT:
                extras.putShort(key,Short.parseShort(data));
                break;
            case RouteMap.INT:
                extras.putInt(key,Integer.parseInt(data));
                break;
            case RouteMap.LONG:
                extras.putLong(key,Long.parseLong(data));
                break;
            case RouteMap.FLOAT:
                extras.putFloat(key,Float.parseFloat(data));
                break;
            case RouteMap.DOUBLE:
                extras.putDouble(key,Double.parseDouble(data));
                break;
            case RouteMap.CHAR:
                extras.putChar(key,data.charAt(0));
                break;
            case RouteMap.BOOLEAN:
                extras.putBoolean(key,Boolean.parseBoolean(data));
                break;
            case RouteMap.STRING:
            default://string
                extras.putString(key,data);
                break;
        }
    }}
