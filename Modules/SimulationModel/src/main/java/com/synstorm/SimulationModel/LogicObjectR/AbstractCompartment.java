package com.synstorm.SimulationModel.LogicObjectR;

import com.synstorm.DES.AllowedEvent;
import com.synstorm.DES.IEventExecutionResult;
import com.synstorm.DES.IEventParameters;
import com.synstorm.SimulationModel.Annotations.Mechanism;
import com.synstorm.SimulationModel.Model.Model;
import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import com.synstorm.common.Utils.ConfigInterfaces.ICondition;
import com.synstorm.common.Utils.ConfigInterfaces.ILogicObjectDescription;
import com.synstorm.common.Utils.ConfigInterfaces.ISignalingPathway;
import com.synstorm.common.Utils.ConfigInterfaces.LogicObjectTypes;
import com.synstorm.common.Utils.Mechanisms.MechanismParameters.ObjectCreateParameters;
import com.synstorm.common.Utils.Mechanisms.MechanismResponse.CompartmentAddedResponse;
import com.synstorm.common.Utils.Mechanisms.ModelingMechanisms;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Optional;
import java.util.Set;

@Model_v1
public abstract class AbstractCompartment extends LogicObject implements ICompartmentOwner {
    //region Fields
    protected final TIntObjectMap<Compartment> compartments;
    protected final EnumMap<LogicObjectTypes, Compartment> uniqueCompartments;
    protected ICompartmentOwner owner;
    //endregion

    //region Constructors
    public AbstractCompartment(int id, int pId, ILogicObjectDescription logicObjectDescription) {
        super(id, pId, logicObjectDescription);
        compartments = new TIntObjectHashMap<>();
        uniqueCompartments = new EnumMap<>(LogicObjectTypes.class);
    }
    //endregion

    //region Getters and Setters
    @Override
    public Optional<ICompartmentOwner> getOwner() {
        return Optional.ofNullable(owner);
    }
    //endregion

    //region Public Methods
    public void addCompartment(int id, Compartment compartment) {
        compartment.setOwner(this);
        compartments.put(id, compartment);
        final LogicObjectTypes baseType = compartment.description.getBaseType();
        if (compartment.isUnique() && !uniqueCompartments.containsKey(baseType)) 
            uniqueCompartments.put(baseType, compartment);
    }

    @Override
    public int getRootId() {
        final Optional<ICompartmentOwner> ownerOptional = getOwner();
        return ownerOptional.map(ICompartmentOwner::getRootId).orElse(objectId);
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    protected void createMechanismReferences() {
        super.createMechanismReferences();
        mechanismReferences.put(ModelingMechanisms.CheckSignals, this::checkSignals);
        mechanismReferences.put(ModelingMechanisms.CheckSignalsWithDisruptAll, this::checkSignalsWithDisruptAll);
        mechanismReferences.put(ModelingMechanisms.CompartmentForm, this::compartmentForm);
    }
    //endregion

    //region Private Methods
    @NotNull
    @Mechanism
    private IEventExecutionResult compartmentForm(IEventParameters parameters) {
        final ISignalingPathway currentPathway = (ISignalingPathway) parameters;
        final ObjectCreateParameters params = (ObjectCreateParameters) currentPathway.getExecutingParameters();
        return new CompartmentAddedResponse(objectId, params.getDiffType());
    }

    @NotNull
    @Mechanism
    private IEventExecutionResult checkSignals(IEventParameters parameters) {
        final ISignalingPathway currentPathway = (ISignalingPathway) parameters;
        final ICondition diffCondition = currentPathway.getCondition();
        final boolean chkDiff = spaceShell.checkObjectPredicate(objectId, diffCondition);
        if (chkDiff) {
            currentPathway.getExecutingOnConditionTrueSignalingPathways()
                    .forEach(event -> startSignalingPathway(pathwaysDescription.get(event)));

            currentPathway.getDisruptingOnConditionTrueSignalingPathways()
                    .forEach(this::disruptSignalingPathway);
        } else {
            currentPathway.getExecutingOnConditionFalseSignalingPathways()
                    .forEach(event -> startSignalingPathway(pathwaysDescription.get(event)));

            currentPathway.getDisruptingOnConditionFalseSignalingPathways()
                    .forEach(this::disruptSignalingPathway);
        }
        return Model.emptyResponse;
    }

    @NotNull
    @Mechanism
    private IEventExecutionResult checkSignalsWithDisruptAll(IEventParameters parameters) {
        final ISignalingPathway currentPathway = (ISignalingPathway) parameters;
        final ICondition diffCondition = currentPathway.getCondition();
        final boolean chkDiff = spaceShell.checkObjectPredicate(objectId, diffCondition);
        if (chkDiff) {
            final Set<AllowedEvent> executionTrueEvents = currentPathway.getExecutingOnConditionTrueSignalingPathways();
            executionTrueEvents.forEach(event -> startSignalingPathway(pathwaysDescription.get(event)));
            disruptAllSignalingPathwaysExcept(executionTrueEvents);
        } else {
            final Set<AllowedEvent> executionFalseEvents = currentPathway.getExecutingOnConditionFalseSignalingPathways();
            executionFalseEvents.forEach(event -> startSignalingPathway(pathwaysDescription.get(event)));
            disruptAllSignalingPathwaysExcept(executionFalseEvents);
        }
        return Model.emptyResponse;
    }
    //endregion
}
