package com.lzh.replugindemo.action;

import android.content.Context;
import android.os.Bundle;

import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.nonview.router.anno.RouterRule;
import com.lzh.nonview.router.route.ActionSupport;
import com.qihoo360.replugin.RePlugin;

/**
 * Created by haoge on 2017/8/15.
 */

@RouterRule("uninstall")
public class UninstallAction extends ActionSupport {

    @Arg
    String plugin;

    @Override
    public void onRouteTrigger(Context context, Bundle bundle) {
        Parceler.toEntity(this,bundle);

        if (RePlugin.isPluginInstalled(plugin)) {
            RePlugin.uninstall(plugin);
        }
    }
}
