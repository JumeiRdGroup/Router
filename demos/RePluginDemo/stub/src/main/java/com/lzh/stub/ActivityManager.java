package com.lzh.stub;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.qihoo360.replugin.RePlugin;

import java.util.LinkedList;

/**
 * 一个简单的Activity模拟保存栈。
 */
public final class ActivityManager implements Application.ActivityLifecycleCallbacks{

    private static ActivityManager manager = new ActivityManager();
    public static ActivityManager get() {
        return manager;
    }

    private LinkedList<Activity> stack = new LinkedList<>();

    // 提供顶层Activity使用。
    public Context top() {
        if (stack.isEmpty()) {
            return RePlugin.getPluginContext();
        }
        return stack.getLast();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (!stack.contains(activity)) {
            stack.add(activity);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (stack.contains(activity)) {
            stack.remove(activity);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }


}
