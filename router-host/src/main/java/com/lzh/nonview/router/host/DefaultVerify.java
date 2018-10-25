package com.lzh.nonview.router.host;

import android.content.Context;
import android.os.Binder;
import android.util.Log;

/**
 * <b>Default impl for {@link RemoteVerify}</b>
 *
 * <p>This class to ensure that the remote-client should have the same package name with service.
 */
final class DefaultVerify implements RemoteVerify{

    @Override
    public boolean verify(Context context) {
        String packageName = context.getPackageName();
        int uid = Binder.getCallingUid();
        String[] packages = context.getPackageManager().getPackagesForUid(uid);
        if (packages == null) {
            packages = new String[0];
        }
        for (String pack: packages) {
            if (packageName.equals(pack)) {
                return true;
            }
        }
        Log.e("DefaultVerify", String.format("The client with uid %s connected failed:", uid));
        return false;
    }
}
