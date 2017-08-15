package com.lzh.replugindemo.update;

import android.util.Log;

import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.model.Update;

import java.io.File;

/**
 * Created by haoge on 2017/8/15.
 */
public class PluginUpgradeCallback implements UpdateCheckCB, UpdateDownloadCB {

    String pluginName;

    public PluginUpgradeCallback(String pluginName) {
        this.pluginName = pluginName;
    }

    @Override
    public void onCheckStart() {
        log("启动更新检查");
    }

    @Override
    public void hasUpdate(Update update) {
        log("检查到有更新");
    }

    @Override
    public void noUpdate() {
        log("检查到无更新");
    }

    @Override
    public void onCheckError(Throwable t) {
        log("检查更新错误：" + t.getMessage());
    }

    @Override
    public void onUserCancel() {
        log("用户取消更新");
    }

    @Override
    public void onCheckIgnore(Update update) {
        log("用户忽略更新");
    }

    @Override
    public void onDownloadStart() {
        log("启动下载任务");
    }

    @Override
    public void onDownloadComplete(File file) {
        log("下载完成");
    }

    @Override
    public void onDownloadProgress(long current, long total) {
        log("下载中：current = " + current + ", and total is " + total);
    }

    @Override
    public void onDownloadError(Throwable t) {
        log("下载失败");
    }

    private void log(String message) {
        Log.e("PluginUpgrade", String.format("plug-in %s status: %s", pluginName, message));
    }
}
