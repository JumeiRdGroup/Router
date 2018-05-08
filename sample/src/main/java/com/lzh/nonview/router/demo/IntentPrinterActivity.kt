package com.lzh.nonview.router.demo

import android.os.Bundle
import android.widget.TextView
import com.lzh.nonview.router.anno.RouterRule

/**
 * DATE: 2018/5/8
 * AUTHOR: haoge
 */
@RouterRule("intent/printer")
class IntentPrinterActivity:BaseActivity() {

    val mPrinter:TextView by lazy { findViewById(R.id.printer_tv) as TextView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_printer)

        var message = StringBuilder()
        if (intent == null
                || intent.extras == null
                || intent.extras.isEmpty) {
            message.append("当前页面的Intent中不含有数据")
        } else {
            val keys = intent.extras.keySet()
            message.append("当前页面的intent中含有一下${keys.size}条数据：\n")
            for (s in keys) {
                message.append("key = $s & value = ${intent.extras[s]}")
                message.append("\n")
            }
        }
        mPrinter.text = message;
    }
}