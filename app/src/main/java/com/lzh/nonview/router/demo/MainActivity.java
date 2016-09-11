package com.lzh.nonview.router.demo;

import android.content.Intent;
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
        // 直接使用open进行跳转
        Router.create(tv.getText().toString()).open(this);
    }

    @OnClick(R.id.addExtras)
    void onAddExtrasClick (View v) {
        // 获取Route对象.并添加额外数据
        Bundle extras = new Bundle();
        extras.putString("username","haoge");
        extras.putString("password","lzh");
        extras.putString("usertype","VIP");
        Router.create("jumei://main").getActivityRoute()
                .addExtras(extras)// 添加额外参数
                .requestCode(100)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .setAnim(R.anim.anim_fade_in,R.anim.anim_fade_out)
                .open(this);

        Intent intent = Router.create("jumei://main").getActivityRoute().createIntent(this);
    }

}
