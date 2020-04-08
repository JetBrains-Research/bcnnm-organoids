package com.synstorm.common.Utils.Annotations.Methods;

import java.lang.annotation.*;

/**
 * Created by human-research on 2019-01-31.
 */


/**
 * Has to be used for non-pure methods.
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD})
public @interface NonAtomicMethod {
    String value() default "NonAtomicMethod";
    Class<? extends Exception> exception() default Exception.class;
}