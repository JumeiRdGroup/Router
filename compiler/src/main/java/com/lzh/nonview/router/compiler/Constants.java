package com.lzh.nonview.router.compiler;

public class Constants {

    public static final String[] CLASSNAME_ACTIVITY = {"android.app","Activity"};
    public static final String[] CLASSNAME_ROUTE_MAP = {"com.lzh.nonview.router.module","RouteMap"};
    public static final String[] CLASSNAME_ROUTE_CREATOR = {"com.lzh.nonview.router.module","RouteCreator"};

    public static final String METHODNAME_CREATE_ROUTER_CREATOR = "createRouteRules";

    public static final String[] CLASSNAME_ARG = {"com.lzh.compiler.parceler.annotation","Arg"};

    public static final String[] FILTER_PREFIX = new String[]{
            "com.android",
            "android",
            "java",
            "javax",
    };
}
