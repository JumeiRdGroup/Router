package com.lzh.nonview.router.activityresult;

import android.app.Activity;
import android.content.Intent;

import com.lzh.nonview.router.tools.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ActivityResultDispatcher {

    private Map<Activity, List<RequestArgs>> container = new HashMap<>();

    private static ActivityResultDispatcher dispatcher = new ActivityResultDispatcher();
    private ActivityResultDispatcher(){ }
    public static ActivityResultDispatcher get() {
        return dispatcher;
    }

    public void bindRequestArgs(Activity activity, int requestCode, ActivityResultCallback callback) {
        if (!Utils.isValid(activity)
                || callback == null
                || requestCode == -1) {
            return;
        }

        List<RequestArgs> list = findListByKey(activity);
        list.add(new RequestArgs(requestCode, callback));
    }

    public boolean dispatchActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (!container.containsKey(activity)) {
            return false;
        }

        boolean handle = false;
        List<RequestArgs> list = findListByKey(activity);
        for (RequestArgs args:list) {
            if (args.requestCode == requestCode) {
                args.callback.onResult(resultCode, data);
                list.remove(args);
                handle = true;
                break;
            }
        }

        releaseInvalidItems();
        return handle;
    }

    // 移除无效的条目：比如当前activity在后台时被回收了
    private void releaseInvalidItems() {
        Set<Activity> keys = container.keySet();
        Iterator<Activity> iterator = keys.iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            if (Utils.isValid(next)
                    || container.get(next).isEmpty()) {
                iterator.remove();
            }
        }
    }

    private List<RequestArgs> findListByKey(Activity activity) {
        List<RequestArgs> list = container.get(activity);
        if (list == null) {
            list = new ArrayList<>();
            container.put(activity, list);
        }
        return list;
    }

    private static class RequestArgs {
        int requestCode;
        ActivityResultCallback callback;

        public RequestArgs(int requestCode, ActivityResultCallback callback) {
            this.requestCode = requestCode;
            this.callback = callback;
        }
    }
}