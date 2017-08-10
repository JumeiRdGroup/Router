package com.lzh.nonview.router.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation defined some basic configurations
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface RouteConfig {
    /**
     * @deprecated The methods will be removing in the future. consider to use baseUrl() instead.
     */
    @Deprecated
    String schema() default "";

    /**
     * @return a base url to combine with the route you set via {@link RouterRule}.
     */
    String baseUrl() default "";

    /**
     * Defined a basic package value.when you haven't set a package value in {@link RouterRule},this word should be used instead
     * @return a basic package value.
     */
    String pack() default "";
}
