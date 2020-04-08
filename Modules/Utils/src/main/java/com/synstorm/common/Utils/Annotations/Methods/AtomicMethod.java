package com.synstorm.common.Utils.Annotations.Methods;

import java.lang.annotation.*;

/**
 * Created by human-research on 2019-01-31.
 */


/**
 * Has to be used for pure methods or methods which implement final function.
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD})
public @interface AtomicMethod {
    String value() default "AtomicMethod";
    Class<? extends Exception> exception() default Exception.class;
}