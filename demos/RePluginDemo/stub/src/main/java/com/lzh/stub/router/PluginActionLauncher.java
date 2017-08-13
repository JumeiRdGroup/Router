package com.lzh.stub.router;

import android.content.Context;
import android.os.Bundle;

import com.lzh.compiler.parceler.Parceler;
import com.lzh.nonview.router.launcher.ActionLauncher;
import com.lzh.nonview.router.route.ActionSupport;
import com.lzh.nonview.router.tools.Utils;
import com.qihoo360.replugin.RePlugin;

import java.lang.reflect.Method;

/**
 * 针对RePlugin框架定制的动作路由启动器。
 */
public class PluginActionLauncher extends ActionLauncher {

    @Override
    public void open(Context context) throws Exception {
        ClassLoader loader = getLoader();
        Object action = loader.loadClass(rule.getRuleClz()).newInstance();
        final Bundle data = new Bundle();
        data.putAll(bundle);
        data.putAll(extras.getExtras());
        if (Utils.PARCELER_SUPPORT) {
            Parceler.toEntity(action, data);// inject data
        }
        Class<?> actionSupport = loader.loadClass(ActionSupport.class.getCanonicalName());
        Method method = actionSupport.getMethod("onRouteTrigger", Context.class, Bundle.class);
        method.invoke(action, context, data);
//        getExecutor().execute(new ActionRunnable(action, context, data));
    }

    private ClassLoader getLoader() {
        ClassLoader loader = PluginActionLauncher.class.getClassLoader();
        if (remote.getBoolean("isHost", false)) {
            // 代表此action被定义在host中
            loader = RePlugin.getHostClassLoader();
        } else if (remote.containsKey("alias")) {
            // 代表此action被定义在别的插件中。
            loader = RePlugin.fetchClassLoader(remote.getString("alias"));
        }
        return loader;
    }
}
