package com.lzh.nonview.router.executors;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

public class MainThreadExecutor implements Executor{

    private Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable command) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            command.run();
        } else {
            mainHandler.post(command);
        }
    }
}
