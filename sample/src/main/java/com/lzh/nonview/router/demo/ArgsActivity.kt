package com.lzh.nonview.router.demo

import android.os.Bundle
import android.widget.TextView
import com.lzh.compiler.parceler.annotation.Arg
import com.lzh.nonview.router.anno.RouterRule
import com.lzh.nonview.router.demo.pojo.User

/**
 * 此页面用于介绍如何结合[Parceler](https://github.com/JumeiRdGroup/Parceler)框架的
 * Arg注解进行**自动参数类型解析传递**
 *
 * DATE: 2018/5/8
 *
 * AUTHOR: haoge
 */
@RouterRule("parceler-args")
class ArgsActivity:BaseActivity() {

    // 基本java数据类型
    @Arg
    var mBoolean:Boolean? = null
    @Arg
    var mInt:Int? = null
    @Arg
    var mByte:Byte? = null
    @Arg
    var mShort:Short? = null
    @Arg
    var mChar:Char? = null
    @Arg
    var mFloat:Float? = null
    @Arg
    var mLong:Long? = null
    @Arg
    var mDouble:Double? = null
    // 其他类型
    @Arg
    var mString:String? = null
    // 非可序列化对象
    @Arg
    var mUser: User? = null
    @Arg
    var mUrl:String? = null

    val mPrinter:TextView by lazy { findViewById<TextView>(R.id.printer_tv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_printer)
        val message = StringBuilder()
        message.append("boolean value is $mBoolean\n")
        message.append("int value is $mInt\n")
        message.append("byte value is $mByte\n")
        message.append("short value is $mShort\n")
        message.append("char value is $mChar\n")
        message.append("float value is $mFloat\n")
        message.append("long value is $mLong\n")
        message.append("double value is $mDouble\n")
        message.append("string value is $mString\n")
        message.append("user value is $mUser\n")
        message.append("url value is $mUrl\n")
        mPrinter.text = message
    }
}