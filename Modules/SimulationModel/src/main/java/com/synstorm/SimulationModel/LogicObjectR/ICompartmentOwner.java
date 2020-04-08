package com.synstorm.SimulationModel.LogicObjectR;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;

import java.util.Optional;

@Model_v1
public interface ICompartmentOwner {
    int getRootId();
    Optional<ICompartmentOwner> getOwner();
}
