package com.lzh.nonview.router.demo.action

import android.content.Context
import android.os.Bundle
import com.haoge.easyandroid.easy.EasyToast
import com.lzh.nonview.router.anno.RouteExecutor
import com.lzh.nonview.router.anno.RouterRule
import com.lzh.nonview.router.demo.executor.TestExecutor
import com.lzh.nonview.router.route.ActionSupport

/**
 * @author haoge on 2018/5/10
 */
@RouteExecutor(TestExecutor::class)
@RouterRule("executor/switcher")
class ExecutorAction : ActionSupport(){
    override fun onRouteTrigger(context: Context?, bundle: Bundle?) {
        EasyToast.DEFAULT.show("线程切换器测试动作路由被启动，启动线程为：${Thread.currentThread().name}" )
    }
}