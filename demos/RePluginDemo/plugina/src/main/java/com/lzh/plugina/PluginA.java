package com.lzh.plugina;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.anno.RouterRule;

import butterknife.ButterKnife;
import butterknife.OnClick;

@RouterRule("main")
public class PluginA extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_a);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.toHost, R.id.toLogin, R.id.triggerActionHost, R.id.triggerActionPluginA, R.id.triggerActionUserCenter})
    void click(Button v) {
        Router.create(v.getText().toString()).open(this);
    }

}
