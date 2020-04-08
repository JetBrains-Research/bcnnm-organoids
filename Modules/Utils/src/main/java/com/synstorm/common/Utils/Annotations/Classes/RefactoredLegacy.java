package com.synstorm.common.Utils.Annotations.Classes;

import java.lang.annotation.*;

/**
 * Created by human-research on 2019-01-31.
 */


/**
 * Annotates good quality legacy code that not needed to be refactored or reimplemented.
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RefactoredLegacy {
    String value() default "RefactoredLegacy";
    Class<? extends Exception> exception() default Exception.class;
}