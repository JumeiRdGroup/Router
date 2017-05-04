package com.lzh.nonview.router.demo.action;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.lzh.nonview.router.anno.RouterRule;
import com.lzh.nonview.router.route.ActionSupport;

/**
 * Created by haoge on 2017/5/3.
 */
@RouterRule({"haoge://haoge.cn/hello"})
public class SayHelloAction extends ActionSupport {
    @Override
    public void onEvent(Context context, Bundle bundle) {
        Toast.makeText(context, "Hello world", Toast.LENGTH_SHORT).show();
    }
}
