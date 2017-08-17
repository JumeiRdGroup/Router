/*
 * Copyright (C) 2017 Haoge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzh.nonview.router.host;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.lzh.nonview.router.module.RemoteRule;
import com.lzh.nonview.router.protocol.IService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Remote service to store route rules.
 * @author haoge
 */
public class RouterHostService extends Service{

    private static RemoteVerify verify = new DefaultVerify();

    public static void setVerify(RemoteVerify verify) {
        RouterHostService.verify = verify;
    }

    IService.Stub stub = new IService.Stub() {
        Map<String, RemoteRule> activities = new HashMap<>();
        Map<String, RemoteRule> actions = new HashMap<>();
        List<String> plugins = new ArrayList<>();

        @Override
        public void register(String pluginName) throws RemoteException {
            if (plugins.contains(pluginName)) {
                plugins.add(pluginName);
            }
        }

        @Override
        public boolean isRegister(String pluginName) throws RemoteException {
            return plugins.contains(pluginName);
        }

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

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            try {
                // check for security verification
                if (verify != null && !verify.verify(getApplicationContext())) {
                    return false;
                }
                return super.onTransact(code, data, reply, flags);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
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
    }
}
