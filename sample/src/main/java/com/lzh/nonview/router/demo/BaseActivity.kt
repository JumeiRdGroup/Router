package com.lzh.nonview.router.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import butterknife.ButterKnife
import com.lzh.compiler.parceler.BundleFactory

import com.lzh.compiler.parceler.Parceler
import com.lzh.nonview.router.RouterConfiguration

abstract class BaseActivity : Activity() {

    protected val bundleFactory by lazy { Parceler.createFactory(intent?.extras) }

    // Parceler基础注入配置
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Parceler.toEntity(this, intent)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Parceler.toBundle(this, outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Parceler.toEntity(this, savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        ButterKnife.bind(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        RouterConfiguration.get().dispatchActivityResult(this, requestCode, resultCode, data)
    }
}
