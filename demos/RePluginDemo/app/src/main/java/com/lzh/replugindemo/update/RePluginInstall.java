package com.lzh.replugindemo.update;

import android.content.Context;
import android.content.Intent;

import com.qihoo360.replugin.RePlugin;

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
        RePlugin.install(filename);
        RePlugin.startActivity(this.context, intent);
    }
}
