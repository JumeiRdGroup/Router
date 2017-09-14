package com.lzh.nonview.router.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation provide to set a serial of RouteInterceptor to used.
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface RouteInterceptors {
    /**
     * Set the subclasses of RouteInterceptor to used, The interceptors will be triggered in sequence.
     * @return The array of RouteInterceptor class.
     */
    Class[] value();
}
