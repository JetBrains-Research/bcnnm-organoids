package com.synstorm.common.Utils.Annotations.Classes;

import java.lang.annotation.*;

/**
 * Created by human-research on 2019-01-31.
 */


/**
 * A class annotated with Model_v0 claims the legacy code
 * that should be refactored or cleaned during Platform Refactoring procedures.
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
public @interface Model_v0 {
    String value() default "Model_v0";
    Class<? extends Exception> exception() default Exception.class;
}
