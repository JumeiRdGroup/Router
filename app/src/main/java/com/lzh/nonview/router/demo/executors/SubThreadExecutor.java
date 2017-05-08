package com.lzh.nonview.router.demo.executors;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class SubThreadExecutor implements Executor {

    ExecutorService pool = Executors.newSingleThreadExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "Sub Thread");
        }
    });

    @Override
    public void execute(@NonNull Runnable command) {
        pool.execute(command);
    }
}