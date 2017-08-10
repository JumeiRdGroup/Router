package com.lzh.nonview.router.host;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;

import com.lzh.nonview.router.protocol.IService;
import com.lzh.nonview.router.protocol.RemoteRule;

import java.util.HashMap;
import java.util.Map;

public class RouterHostService extends Service{

    IService.Stub stub = new IService.Stub() {
        Map<String, RemoteRule> activities = new HashMap<>();
        Map<String, RemoteRule> actions = new HashMap<>();

        @Override
        public void addActivityRules(Map rules) throws RemoteException {
            activities.putAll(rules);
        }

        @Override
        public void addActionRules(Map rules) throws RemoteException {
            actions.putAll(rules);
        }

        @Override
        public RemoteRule getActionRule(Uri uri) throws RemoteException {
            return findRule(uri, actions);
        }

        @Override
        public RemoteRule getActivityRule(Uri uri) throws RemoteException {
            return findRule(uri, activities);
        }
    };

    private RemoteRule findRule(Uri uri, Map<String, RemoteRule> rules) {
        String route = uri.getScheme() + "://" + uri.getHost() + uri.getPath();
        for (String key:rules.keySet()) {
            if (format(key).equals(format(route))) {
                return rules.get(key);
            }
        }
        return null;
    }

    private String format(String url) {
        if (url.endsWith("/")){
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
//        return null;
    }
}
