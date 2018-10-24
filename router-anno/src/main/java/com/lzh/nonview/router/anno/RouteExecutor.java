package com.lzh.nonview.router.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.Executor;

/**
 * This annotation provided user to indicate which thread the routing event will be called.
 * @author haoge
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface RouteExecutor {
    /**
     * @return subClass of Executor. if not set. it should be MainThreadExecutor.
     */
    Class<? extends Executor> value();
}
