package com.synstorm.common.Utils.SpaceUtils;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.jetbrains.annotations.NotNull;

/**
 * Created by dvbozhko on 12/22/15.
 */
@Model_v1
public interface ICoordinate<T> extends Comparable<ICoordinate> {
    int getX();
    int getY();
    int getZ();
    T getComparative();

    short getPriority(@NotNull ICoordinate coordinate);

    @Override
    int compareTo(@NotNull ICoordinate o);
}
