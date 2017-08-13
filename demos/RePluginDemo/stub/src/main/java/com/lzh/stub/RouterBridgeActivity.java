package com.lzh.stub;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.stub.router.PluginCallback;

/**
 * 由于RePlugin无法直接指定加载某插件。所以使用Router跳转时可能由于插件未被加载。导致启动失败。
 *
 * 此类需结合{@link PluginCallback}使用。callback使用统一规则匹配。发现对应的插件并启动。然后插件被启动后在此中转页面做路由恢复
 */
public class RouterBridgeActivity extends Activity {

    @Arg
    Uri uri;
    @Arg
    RouteBundleExtras extras;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parceler.toEntity(this, getIntent());
        // 恢复路由并销毁当前页面。
        Router.resume(uri, extras).open(RouterBridgeActivity.this);
        finish();
    }

}