package com.lzh.nonview.router.demo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.route.ActivityRouteBundleExtras;
import com.lzh.nonview.router.route.IActivityRoute;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
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
        IActivityRoute route = (IActivityRoute) Router.getRoute(uri.toString());
        route.replaceBundleExtras(extras);
        route.open(this);
        finish();
    }
}
