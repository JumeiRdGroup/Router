package com.lzh.replugindemo.action;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

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
            Toast.makeText(context, "卸载插件" + plugin + "完成，请重启app生效", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "当前无此插件：" + plugin, Toast.LENGTH_SHORT).show();
        }
    }
}
