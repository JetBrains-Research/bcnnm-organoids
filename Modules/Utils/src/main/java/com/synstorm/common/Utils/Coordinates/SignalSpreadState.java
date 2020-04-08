package com.synstorm.common.Utils.Coordinates;

/**
 * Class that handles state of object that can spread signal.
 * Created by vmyrov on 28/11/18.
 */

public class SignalSpreadState {
    private int objectId;
    private int currentRadius;
    private int ligandId;

    public SignalSpreadState(int objectId, int ligandId, int currentRadius) {
        this.objectId = objectId;
        this.ligandId = ligandId;
        this.currentRadius = currentRadius;
    }

    public int getObjectId() {
        return objectId;
    }

    public int getCurrentRadius() {
        return currentRadius;
    }

    public int getLigandId() {
        return ligandId;
    }
}
