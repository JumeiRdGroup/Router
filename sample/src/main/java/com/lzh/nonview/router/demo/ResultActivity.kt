package com.lzh.nonview.router.demo

import android.content.Intent
import android.os.Bundle
import butterknife.OnClick
import com.lzh.nonview.router.anno.RouterRule

/**
 * 提供resultCode的页面。
 * DATE: 2018/5/8
 * AUTHOR: haoge
 */
@RouterRule("result")
class ResultActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
    }

    @OnClick(R.id.backWithoutIntent)
    fun backWithIntent() {
        finish()
    }

    @OnClick(R.id.backWithIntent)
    fun backWithoutIntent() {
        val intent = Intent()
        intent.putExtra("value", "返回数据")
        setResult(1001, intent)
        finish()
    }
}