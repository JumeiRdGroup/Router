package com.lzh.nonview.router.demo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.anno.RouterRule;
import com.lzh.nonview.router.route.ActivityRouteBundleExtras;

import butterknife.ButterKnife;
import butterknife.OnClick;

@RouterRule("jumei://jumei.com/")
public class LoginActivity extends Activity {

    @Arg
    int index;
    @Arg
    String username;


    private Uri uri;
    private ActivityRouteBundleExtras extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        uri = getIntent().getParcelableExtra("uri");
        extras = getIntent().getParcelableExtra("extra");
    }

    @OnClick(R.id.login)
    void onLoginClick () {
        DataManager.INSTANCE.setLogin(true);
        Router.create(uri).getActivityRoute().replaceBundleExtras(extras).open(this);
        finish();
    }
}
