package com.lzh.replugindemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.anno.RouterRule;

import butterknife.ButterKnife;
import butterknife.OnClick;

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
