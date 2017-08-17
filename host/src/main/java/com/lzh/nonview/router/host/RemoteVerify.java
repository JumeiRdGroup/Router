package com.lzh.nonview.router.host;

import android.app.Service;
import android.os.Binder;

/**
 * Provide an interface for security verification.
 */
public interface RemoteVerify {
    /**
     * Verify the plug-in you are connecting to.
     * @param service The remote service.
     * @param binder The remote binder
     * @return returns true if safety
     */
    boolean verify(Service service, Binder binder);
}
