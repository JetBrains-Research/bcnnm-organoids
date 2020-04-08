package com.synstorm.common.Utils.SpaceUtils;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;

/**
 * Created by dvbozhko on 10/22/15.
 */
@Model_v1
public class IllegalDimensionalSizeException extends Exception {
    public IllegalDimensionalSizeException() {
        super("Illegal dimension size for model: capacity values should be strictly more than 2");
    }
}
