package com.lzh.nonview.router.protocol;

import android.content.Context;
import android.os.Bundle;

import com.lzh.nonview.router.module.RouteRule;

public interface IRemoteFactory {
    Bundle createRemote(Context application, RouteRule rule);
}
