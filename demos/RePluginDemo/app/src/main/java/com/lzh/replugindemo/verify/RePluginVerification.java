package com.lzh.replugindemo.verify;

import android.content.Context;
import android.os.Binder;
import android.os.Process;

import com.lzh.nonview.router.host.RemoteVerify;

/**
 * Created by haoge on 2017/8/17.
 */

public class RePluginVerification implements RemoteVerify{

    @Override
    public boolean verify(Context context) throws Exception {
        return Process.myUid() == Binder.getCallingUid();
    }
}
