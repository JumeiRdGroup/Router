package com.lzh.nonview.router.demo.interceptors

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.lzh.nonview.router.demo.LoginActivity
import com.lzh.nonview.router.demo.manager.DataManager
import com.lzh.nonview.router.extras.RouteBundleExtras
import com.lzh.nonview.router.interceptors.RouteInterceptor

/**
 * 登录拦截器。
 */
class LoginInterceptor : RouteInterceptor {

    override fun onIntercepted(uri: Uri?, extras: RouteBundleExtras?, context: Context?) {
        // 拦截后，将数据传递到登录页去。待登录完成后进行路由恢复
        val intent:Intent = Intent(context, LoginActivity::class.java)
        intent.putExtra("uri", uri)
        intent.putExtra("extras", extras)
        context?.startActivity(intent)
    }

    override fun intercept(uri: Uri?, extras: RouteBundleExtras?, context: Context?): Boolean {
        // 判断是否已登录。已登录：不拦截、登录：拦截
        return !DataManager.login
    }

}