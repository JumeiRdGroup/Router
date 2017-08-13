package com.lzh.stub.router;

import android.content.Context;
import android.os.Bundle;

import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.protocol.IRemoteFactory;

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
