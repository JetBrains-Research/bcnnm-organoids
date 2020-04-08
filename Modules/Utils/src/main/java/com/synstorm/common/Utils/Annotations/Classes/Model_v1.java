package com.synstorm.common.Utils.Annotations.Classes;

import java.lang.annotation.*;

/**
 * Created by human-research on 2019-01-31.
 */


/**
 * A class annotated with Model_v1 claims the actual code of BCNNM Platform.
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
public @interface Model_v1 {
    String value() default "Model_v1";
    Class<? extends Exception> exception() default Exception.class;
}