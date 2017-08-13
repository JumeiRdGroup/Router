package com.lzh.stub.router;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.lzh.nonview.router.launcher.ActivityLauncher;
import com.qihoo360.replugin.RePlugin;

/**
 * 用于给插件使用的插件启动器:
 */
public class PluginActivityLauncher extends ActivityLauncher {

    @Override
    public Intent createIntent(Context context) {
        String alias = alias();
        Intent intent;
        if (TextUtils.isEmpty(alias)) {
            intent = new Intent();
            intent.setClassName(context, rule.getRuleClz());
        } else {
            // it means plugin
            intent = RePlugin.createIntent(alias, rule.getRuleClz());
        }

        intent.putExtras(bundle);
        intent.putExtras(extras.getExtras());
        intent.addFlags(extras.getFlags());
        return intent;
    }

    @Override
    public void open(Fragment fragment) throws Exception {
        open(fragment.getActivity());
    }

    @Override
    public void open(Context context) throws Exception {
        // 根据是否含有alias判断是否需要使用RePlugin进行跳转
        if (!TextUtils.isEmpty(alias())) {
            // 跳转其他组件。需要使用RePlugin
            RePlugin.startActivity(context, createIntent(context));
            return;
        }

        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(createIntent(context), extras.getRequestCode());
        } else {
            context.startActivity(createIntent(context));
        }
    }

    private String alias() {
        // remote: 由其他组件通过IRemoteFactory接口创建的bundle并通过远程服务传递过来的共享数据。
        // 在此取出进行适配：(存取数据参考PluginRemoteFactory)
        // 若不含有别名。表示此路由匹配的页面。在当前插件中。或者在host中。
        if (remote == null || !remote.containsKey("alias")) {
            return null;
        }
        return remote.getString("alias");
    }
}
