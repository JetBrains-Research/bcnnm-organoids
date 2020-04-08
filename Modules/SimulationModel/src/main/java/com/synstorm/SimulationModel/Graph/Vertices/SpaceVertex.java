package com.synstorm.SimulationModel.Graph.Vertices;

import com.synstorm.common.Utils.SpaceUtils.ICoordinate;

/**
 * Created by dvbozhko on 08/06/16.
 */
public class SpaceVertex extends GraphVertex {
    //region Fields
    private ICoordinate coordinate;
    //endregion

    //region Constructors
    public SpaceVertex(ICoordinate spaceCoordinate) {
        coordinate = spaceCoordinate;
    }
    //endregion

    //region Getters and Setters
    public ICoordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(ICoordinate coordinate) {
        this.coordinate = coordinate;
    }
    //endregion

    public static SpaceVertex assignCoordinate(GraphVertex v, ICoordinate coordinate) {
        SpaceVertex sv = new SpaceVertex(coordinate);
        sv.setId(v.getId());
        return sv;
    }

    //region Public Methods
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    //endregion

    //region Private Methods
    //endregion
}
