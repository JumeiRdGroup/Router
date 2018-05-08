package com.lzh.nonview.router.demo.interceptors

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.lzh.nonview.router.demo.LoginActivity
import com.lzh.nonview.router.demo.manager.DataManager
import com.lzh.nonview.router.extras.RouteBundleExtras
import com.lzh.nonview.router.interceptors.RouteInterceptor

/**
 * 默认拦截器。所有路由(除掉直接以浏览器方式打开的路由)均会触发此拦截器。
 *
 * 可以用作登录开关控制：为登录拦截添加动态登录控制。
 *
 * DATE: 2018/5/8
 * AUTHOR: haoge
 */
class DefaultInterceptor : RouteInterceptor {
    override fun intercept(uri: Uri?, extras: RouteBundleExtras?, context: Context?): Boolean {
        // 当路由启动链接为需要进行登录检查时，且当前未登录，进行拦截
        return checkRequestLogin(uri) && !DataManager.login
    }

    override fun onIntercepted(uri: Uri?, extras: RouteBundleExtras?, context: Context?) {
        // 拦截后，将数据传递到登录页去。待登录完成后进行路由恢复
        val intent: Intent = Intent(context, LoginActivity::class.java)
        intent.putExtra("uri", uri)
        intent.putExtra("extras", extras)
        context?.startActivity(intent)
    }

    // 判断是否为要求进行登录检查的路由链接。
    private fun checkRequestLogin(uri: Uri?):Boolean {
        return uri?.getQueryParameter("requestLogin") == "1"
    }

}