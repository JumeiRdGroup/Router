package com.lzh.nonview.router.parser;


import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.lzh.nonview.router.Utils;
import com.lzh.nonview.router.module.RawParse;
import com.lzh.nonview.router.module.RouteMap;

import org.json.JSONException;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by zhihaol on 16/9/6.
 */
public enum RouterParser {
    INSTANCE;
    Map<String,RouteMap> routeMap = new HashMap<>();
    String readJsonStringFromAssertFile (Context context) {
        StringBuilder json = new StringBuilder();
        AssetManager assets = context.getAssets();
        try {
            InputStream open = assets.open("router.json");
            byte[] buffer = new byte[8 * 1024];
            int length = -1;
            while ((length = open.read(buffer)) != -1) {
                json.append(new String(buffer,0,length));
            }
            return json.toString();
        } catch (Exception e) {
            throw new RuntimeException("read router.json file failed,Please add it to your assets folder",e);
        }
    }

    public Map<String, RouteMap> getRouteMap() {
        return routeMap;
    }

    public void parse(Context context) throws JSONException {
        String json = readJsonStringFromAssertFile(context);
        if (TextUtils.isEmpty(json)) {
            return;
        }
        List<RawParse> rawParses = parseJson(json);
        flatRawList(rawParses);
    }

    void flatRawList(List<RawParse> rawParses) {
        for (int i = 0; i < rawParses.size(); i++) {
            RawParse parse = rawParses.get(i);
            inflateRouteMap(parse);
        }
    }

    void inflateRouteMap(RawParse parse) {
        String pkg = parse.getPkg();
        String scheme = parse.getScheme();
        List<RawParse.RouteBean> routeList = parse.getRoute();
        for (int j = 0; j < routeList.size(); j++) {
            RawParse.RouteBean routeBean = routeList.get(j);
            String route = scheme + "://" + routeBean.getPath();
            RouteMap item = new RouteMap();
            String clzName = Utils.isEmpty(pkg) ? routeBean.getClz():pkg + "." + routeBean.getClz();
            item.setClzName(clzName);
            item.setParams(routeBean.getPms());
            routeMap.put(route,item);
        }
    }

    List<RawParse> parseJson(String json) {
        List<RawParse> parseList;
        if (Utils.isClassSupport("com.alibaba.fastjson.JSON")) {
            parseList = JSON.parseArray(json,RawParse.class);
        } else if (Utils.isClassSupport("com.google.gson.Gson")) {
            RawParse[] rawParses = new Gson().fromJson(json, RawParse[].class);
            parseList = Arrays.asList(rawParses);
        } else {
            throw new IllegalStateException("You should add neither FastJson or Gson to your dependencies list in build.gradle file");
        }
        return parseList;
    }

}
