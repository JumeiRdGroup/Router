package com.lzh.nonview.router.demo.pojo

/**
 * DATE: 2018/5/8
 * AUTHOR: haoge
 */
class User() {
    var name:String? = null
    constructor(name:String?):this() {
        this.name = name
    }

    override fun toString(): String {
        return "User(name=$name)"
    }

}

//data class User(var name:String?)