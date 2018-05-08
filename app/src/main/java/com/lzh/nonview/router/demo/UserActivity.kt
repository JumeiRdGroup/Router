package com.lzh.nonview.router.demo

import android.os.Bundle
import android.widget.TextView
import com.lzh.compiler.parceler.annotation.Arg
import com.lzh.nonview.router.anno.RouteInterceptors
import com.lzh.nonview.router.anno.RouterRule
import com.lzh.nonview.router.demo.interceptors.LoginInterceptor

/**
 * 用户信息展示页
 * DATE: 2018/5/8
 * AUTHOR: haoge
 */
@RouteInterceptors(LoginInterceptor::class)// 指定所有往此页面跳转的路由，均要进行登录检查
@RouterRule("user-info")
class UserActivity :BaseActivity(){

    // 使用Parceler自动注入intent数据进行使用
    @Arg("username")
    var username = "HaogeStudio"

    val userTv:TextView by lazy { findViewById(R.id.username) as TextView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        userTv.text = "用户名为【$username】"
    }
}