package com.lzh.stub.router;

import android.content.Context;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.RouterConfiguration;

public class RouterLoader {

    private static RouterLoader manager = new RouterLoader();
    public static RouterLoader get() {
        return manager;
    }

    public void init (Context context) {
        // 启动远程服务
        RouterConfiguration.get().startHostService("com.lzh.replugindemo", context);

        RouterConfiguration.get().setCallback(new PluginCallback());
        RouterConfiguration.get().setActivityLauncher(PluginActivityLauncher.class);
        RouterConfiguration.get().setActionLauncher(PluginActionLauncher.class);
    }
}
