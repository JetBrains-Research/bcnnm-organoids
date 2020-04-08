package com.synstorm.common.Utils.Annotations.Classes;

import java.lang.annotation.*;

/**
 * Created by human-research on 2019-01-31.
 */


/**
 * Annotates legacy code without suitable replacement.
 * That means the class should be refactored or completely rewritten.
 * Use it for planning future work.
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
public @interface ProductionLegacy {
    String value() default "ProductionLegacy";
    Class<? extends Exception> exception() default Exception.class;
}