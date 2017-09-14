package com.lzh.nonview.router.demo;

import android.os.Bundle;
import android.util.Size;
import android.widget.TextView;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.nonview.router.anno.ActivityLauncher;
import com.lzh.nonview.router.anno.RouteInterceptors;
import com.lzh.nonview.router.anno.RouterRule;
import com.lzh.nonview.router.demo.base.BaseActivity;
import com.lzh.nonview.router.demo.interceptors.ToastInterceptor;
import com.lzh.nonview.router.launcher.DefaultActivityLauncher;

import java.util.ArrayList;

@RouteInterceptors({ToastInterceptor.class})
@ActivityLauncher(DefaultActivityLauncher.class)
@RouterRule({"haoge.cn/parceler","http://haoge.cn/parceler"})
public class ParcelerActivity extends BaseActivity {

    @Arg
    String username;
    @Arg
    String address;
    @Arg
    Size size;
    @Arg
    ArrayList<String> strList;
    @Arg
    ArrayList<Integer> intList;
    @Arg
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parceler);
        TextView tv = (TextView) findViewById(R.id.parcel_print);
        tv.setText("username:" + username + ",\r\naddress :" + address + ",\r\nstrList" + strList + ",\r\nurl :" + url);
    }
}
