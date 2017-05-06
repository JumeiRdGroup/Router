package com.lzh.nonview.router.parser;

import android.os.Bundle;
import android.text.TextUtils;

import com.lzh.nonview.router.module.RouteRule;

import java.util.ArrayList;

/**
 * A subclass of {@link BundleWrapper} to package data with List
 * @author lzh
 */
public final class ListBundle extends BundleWrapper {
    private ArrayList datas;

    @SuppressWarnings("Convert2Diamond")
    public ListBundle(int type) {
        super(type);
        switch (type) {
            case RouteRule.INT_LIST:
                datas = new ArrayList<Integer>();
                break;
            case RouteRule.STRING_LIST:
                datas = new ArrayList<String>();
                break;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void set(String data) {
        // filter empty data
        if (TextUtils.isEmpty(data)) return;

        switch (type) {
            case RouteRule.INT_LIST:
                datas.add(Integer.parseInt(data));
                break;
            case RouteRule.STRING_LIST:
                datas.add(data);
                break;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void put(Bundle extras, String key) {
        switch (type) {
            case RouteRule.INT_LIST:
                extras.putIntegerArrayList(key,datas);
                break;
            case RouteRule.STRING_LIST:
                extras.putStringArrayList(key,datas);
        }
    }
}
