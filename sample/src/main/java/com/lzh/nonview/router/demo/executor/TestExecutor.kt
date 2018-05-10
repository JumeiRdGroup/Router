package com.lzh.nonview.router.demo.executor

import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * @author haoge on 2018/5/10
 */
class TestExecutor : Executor{

    val pool:ExecutorService = Executors.newSingleThreadExecutor { runnable ->
        val thread:Thread = Thread(runnable)
        thread.name = "action_annotation_executor"
        return@newSingleThreadExecutor thread
    }

    override fun execute(command: Runnable?) {
        pool.execute(command)
    }
}