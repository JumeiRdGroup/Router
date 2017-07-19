package com.lzh.nonview.router.demo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.anno.RouterRule;
import com.lzh.nonview.router.demo.base.BaseActivity;
import com.lzh.nonview.router.exception.NotFoundException;
import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;
import com.lzh.nonview.router.module.RouteRule;
import com.lzh.nonview.router.parser.URIParser;
import com.lzh.nonview.router.route.RouteCallback;

import java.io.Serializable;

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
        Router.create("jumei://main")
                .setCallback(new SerializableCallback())
                .getActivityRoute()
//                .addInterceptor(new SerialInterceptor())
//                .addInterceptor(new ParcelableInterceptor())
                .addExtras(extras)// 添加额外参数
                .requestCode(100)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .setAnim(R.anim.anim_fade_in,R.anim.anim_fade_out)
                .open(this);
    }

    static class SerialInterceptor implements RouteInterceptor,Serializable {

        @Override
        public boolean intercept(Uri uri, RouteBundleExtras extras, Context context) {
            return true;
        }

        @Override
        public void onIntercepted(Uri uri, RouteBundleExtras extras, Context context) {
            Log.e("MainActivity","被Serializable的拦截器拦截");
        }
    }

    public static class ParcelableInterceptor implements RouteInterceptor,Parcelable {

        public static final Creator<ParcelableInterceptor> CREATOR = new Creator<ParcelableInterceptor>() {
            @Override
            public ParcelableInterceptor createFromParcel(Parcel source) {
                return new ParcelableInterceptor();
            }

            @Override
            public ParcelableInterceptor[] newArray(int size) {
                return new ParcelableInterceptor[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }

        @Override
        public boolean intercept(Uri uri, RouteBundleExtras extras, Context context) {
            return true;
        }

        @Override
        public void onIntercepted(Uri uri, RouteBundleExtras extras, Context context) {
            Log.e("MainActivity","被Parcelable的拦截器拦截");
        }
    }

    private static class SerializableCallback implements RouteCallback, Serializable {

        @Override
        public void notFound(Uri uri, NotFoundException e) {
            System.out.println("SerializableCallback.notFound");
            System.out.println("uri = [" + uri + "], e = [" + e + "]");
        }

        @Override
        public void onOpenSuccess(Uri uri, RouteRule rule) {
            System.out.println("SerializableCallback.onOpenSuccess");
            System.out.println("uri = [" + uri + "], rule = [" + rule + "]");
        }

        @Override
        public void onOpenFailed(Uri uri, Throwable e) {
            System.out.println("SerializableCallback.onOpenFailed");
            System.out.println("uri = [" + uri + "], e = [" + e + "]");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
