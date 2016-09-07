package com.lzh.nonview.router.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.route.IActivityRoute;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    final String onLine = "http://online.com/";
    final String offline = "http://test.com/";

    String host = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.to_webview,R.id.use_simple_router})
    void onSimpleClick (TextView tv) {
        // 直接使用open进行跳转
        Router.open(tv.getText().toString(),this);
    }

    @OnClick(R.id.addExtras)
    void onAddExtrasClick (View v) {
        // 获取Route对象.并添加额外数据
        IActivityRoute route = (IActivityRoute) Router.getRoute("jumei://main");
        Bundle extras = route.getExtras();// bundle中添加额外参数
        extras.putString("username","haoge");
        extras.putString("password","lzh");
        extras.putString("usertype","VIP");
        route.requestCode(100)// 请求码
                .setAnim(R.anim.anim_fade_in,R.anim.anim_fade_out)// 转场动画设置
                .open(this);
    }

}
