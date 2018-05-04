package com.lzh.nonview.router.demo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.anno.RouterRule;
import com.lzh.nonview.router.demo.base.BaseActivity;
import com.lzh.nonview.router.demo.pojo.User;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;

import java.net.URLEncoder;

import butterknife.ButterKnife;
import butterknife.OnClick;

@RouterRule("haoge.com/main")
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.to_webview,R.id.use_simple_router,R.id.encode_query, R.id.action_say_hello, R.id.action_subthread})
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

        User user = new User();
        user.username = "haoge";
        user.password = "123456";
        String json = URLEncoder.encode(JSON.toJSONString(user));
        String url = String.format("jumei://main?user=%s", json);

        Router.create(url)
                .addExtras(extras)// 添加额外参数
                .requestCode(0x0000ffff)
                .addInterceptor(new LogInterceptor())
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .setAnim(R.anim.anim_fade_in,R.anim.anim_fade_out)
                .open(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    // 用于测试路由被拦截后再次恢复时，是否正确恢复
    public static class LogInterceptor implements RouteInterceptor {

        @Override
        public boolean intercept(Uri uri, RouteBundleExtras extras, Context context) {
            Log.e("MainActivity", "This is a log interceptor");
            return false;
        }

        @Override
        public void onIntercepted(Uri uri, RouteBundleExtras extras, Context context) {
        }
    }
}
