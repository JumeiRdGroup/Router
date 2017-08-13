package com.lzh.stub.router;

import android.content.Context;
import android.os.Bundle;

import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.protocol.IRemoteFactory;

/**
 * 用于plugin module使用的远程数据工厂。提供一个指定的alias别名。用作判断插件加载状况
 */
public class PluginRemoteFactory implements IRemoteFactory {

    private String alias;

    public PluginRemoteFactory(String alias) {
        this.alias = alias;
    }

    @Override
    public Bundle createRemote(Context application, RouteRule rule) {
        Bundle bundle = new Bundle();
        bundle.putString("alias", alias);
        return bundle;
    }
}
