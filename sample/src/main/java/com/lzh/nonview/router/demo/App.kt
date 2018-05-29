package com.lzh.nonview.router.demo

import android.app.Application
import com.haoge.easyandroid.EasyAndroid
import com.haoge.studio.RouterRuleCreator
import com.lzh.compiler.parceler.Parceler
import com.lzh.compiler.parceler.annotation.FastJsonConverter
import com.lzh.nonview.router.Router
import com.lzh.nonview.router.RouterConfiguration
import com.lzh.nonview.router.anno.RouteConfig
import com.lzh.nonview.router.demo.interceptors.DefaultInterceptor

@RouteConfig(baseUrl = "haoge://page/", pack = "com.haoge.studio")
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // 注册通过apt生成的路由表
        RouterConfiguration.get().addRouteCreator(RouterRuleCreator())
        // 设置默认路由拦截器：所有路由跳转均会被触发(除了需要直接打开浏览器的链接)
        RouterConfiguration.get().interceptor = DefaultInterceptor()

        // 开启Router日志打印
        Router.DEBUG = true

        // 配置Parceler转换器
        Parceler.setDefaultConverter(FastJsonConverter::class.java)

        EasyAndroid.init(this)
    }
}
