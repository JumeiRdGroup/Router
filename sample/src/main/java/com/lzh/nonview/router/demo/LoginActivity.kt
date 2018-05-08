package com.lzh.nonview.router.demo

import android.net.Uri
import android.os.Bundle
import butterknife.OnClick
import com.lzh.compiler.parceler.annotation.Arg
import com.lzh.nonview.router.Router
import com.lzh.nonview.router.demo.manager.DataManager
import com.lzh.nonview.router.extras.RouteBundleExtras

class LoginActivity:BaseActivity() {

    @Arg("uri")
    var uri:Uri? = null
    @Arg("extras")
    var extras:RouteBundleExtras? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    @OnClick(R.id.login)
    fun login() {
        DataManager.login = true
        // 登录完成后恢复路由启动。
        Router.resume(uri, extras).open(this)
        finish()
    }
}

