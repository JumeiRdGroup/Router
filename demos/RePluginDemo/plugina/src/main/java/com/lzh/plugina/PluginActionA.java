package com.lzh.plugina;


import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.lzh.nonview.router.anno.RouterRule;
import com.lzh.nonview.router.route.ActionSupport;

@RouterRule("action")
public class PluginActionA extends ActionSupport{

    @Override
    public void onRouteTrigger(Context context, Bundle bundle) {
        Toast.makeText(context, "Plugin Action A invoked!", Toast.LENGTH_SHORT).show();
    }
}
