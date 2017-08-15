package com.lzh.replugindemo;

import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.lzh.replugindemo.update.PluginUpgradeCallback;
import com.qihoo360.replugin.RePluginCallbacks;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateParser;

/**
 * RePlugin提供的回调器。我们在此进行插件升级更新：
 */
public class RePluginCallback extends RePluginCallbacks {

    private UpdateConfig pluginUpdateConfig;// 专用于插件升级的升级配置

    public RePluginCallback(Context context) {
        super(context);
    }

    @Override
    public boolean onPluginNotExistsForActivity(Context context, String plugin, Intent intent, int process) {
        UpdateConfig config = createPluginUpdateConfig(plugin);
        UpdateBuilder.create(config).check();
        return true;
    }

    private UpdateConfig createPluginUpdateConfig(String plugin) {
        PluginUpgradeCallback callback = new PluginUpgradeCallback(plugin);
        return UpdateConfig.createConfig()
                .checkCB(callback)
                .downloadCB(callback)
                .url("https://raw.githubusercontent.com/JumeiRdGroup/Router/master/demos/RePluginDemo/mocked/api/remote.json")
                .jsonParser(new UpdateParser() {
                    @Override
                    public Update parse(String httpResponse) throws Exception {
                        return JSON.parseObject(httpResponse, Update.class);
                    }
                });
    }

}
