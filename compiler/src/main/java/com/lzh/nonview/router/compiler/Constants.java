package com.lzh.nonview.router.compiler;

public class Constants {

    public static final String CLASSNAME_APPLICATION = "android.app.Application";
    public static final String CLASSNAME_ACTIVITY = "android.app.Activity";
    public static final String CLASSNAME_ACTION_SUPPORT = "com.lzh.nonview.router.route.ActionSupport";
    public static final String CLASSNAME_ROUTE_MAP = "com.lzh.nonview.router.module.RouteRule";
    public static final String CLASSNAME_ACTIVITY_ROUTE_MAP = "com.lzh.nonview.router.module.ActivityRouteRule";
    public static final String CLASSNAME_ACTION_ROUTE_MAP = "com.lzh.nonview.router.module.ActionRouteRule";
    public static final String CLASSNAME_ROUTE_CREATOR = "com.lzh.nonview.router.module.RouteCreator";
    public static final String CLASSNAME_MAINTHREADEXECUTOR = "com.lzh.nonview.router.executors.MainThreadExecutor";

    public static final String METHODNAME_CREATE_ACTIVITY_ROUTER = "createActivityRouteRules";
    public static final String METHODNAME_CREATE_ACTION_ROUTER = "createActionRouteRules";

}
