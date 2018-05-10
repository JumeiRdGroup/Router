package com.lzh.nonview.router.demo.action

import android.content.Context
import android.os.Bundle
import com.lzh.nonview.router.anno.RouterRule
import com.lzh.nonview.router.demo.tools.SingleCache
import com.lzh.nonview.router.route.ActionSupport

/**
 * @author haoge on 2018/5/10
 */
@RouterRule("say/hello")
class SayHelloAction:ActionSupport() {
    override fun onRouteTrigger(context: Context?, bundle: Bundle?) {
        SingleCache.toast?.show("Hello! this is an action route!")
    }
}