package com.lzh.nonview.router.protocol;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by haoge on 2017/8/9.
 */

public class HostServiceWrapper {
    private static IClient client;
    private static IService service;

    private static ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            HostServiceWrapper.service = IService.Stub.asInterface(service);
            try {
                HostServiceWrapper.service.add(ClientProxy.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("HostServiceWrapper", "onServiceConnected: failed");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            HostServiceWrapper.service = null;
        }
    };

    /**
     * Set a host package name. it will be used to bind a remote service in host app.
     *
     * @param hostPackage the package name of host.
     * @param context
     */
    public static void startHostService(String hostPackage, Context context) {
        if (service != null) {
            throw new RuntimeException("You've bind a remote service before");
        }
        Intent intent = new Intent();
        intent.setPackage(hostPackage);
        intent.setAction("com.lzh.router.action.HOST");
        context.bindService(intent, connection, Service.BIND_AUTO_CREATE);
    }

    public static boolean canOpenRouter(Uri uri) {
        if (service == null) {
            return false;
        }
        try {
            return service.canOpen(uri);
        } catch (RemoteException e) {
            return false;
        }
    }
}
