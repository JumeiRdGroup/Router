package com.lzh.nonview.router.demo.pojo

import com.lzh.compiler.parceler.annotation.Arg
import com.lzh.nonview.router.anno.RouterRule

/**
 * DATE: 2018/5/8
 * AUTHOR: haoge
 */
@RouterRule("creator/user")
class User() {
    @Arg
    var name:String? = null
    constructor(name:String?):this() {
        this.name = name
    }

    override fun toString(): String {
        return "User(name=$name)"
    }

}

//data class User(var name:String?)