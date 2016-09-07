package com.lzh.nonview.router.demo;

import android.app.Application;

import com.lzh.nonview.router.Router;

/**
 * Created by admin on 16/9/6.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Router.init(this);
    }
}
