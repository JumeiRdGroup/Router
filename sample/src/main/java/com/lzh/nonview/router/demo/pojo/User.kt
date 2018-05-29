package com.lzh.nonview.router.demo.pojo

import android.os.Bundle
import com.lzh.compiler.parceler.Parceler
import com.lzh.compiler.parceler.annotation.Arg
import com.lzh.nonview.router.anno.RouterRule
import com.lzh.nonview.router.route.ICreatorInjector

/**
 * DATE: 2018/5/8
 * AUTHOR: haoge
 */
@RouterRule("creator/user")
class User():ICreatorInjector {
    override fun inject(bundle: Bundle?) {
        Parceler.toEntity(this, bundle)
    }

    @Arg
    var name:String? = null

    constructor(name:String?):this() {
        this.name = name
    }

    override fun toString(): String {
        return "User(name=$name)"
    }

}