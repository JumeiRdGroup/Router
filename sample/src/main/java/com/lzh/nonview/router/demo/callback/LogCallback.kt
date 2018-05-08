package com.lzh.nonview.router.demo.callback

import android.net.Uri
import android.util.Log
import com.lzh.nonview.router.exception.NotFoundException
import com.lzh.nonview.router.launcher.Launcher
import com.lzh.nonview.router.module.RouteRule
import com.lzh.nonview.router.route.RouteCallback

/**
 * DATE: 2018/5/8
 * AUTHOR: haoge
 */
class LogCallback:RouteCallback {
    val TAG = "LogCallback"

    override fun notFound(uri: Uri?, e: NotFoundException?) {
    }

    override fun onOpenSuccess(uri: Uri?, rule: RouteRule<out RouteRule<*, *>, out Launcher<*>>?) {
    }

    override fun onOpenFailed(uri: Uri?, e: Throwable?) {
    }
}