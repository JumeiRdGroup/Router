package com.lzh.nonview.router.demo.interceptors;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.lzh.nonview.router.extras.RouteBundleExtras;
import com.lzh.nonview.router.interceptors.RouteInterceptor;

public class ToastInterceptor implements RouteInterceptor{

    @Override
    public boolean intercept(Uri uri, RouteBundleExtras extras, Context context) {
        Toast.makeText(context, "The LogInterceptor is called", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onIntercepted(Uri uri, RouteBundleExtras extras, Context context) {

    }
}
