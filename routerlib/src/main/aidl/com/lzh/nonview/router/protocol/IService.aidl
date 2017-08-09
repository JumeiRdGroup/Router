package com.lzh.nonview.router.protocol;

import com.lzh.nonview.router.protocol.IClient;
import android.net.Uri;

interface IService {

    void add(in IClient client);
    void remove(in IClient client);
    boolean canOpen(in Uri uri);
}