package com.synstorm.common.Utils.Annotations.Classes;

import java.lang.annotation.*;

/**
 * Created by human-research on 2019-01-31.
 */


/**
 * Annotates completely legacy code.
 * That means you understand that new implementation for its function is in production.
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface NonProductionLegacy {
    String value() default "NonProductionLegacy";
    Class<? extends Exception> exception() default Exception.class;
}