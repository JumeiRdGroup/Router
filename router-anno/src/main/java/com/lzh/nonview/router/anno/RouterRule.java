package com.lzh.nonview.router.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface RouterRule {
    /**
     * value of scheme.should be unique and nonNull
     * @return scheme
     */
    String[] value();
}
