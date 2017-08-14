package com.lzh.replugindemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.anno.RouterRule;
import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.RePluginCallbacks;

import butterknife.ButterKnife;
import butterknife.OnClick;

// 因为指定了baseUrl。 所以这里会使用baseUrl做组合。
@RouterRule("main")
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.toPluginA, R.id.toLogin, R.id.triggerActionHost, R.id.triggerActionPluginA, R.id.triggerActionUserCenter})
    void routeClick(Button v) {
        Router.create(v.getText().toString()).open(this);
    }


}
