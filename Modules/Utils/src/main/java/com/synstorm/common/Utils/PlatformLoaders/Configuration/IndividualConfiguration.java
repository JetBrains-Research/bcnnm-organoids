package com.synstorm.common.Utils.PlatformLoaders.Configuration;

import com.synstorm.common.Utils.PlatformLoaders.Objects.SpaceObjects.SpaceObject;
import org.apache.commons.math3.util.Pair;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class IndividualConfiguration {
    //region Fields
    private List<SpaceObject> spaceObjects;
    private final Map<Long, List<Pair<Integer, Integer>>> stateMap;
    //endregion


    //region Constructors

    public IndividualConfiguration() {
        this.spaceObjects = new ArrayList<>();
        this.stateMap = new HashMap<>();
    }


    //endregion


    //region Getters and Setters

    @Contract(pure = true)
    public List<SpaceObject> getSpaceObjects() {
        return spaceObjects;
    }

    @Contract(pure = true)
    public Map<Long, List<Pair<Integer, Integer>>> getStateMap() {
        return stateMap;
    }

    //endregion


    //region Public Methods
    public void addSpaceObject(SpaceObject spaceObject) {
        spaceObjects.add(spaceObject);
    }

    public void addSpaceState(long id, int ligand, int radius) {
        Pair<Integer, Integer> pair = new Pair<>(ligand, radius);
        if (stateMap.containsKey(id))
            stateMap.get(id).add(pair);
        else {
            List<Pair<Integer, Integer>> pairs = new ArrayList<>();
            pairs.add(pair);
            stateMap.put(id, pairs);
        }
    }
    //endregion




    //region Private Methods

    //endregion

}
