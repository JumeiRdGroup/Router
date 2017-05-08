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
     * Define a basic schema value.when you set route rules in {@link RouterRule} without a special schema.this should be used instead
     * @return a basic schema value
     */
    String schema() default "";

    /**
     * Defined a basic package value.when you haven't set a package value in {@link RouterRule},this word should be used instead
     * @return a basic package value.
     */
    String pack() default "";
}
