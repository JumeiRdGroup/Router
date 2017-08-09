package com.lzh.nonview.router.host;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;

import com.lzh.nonview.router.protocol.IClient;
import com.lzh.nonview.router.protocol.IService;

import java.util.ArrayList;
import java.util.List;


public class RouterHostService extends Service{

    IService.Stub stub = new IService.Stub() {
        private List<IClient> clients = new ArrayList<>();

        @Override
        public void add(IClient client) throws RemoteException {
            if (!clients.contains(client)) {
                clients.add(client);
            }
        }

        @Override
        public void remove(IClient client) throws RemoteException {
            if (clients.contains(client)) {
                clients.remove(client);
            }
        }

        @Override
        public boolean canOpen(Uri uri) throws RemoteException {
            for (IClient client : clients) {
                try {
                    if (client.canOpen(uri)) {
                        return true;
                    }
                } catch (RemoteException remote) {
                    // ignore
                }
            }
            return false;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
