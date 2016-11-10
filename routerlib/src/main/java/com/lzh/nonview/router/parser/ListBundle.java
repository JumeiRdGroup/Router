package com.lzh.nonview.router.parser;

import android.os.Bundle;
import android.text.TextUtils;

import com.lzh.nonview.router.module.RouteMap;

import java.util.ArrayList;

/**
 * A subclass of {@link BundleWrapper} to package data with List
 * @author lzh
 */
public class ListBundle extends BundleWrapper {
    ArrayList datas;

    public ListBundle(int type) {
        super(type);
        switch (type) {
            case RouteMap.INT_LIST:
                datas = new ArrayList<Integer>();
                break;
            case RouteMap.STRING_LIST:
                datas = new ArrayList<String>();
                break;
        }
    }

    @Override
    public void set(String data) {
        // filter empty data
        if (TextUtils.isEmpty(data)) return;

        switch (type) {
            case RouteMap.INT_LIST:
                datas.add(Integer.parseInt(data));
                break;
            case RouteMap.STRING_LIST:
                datas.add(data);
                break;
        }
    }

    @Override
    public void put(Bundle extras, String key) {
        switch (type) {
            case RouteMap.INT_LIST:
                extras.putIntegerArrayList(key,datas);
                break;
            case RouteMap.STRING_LIST:
                extras.putStringArrayList(key,datas);
        }
    }
}
