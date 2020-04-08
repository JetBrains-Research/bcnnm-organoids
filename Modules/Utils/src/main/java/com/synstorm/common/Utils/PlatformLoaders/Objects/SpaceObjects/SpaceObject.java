package com.synstorm.common.Utils.PlatformLoaders.Objects.SpaceObjects;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;

/**
 * Created by human-research on 2018-11-27.
 */
@Model_v1
public class SpaceObject implements IObjectInstance {
    //region Fields
    private final long id;
    private final String group;
    private final int[] coordinate;
    private final ILogicObjectDescription iLogicObjectDescription;
    private final String axonConnections;
    private final String dendriteConnections;
    //endregion


    //region Constructors

    public SpaceObject(long id, String group, int[] coordinate, ILogicObjectDescription iLogicObjectDescription, String axonConnections, String dendriteConnections) {
        this.id = id;
        this.group = group;
        this.coordinate = coordinate;
        this.iLogicObjectDescription = iLogicObjectDescription;
        this.axonConnections = axonConnections;
        this.dendriteConnections = dendriteConnections;
    }

    //endregion


    //region Getters and Setters

    public long getId() {
        return id;
    }

    public String getGroup() {
        return group;
    }

    public int[] getCoordinate() {
        return coordinate;
    }

    @Override
    public ILogicObjectDescription getILogicObjectDescription() {
        return iLogicObjectDescription;
    }

    public String getAxonConnections() {
        return axonConnections;
    }

    public String getDendriteConnections() {
        return dendriteConnections;
    }

    //endregion


    //region Public Methods

    //endregion


    //region Private Methods

    //endregion

}
