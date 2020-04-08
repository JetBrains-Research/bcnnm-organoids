package com.synstorm.common.Utils.Details;

import com.synstorm.common.Utils.Annotations.Classes.Model_v0;
import com.synstorm.common.Utils.Annotations.Classes.NonProductionLegacy;
import com.synstorm.common.Utils.SpaceUtils.CoordinateUtils;
import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

/**
 * Created by human-research on 21/07/15.
 */

@Model_v0
@NonProductionLegacy

public class GPDetails implements IDetails {
    //region Fields
    private int id;
    private ICoordinate coordinate;
    private int radius;
    private boolean calculateKp;
    private String factorName;
    private String group;
    //endregion

    //region Constructors

    public GPDetails(int id, ICoordinate coordinate, int radius, boolean calculateKp, String factorName, String group) {
        this.id = id;
        this.coordinate = coordinate;
        this.radius = radius;
        this.calculateKp = calculateKp;
        this.factorName = factorName;
        this.group = group;
    }

    //endregion

    //region Getters and Setters
    public int getId() { return id; }

    public ICoordinate getCoordinate() {
        return coordinate;
    }

    public boolean updateCoordinate(int x, int y, int z) {
        ICoordinate coordinateToSet = CoordinateUtils.INSTANCE.createCoordinate(x, y, z);
        if (coordinateToSet == null)
            return false;
        this.coordinate = coordinateToSet;
        return true;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean isCalculateKp() {
        return calculateKp;
    }

    public void setCalculateKp(boolean calculateKp) {
        this.calculateKp = calculateKp;
    }

    public String getFactorName() {
        return factorName;
    }

    public void setFactorName(String factorName) {
        this.factorName = factorName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    //endregion

    //region Public Methods
    //endregion

    //region Private Methods
    //endregion
}
