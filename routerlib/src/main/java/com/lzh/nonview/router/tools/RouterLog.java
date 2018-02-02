package com.lzh.nonview.router.tools;

import android.util.Log;

import com.lzh.nonview.router.Router;

public final class RouterLog {
    private static final String TAG = "RouterLog";

    public static void d(String message) {
        if (Router.DEBUG) {
            Log.d(TAG, message);
        }
    }
    public static void e(String message, Throwable t) {
        if (Router.DEBUG) {
            Log.e(TAG, message, t);
        }
    }
}
