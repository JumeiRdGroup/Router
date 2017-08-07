package com.lzh.nonview.router.compiler;

public class Constants {

    // Application
    public static final String CLASSNAME_APPLICATION = "android.app.Application";
    // Activity
    public static final String CLASSNAME_ACTIVITY = "android.app.Activity";
    // ActionSupport
    public static final String CLASSNAME_ACTION_SUPPORT = "com.lzh.nonview.router.route.ActionSupport";
    // RouteRule
    public static final String CLASSNAME_ROUTE_MAP = "com.lzh.nonview.router.module.RouteRule";
    // ActivityRouteRule
    public static final String CLASSNAME_ACTIVITY_ROUTE_MAP = "com.lzh.nonview.router.module.ActivityRouteRule";
    // ActionRouteRule
    public static final String CLASSNAME_ACTION_ROUTE_MAP = "com.lzh.nonview.router.module.ActionRouteRule";
    // RouteCreator
    public static final String CLASSNAME_ROUTE_CREATOR = "com.lzh.nonview.router.module.RouteCreator";
    // MainThreadExecutor
    public static final String CLASSNAME_MAINTHREADEXECUTOR = "com.lzh.nonview.router.executors.MainThreadExecutor";
    // DefaultActivityLauncher
    public static final String CLASSNAME_ACTIVITY_LAUNCHER = "com.lzh.nonview.router.launcher.ActivityLauncher";
    // DefaultActionLauncher
    public static final String CLASSNAME_ACTION_LAUNCHER = "com.lzh.nonview.router.launcher.ActionLauncher";

    // RouteCreator.createActivityRouteRule
    public static final String METHODNAME_CREATE_ACTIVITY_ROUTER = "createActivityRouteRules";
    // RouteCreator.createActionRouteRule
    public static final String METHODNAME_CREATE_ACTION_ROUTER = "createActionRouteRules";

}
