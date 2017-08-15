package com.lzh.replugindemo.update;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;

import org.lzh.framework.updatepluginlib.strategy.InstallStrategy;

/**
 * 定制下载成功后安装操作。下载后进行安装并重启plugin intent.
 */
public class RePluginInstall implements InstallStrategy{

    private String pluginName;
    private Context context;
    private Intent intent;

    public RePluginInstall(String pluginName, Context context, Intent intent) {
        this.pluginName = pluginName;
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void install(Context context, String filename) {
        PluginInfo info = RePlugin.install(filename);
        if (!info.getAlias().equals(pluginName)) {
            // 校验是否插件的别名能匹配上。将不正确的卸载掉
            Toast.makeText(context, String.format("install plugin failed: need alias for %s but is %s", pluginName, info.getAlias()), Toast.LENGTH_SHORT).show();
            RePlugin.uninstall(info.getAlias());
        } else {
            RePlugin.startActivity(this.context, intent);
        }
    }
}
