# XSD schema explanation

The framework configuration contains XML model files, individuals, and their corresponding XML schema definitions.

## Model XML definition (Model.xsd)

Element levels:

0: BCNNM

1: System, Mechanisms, Ligands, ModelCells, SignalPoints

2: Space, Pipeline, Mechanism, Ligand, ModelCell, SignalPoint

3: Stage, SignalingPathway, Compartments, SPs

4: ExecuteParameter, Cascades, SP, Compartment, Condition

5: Predicate, ExecuteParameter, ConditionParameter, Cascade

String based types:

* StageMode - enumeration [Development, Damage].
* StagePipelineType - enumeration [Trauma].
* CellType - enumeration [StemCell, NeuronCell, GlialCell].
* CompartmentType - enumeration [Axon, AxonalTerminal, DendriteTree, DendriticSpine, Synapse].
* StageTarget - enumeration [Random].
* EventInfluence - enumeration [Execute, Disrupt].
* Distribution - enumeration [Binomial, Pascal, Poisson, Exponential, Gaussian].

### Root level element

The root level element BCNNM is a combination of all elements and is a required element. Contains no attributes.

### First level elements

The first level element System is nested within the root level BCNNM element. Required. Sets the required parameters of the simulation, such as duration, spatial parameters, network training parameters.

The first level element Mechanisms is nested within the BCNNM root level element. Required. Contains a description of the mechanisms through which the state of objects and model space changes.

The first level element Ligands is nested within the BCNNM root level element. Required. Contains a description of all chemical substances in the simulation.

The first level element ModelCells is nested within the BCNNM root level element. Required. Contains a description of all possible cell types in the simulation.

The first level element SignalPoints is nested within the BCNNM root level element. Not required. Contains a description of all possible types of signaling points in the space.

### Second level elements

The second level element Space is nested within the first level element System. Required. Contains a **limit** attribute specifying the size of integer grid.

The second level element Pipeline is nested within the first level element System. Required. Describes the sequence of the simulation stages. For example: development, trauma, development.

The second level element Mechanism is nested within the first level element Mechanisms. Contains a **type** attribute of type string.

The second level element Ligand is nested within the first level element Ligands. Contains the following attributes:

* attribute **id** of type string.
* attribute **radius** of type nonNegativeInteger. Sets basic radius for ligand diffusion. Can be overridden for objects of type SignalPoint.

The second level element ModelCell is nested within the first level element ModelCells. Required if ModelCells are present in the configuration. Contains the following attributes:

* attribute **id** of type string, required. Specifies the cell type, for example "NeuronPrecursor".
* attribute **baseType** of type CellType, required. Specifies the basic cell type.
* attribute **immovable** of type boolean. Specifies the ability to move for cells of a given type.

The second level element SignalPoint is nested within the first level element SignalPoints. Contains an attribute **type** of type string.

### Third level elements

The third level element Stage is nested within the second level element. Contains the following attributes:

* attribute **id** of type long. Specifies the number of stage in the simulation scenario.
* attribute **mode** of type StageMode. Specifies the simulation run mode, for example Development.
* attribute **type** of type StagePipelineType. Specifies the type of injury when Damage simulation mode is selected.
* attribute **ratio** of type double. Specifies the percentage of randomly damaged tissue of a particular kind in Damage simulation mode.
* attribute **cellType** of type string. Specifies the type of damaged tissue in Damage simulation mode.
* attribute **target** of type StageTarget. Specifies the damage target area in Damage simulation mode.
* attribute **pharma** of type string. Specifies the ligand which should damage the target area in Damage simulation mode.
* attribute **time** of type long. Specifies the duration of the simulation stage. Required.
* attribute **scoring** of type double. Proportion of neural tissue to be damaged.
* attribute **customSP** name of a Pathway that cause the damage. Required.

The third level element SignalingPathway is nested within the second level element Mechanism. Contains the following attributes:

* attribute **id** of type string, required. Specifies the unique signaling pathway identifier.
* attribute **duration** of type nonNegativeInteger, required. Specifies the duration of the SP execution in model time ticks.
* attribite **initial** of type boolean, required. Specifies the possibility of SP execution start when the object is created.

The third level element Compartments is nested within the second level element ModelCell or the fourth level element Compartment. Not required. Contains possible compartments of a cell or of a compartment. Constructing nested structures, such as organelles in a cell, is possible with this element.

The third level element SPs is nested within the second level elements ModelCell, Stage and the fourth level element Compartment. Not required. Contains possible signaling pathways associated with a given object type.

#### Fourth level elements

The fourth level element ExecuteParameter is nested within third level element SignalingPathway.

The fourth level element Cascades is nested within the third level element SignalingPathway. Not required. Describes the set of cascade actions in the signaling pathway execution. In such a way, users can specify a signaling cascade.

The fourth level element SP is nested within the third level element SPs. Not required. References a SP associated with the specified cell or compartment.

The fourth level element Compartment is nested within the third level element Compartments. Not required. Contains a description of the object's compartments.


The fourth level element Condition is nested within the third level element SignalingPathway. Not required. Contains a description of conditions for a SP execution.

#### Fifth level elements
The fifth level element Predicate is nested within the fourth level element Condition. Not required. Specifies a condition predicate for a SP. Contains the following attributes:

* attribute rule of type string, not required. Specifies the checked condition itself, for example "ligand X concentration is > 0"
* attribute args of type string, not required. Specifies a ligand for a condition.
* attribute distribution of type Distribution, not required. Specifies a distribution for random variable selection. Should be specified if you wish to implement additional probabilistic nature of a rule ("the rule will be checked with a certain probability").
* attribute distributionVariable of type string, not required. Specifies the ligand for which a probability distribution is created.
* attribute distributionParameters of type string, not required. Specifies distribution parameters: mean, variance.

The fifth level element ConditionParameter is nested within the fourth level element Condition. Not required. Describes additional rules for predicate execution: for which ligand is executed, whether ligand gradient should be taken into account. Contains attributes:
* attribute selectionLigand of type string. Sets the ligand name, for which the predicate condition is specified.
* attribute selectionType of type string. Specifies gradient check: along or against the concentration gradient of a given ligand.

The fifth level element Cascade is nested within the fourth level element Cascades. Not required. Describes cascade actions called by the execution of the parental SP. Contains attributes:
* attribute pathway of type string, required. Specifies the SP which is affected.
* attribute action of type EventInfluence, required. Specifies the action: launch or termination of the SP.
* attribute condition of type string, required. Specifies behavior is three cases: on condition true, on condition false, default. Default means execution regardless of the condition.

## Individual XML definition (Individual.xsd)

Elements levels:

0: Individual
1: Objects, SpaceState
2: Object, State

### Root level element

The root level element Individual unifies all elements and is required. Contains attribute **id** of type string.

### First level elements

The first level element Objects is nested within the root level element Individual, can be singular. Integrates unlimited number of elements of type Object. Does not contain attributes.

The first level element SpaceState is nested within the root level element Individual, can be singular.
Integrates unlimited number of elements of type State. Does not contain attributes.

### Second level elements

The second level element Object  is nested within the first level element Objects. Contains attributes:

* attribute **id** of type nonNegativeInteger, required. Designated for storing the unique object id.
* attribute **group** of type string, not required. Designated for labeling various objects in order to group them in a configuration. Does not affect the modeling process.
* attribiute **type** of type string, required. Has to correspond to one of the object types, set in Model.xml.
* attribute **x** of type int, required. Describes the position of object in space along the X axis.
* attribute **y** of type int, required. Describes the position of object in space along the Y axis.
* attribute **z** of type int, required. Describes the position of object in space along the Z axis.
* attribute **axonConnections** of type string, not required. Contains a list of objects with which axonal (outgoing) connections are established**.**
* attribute **dendriteConnections** of type string, not required. Contains a list of objects with which dendritic (incoming) links are established**.**

The second level element State is nested within the first level element SpaceState. Contains attributes:

* attribute **id** of type nonNegativeInteger, required. Stored the unique object id.
* attribute **ligand** of type nonNegativeInteger, required. Has to correspond to the id of one of the declared ligands in Model.xml
* radius **radius** of type nonNegativeInteger, required. Signal propagation radius can be specified arbitrarily by the user and has to be greater than 0.
