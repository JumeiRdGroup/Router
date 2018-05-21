package com.lzh.nonview.router.demo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import butterknife.OnClick
import com.alibaba.fastjson.JSON
import com.lzh.compiler.parceler.Parceler
import com.lzh.nonview.router.Router
import com.lzh.nonview.router.anno.RouterRule
import com.lzh.nonview.router.demo.interceptors.LoginInterceptor
import com.lzh.nonview.router.demo.pojo.User
import com.lzh.nonview.router.exception.NotFoundException
import com.lzh.nonview.router.launcher.Launcher
import com.lzh.nonview.router.module.RouteRule
import com.lzh.nonview.router.route.RouteCallback
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@RouterRule("main")
class MainActivity : BaseActivity() {

    val pool: ExecutorService = Executors.newSingleThreadExecutor { runnable ->
        val thread:Thread = Thread(runnable)
        thread.name = "action_executor"
        return@newSingleThreadExecutor thread
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // =======http/https自动跳转浏览器========
    @OnClick(R.id.open_browser)
    fun openBrowser() {
        Router.create("https://www.baidu.com").open(this)
    }

    // =======不同拦截器使用方式示例===========
    @OnClick(R.id.toPrinterActivityWithRequestLogin)
    fun toPrinterActivityWithRequestLogin() {
        Router.create("haoge://page/intent/printer?title=动态登录检查&requestLogin=1").open(this)
    }

    @OnClick(R.id.toPrinterActivityWithInterceptor)
    fun toPrinterActivityWithInterceptor() {
        Router.create("haoge://page/intent/printer?title=使用指定拦截器")
                .addInterceptor(LoginInterceptor())// 指定此次跳转使用此拦截器
                .open(this)
    }

    @OnClick(R.id.toPrinterActivityWithoutInterceptor)
    fun toPrinterActivityWithoutInterceptor() {
        Router.create("haoge://page/intent/printer?title=不使用拦截器进行跳转").open(this)
    }

    @OnClick(R.id.toUserActivity)
    fun toUserActivity() {
        Router.create("haoge://page/user-info?username=测试账号").open(this)
    }

    @OnClick(R.id.toPrinterActivityWithExtras)
    fun toPrinterActivityWithExtras() {
        val data = Parceler.createFactory(null)
                .put("用户名", "测试")
                .put("密码", "你猜")
                .bundle

        Router.create("haoge://page/intent/printer")
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)// 添加启动标记位：Intent.addFlag()
                .requestCode(1001)// 指定请求码，使用startActivityForResult跳转
                .addExtras(data)// 添加额外数据。将放入Intent中进行传递:Intent.putExtras(data)
                .addInterceptor(LoginInterceptor())// 添加拦截器,若添加有多个拦截器，将被依次触发
                .setCallback(object:RouteCallback {// 添加路由回调
                    override fun notFound(uri: Uri?, e: NotFoundException?) {
                        Toast.makeText(this@MainActivity, "没匹配到与此uri所匹配的路由目标", Toast.LENGTH_SHORT).show()
                    }

                    override fun onOpenSuccess(uri: Uri?, rule: RouteRule<out RouteRule<*, *>, out Launcher>?) {
                        Toast.makeText(this@MainActivity, "打开路由成功", Toast.LENGTH_SHORT).show()
                    }

                    override fun onOpenFailed(uri: Uri?, e: Throwable?) {
                        Toast.makeText(this@MainActivity, "打开路由失败：${e?.message}", Toast.LENGTH_SHORT).show()
                    }
                })
                .setAnim(R.anim.anim_fade_in, R.anim.anim_fade_out)// 设置转场动画
                .open(this)

    }

    @OnClick(R.id.toResultActivity)
    fun toResultActivity() {
        Router.create("haoge://page/result")
                // 指定返回数据回调
                .resultCallback { resultCode, data -> Toast.makeText(this, "返回码是$resultCode", Toast.LENGTH_SHORT).show() }
                .open(this)
    }

    @OnClick(R.id.toArgsActivity)
    fun toArgsActivity() {
        val url = Uri.parse("haoge://page/parceler-args")
                .buildUpon()
                // 添加基本数据类型
                .appendQueryParameter("mBoolean", "true")
                .appendQueryParameter("mByte", "0")
                .appendQueryParameter("mShort", "1")
                .appendQueryParameter("mChar", "c")
                .appendQueryParameter("mInt", "3")
                .appendQueryParameter("mFloat", "3.14")
                .appendQueryParameter("mDouble", "3.14")
                .appendQueryParameter("mLong", "5")
                .appendQueryParameter("mString", "HaogeStudio")
                // 非可序列化对象可通过json格式传递
                .appendQueryParameter("mUser", JSON.toJSONString(User("HaogeStudio")))
                // 转义字符串。比如参数中需要传递网址时
                // appendQueryParameter本身会将数据先进行转义后再拼接上。所以此处是转义的链接
                .appendQueryParameter("mUrl", "https://www.baidu.com")
                .build()

        Router.create(url).open(this)
    }

    @OnClick(R.id.launchActionRoute)
    fun launchActionRoute() {
        Router.create("haoge://page/say/hello").open(this)
    }

    @OnClick(R.id.launchActionRouteWithExecutorAnnotation)
    fun launchActionRouteWithExecutorAnnotation() {
        Router.create("haoge://page/executor/switcher").open(this)
    }

    @OnClick(R.id.launchActionRouteWithExecutorConfig)
    fun launchActionRouteWithExecutorConfig() {
        Router.create("haoge://page/executor/switcher")
                .setExecutor(pool)
                .open(this)
    }
}
