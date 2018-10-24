package com.lzh.nonview.router.host;

import android.content.Context;

/**
 * Provide an interface for security verification.
 */
public interface RemoteVerify {
    /**
     * Verify the client you are connecting to.
     * @param context The application context to provide managers.
     * @return returns true if safety
     * @throws Exception error occurs
     */
    boolean verify(Context context) throws Exception;
}
