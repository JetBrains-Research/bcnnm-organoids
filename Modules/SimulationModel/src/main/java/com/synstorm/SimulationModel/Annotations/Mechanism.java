package com.synstorm.SimulationModel.Annotations;

import java.lang.annotation.*;

@Inherited
@Target(value= ElementType.METHOD)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface Mechanism {
}
