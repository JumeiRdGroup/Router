package com.lzh.nonview.router.protocol;

import android.net.Uri;
import com.lzh.nonview.router.extras.RouteBundleExtras;

interface IClient {
    boolean canOpen(in Uri uri);
    void open(in Uri uri, in RouteBundleExtras extras);
}