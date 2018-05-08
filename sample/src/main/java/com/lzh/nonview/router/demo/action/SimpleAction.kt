package com.lzh.nonview.router.demo.action

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.lzh.nonview.router.anno.RouterRule
import com.lzh.nonview.router.route.ActionSupport

/**
 * DATE: 2018/5/8
 * AUTHOR: haoge
 */
@RouterRule("simple-action")
class SimpleAction : ActionSupport(){
    override fun onRouteTrigger(context: Context?, bundle: Bundle?) {
        Toast.makeText(context, "simple-action被触发", Toast.LENGTH_SHORT).show()
    }
}