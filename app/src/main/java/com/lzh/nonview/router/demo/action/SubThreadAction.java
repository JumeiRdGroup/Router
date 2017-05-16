package com.lzh.nonview.router.demo.action;

import android.content.Context;
import android.os.Bundle;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.nonview.router.anno.RouteExecutor;
import com.lzh.nonview.router.anno.RouterRule;
import com.lzh.nonview.router.demo.executors.SubThreadExecutor;
import com.lzh.nonview.router.route.ActionRoute;
import com.lzh.nonview.router.route.ActionSupport;

@RouteExecutor(SubThreadExecutor.class)
@RouterRule("haoge://test/sub/thread/")
public class SubThreadAction extends ActionSupport{

    @Arg
    String username;
    @Override
    public void onRouteTrigger(Context context, Bundle bundle) {
        System.out.println("接收到路由；Thread name:" + Thread.currentThread());
    }

    static {
        // 注册此Executor.
        ActionRoute.registerExecutors(SubThreadExecutor.class, new SubThreadExecutor());
    }
}


