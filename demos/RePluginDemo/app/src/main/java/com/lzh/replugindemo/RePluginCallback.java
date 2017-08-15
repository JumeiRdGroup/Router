package com.lzh.replugindemo;

import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.lzh.replugindemo.update.PluginUpgradeCallback;
import com.lzh.replugindemo.update.RePluginInstall;
import com.qihoo360.replugin.RePluginCallbacks;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateChecker;
import org.lzh.framework.updatepluginlib.model.UpdateParser;
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy;

/**
 * RePlugin提供的回调器。我们在此进行插件升级更新：
 */
public class RePluginCallback extends RePluginCallbacks {

    public RePluginCallback(Context context) {
        super(context);
    }

    @Override
    public boolean onPluginNotExistsForActivity(Context context, String plugin, Intent intent, int process) {
        UpdateConfig config = createPluginUpdateConfig(context, plugin, intent);
        UpdateBuilder.create(config).check();
        return true;
    }

    private UpdateConfig createPluginUpdateConfig(Context context, String plugin, Intent intent) {
        PluginUpgradeCallback callback = new PluginUpgradeCallback(plugin);
        return UpdateConfig.createConfig()
                .checkCB(callback)
                .downloadCB(callback)
                .strategy(new UpdateStrategy() {
                    // 定制显示策略逻辑。
                    @Override
                    public boolean isShowUpdateDialog(Update update) {
                        return true;
                    }

                    @Override
                    public boolean isAutoInstall() {
                        return true;
                    }

                    @Override
                    public boolean isShowDownloadDialog() {
                        return true;
                    }
                })
                .updateChecker(new UpdateChecker() {
                    @Override
                    public boolean check(Update update) throws Exception {
                        return true;
                    }
                })
                .installStrategy(new RePluginInstall(plugin, context, intent))
                .url("https://raw.githubusercontent.com/JumeiRdGroup/Router/master/demos/RePluginDemo/mocked/api/" + plugin + ".json")
                .jsonParser(new UpdateParser() {
                    @Override
                    public Update parse(String httpResponse) throws Exception {
                        return JSON.parseObject(httpResponse, Update.class);
                    }
                });
    }

}
