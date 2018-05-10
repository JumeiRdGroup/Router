package com.lzh.nonview.router.demo.tools

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper


/**
 * 提供一些全局的常用数据进行使用。
 *
 * DATE: 2018/5/9
 *
 * AUTHOR: haoge
 */
@SuppressLint("StaticFieldLeak")
object SingleCache {
    internal var toast:EasyToast? = null
    internal var context:Context? = null
    var mainHandler: Handler = Handler(Looper.getMainLooper())

    fun init(context: Context) {
        if (this.context == null) {
            this.context = context.applicationContext
            toast = EasyToast.DEFAULT
        }
    }
}