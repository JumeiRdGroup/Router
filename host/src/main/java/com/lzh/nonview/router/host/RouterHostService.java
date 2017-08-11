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
import android.util.Log;

import com.lzh.nonview.router.protocol.IService;
import com.lzh.nonview.router.module.RemoteRule;

import java.util.HashMap;
import java.util.Map;

/**
 * Remote service to store route rules.
 *
 * @author haoge
 */
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
    }
}
