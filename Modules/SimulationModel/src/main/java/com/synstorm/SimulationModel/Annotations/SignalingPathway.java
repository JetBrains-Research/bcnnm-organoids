package com.synstorm.SimulationModel.Annotations;

import java.lang.annotation.*;

/**
 * Created by Dmitry.Bozhko on 10/1/2015.
 */
@Inherited
@Target(value= ElementType.METHOD)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface SignalingPathway {
}
