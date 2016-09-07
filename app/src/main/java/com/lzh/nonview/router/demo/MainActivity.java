package com.lzh.nonview.router.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.route.IActivityRoute;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.to_webview,R.id.use_simple_router})
    void onSimpleClick (TextView tv) {
        Router.open(tv.getText().toString(),this);
    }

    @OnClick(R.id.addExtras)
    void onAddExtrasClick (View v) {
        IActivityRoute route = (IActivityRoute) Router.getRoute("jumei://main");
        Bundle extras = route.getExtras();
        extras.putString("username","haoge");
        extras.putString("password","lzh");
        extras.putString("usertype","VIP");
        route.requestCode(100)
                .setAnim(R.anim.anim_fade_in,R.anim.anim_fade_out)
                .open(this);
    }

}
