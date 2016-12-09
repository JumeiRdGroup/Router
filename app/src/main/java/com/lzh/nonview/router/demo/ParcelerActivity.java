package com.lzh.nonview.router.demo;

import android.os.Bundle;
import android.util.Size;
import android.widget.TextView;

import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.compiler.parceler.annotation.Dispatcher;
import com.lzh.nonview.router.anno.RouterRule;
import com.lzh.nonview.router.demo.base.BaseActivity;
import com.lzh.nonview.router.route.RouteInterceptor;

import java.util.ArrayList;
import java.util.List;

@RouterRule("haoge://haoge.cn/parceler")
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parceler);
        TextView tv = (TextView) findViewById(R.id.parcel_print);
        tv.setText("username:" + username + "\r\n,address :" + address + "\r\n,strList" + strList);
    }
}
