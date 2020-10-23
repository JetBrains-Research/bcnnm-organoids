# Creating your own configuration

Let's walk through the process of creating a configuration for the BCNNM platform.

The configuration consists of two files: Model.xml and Individual.xml, and their corresponding XSD schemas.

The Model.xml contains simulation parameters such as:

* spatial parameters and system-wide settings
* mechanisms that alter the states of objects
* possible types of objects in the simulation and their relationships

## Cell Division Configuration

Let's create a simple configuration of cell division. 

First we determine the simulation limits:

* Space grid size: 100
* Number of stages in the simulation: 1
* Time limit for simulation stage 1: 100000
* Mode of simulation stage 1: Development
* The initial object of the simulation and its position (one object in spatial coordinates): [50,50,50]

Configuration description in Model.xml:

```xml
<System>
	<Space limit="100"/>
	<Pipeline>
		<Stage id="0" mode="Development" time="100000"/>
	</Pipeline>
</System>
```

Let's define types of simulation objects:
* Number of cell types: 2
* Presence of signaling points in space: no

Let's determine functional properties of cells:
* Cells can perceive an external chemical signal
* Cells can emit a chemical signal into the extracellular space
* Cells can divide according to two scenarios: division without potency decrease (Divide) or division with potency decrease (DivideAndDifferentiate).

Let's define cell types:
* Cell type PluripotentStemCell of the basic type StemCell
* Cell type TotipotentStemCell of the basic type StemCell

Let's define interdependent behavior through mechanisms:
* PluripotentStemCell divides asymmetrically into 1 PluripotentStemCell and 1 TotipotentStemCell (mechanism DivideAndDifferentiate, signaling pathway PluripotentStemCellASymmetricDivision)
* PluripotentStemCell divides symmetrically into 2 PluripotentStemCell's (mechanism Divide, signaling pathway PluripotentStemCellSymmetricDivision)
* To select the division path, let's create the DivisionChoice signaling pathway (SP) belonging to the CheckSignals mechanism. This SP checks the following condition: "TSC signal level is below 0.5 OR above 5". If the condition is met, the PluripotentStemCellASymmetricDivision pathway will be launched in a cascade; if not, the PluripotentStemCellSymmetricDivision pathway will be launched. Also, this SP should be triggered when creating a PluripotentStemCell (initial pathway) and start executing itself unconditionally (default cascade).
* TotiptentStemCell continuously emits TSC signal into the surrounding space with the propagation radius equal to 5 (mechanism SpreadChemicalSignal, SP TSCSpread)

Description of mechanisms and ligands in Model.xml:

```xml
<Mechanisms>
	<Mechanism type="DivideAndDifferentiate">
		<!-->The SP parameterizes the basic mechanism with specific arguments<-->
		<SignalingPathway id="PluripotentStemCellASymmetricDivision" duration="500" initial="false">
			<!-->Set the result of this mechanism: creating an object<-->
			<ExecuteParameter type="ObjectCreateParameters" creationType="TotipotentStemCell"/>
		</SignalingPathway>
	</Mechanism>

	<Mechanism type="Divide">
		<SignalingPathway id="PluripotentStemCellSymmetricDivision" duration="600" initial="false">
			<ExecuteParameter type="ObjectCreateParameters" creationType="PluripotentStemCell"/>
		</SignalingPathway>
	</Mechanism>

	<Mechanism type="CheckSignals">
            <SignalingPathway id="DivisionChoice" duration="1000" initial="true">
                <!-->Define the conditions checked by this SP<-->
                <Condition>
                    <Predicate rule="TSC _lt 0.5 OR TSC _gt 5" args="TSC"/>
                </Condition>
                <Cascades>
                    <!-->Set the behavior depending on whether conditions are met<-->
                    <Cascade pathway="PluripotentStemCellASymmetricDivision" action="Execute" condition="OnConditionTrue"/>
                    <Cascade pathway="PluripotentStemCellSymmetricDivision" action="Execute" condition="OnConditionFalse"/>
                    <!-->Regardless of whether the condition is met, the SP will restart itself<-->
                    <Cascade pathway="DivisionChoice" action="Execute" condition="Default"/>
                </Cascades>
            </SignalingPathway>
        </Mechanism>

	<Mechanism type="SpreadChemicalSignal">
		<SignalingPathway id="TSCSpread" duration="200" initial="true">
			<!-->Set the result of this mechanism: emitting a signal into space<-->
			<ExecuteParameter type="ChemicalSignalParameters" ligand="TSC"/>
			<Cascases>
				<Cascade pathway="TSCSpread" action="Execute" condition="Default"/>
			</Cascases>
		</SignalingPathway>
	</Mechanism>
</Mechanisms>

<Ligands>
	<Ligand id="TSC" radius="5"/>
</Ligands>
```

Let's describe cells and signaling pathways available to them in Model.xml:

```xml
<ModelCells>
	<!-->Cell PluripotentStemCell<-->
	<ModelCell baseType="StemCell" id="PluripotentStemCell">
		<!-->All SPs available to this cell<-->
		<SPs>
			<SP type="DivisionChoice"/>
			<SP type="PluripotentStemCellASymmetricDivision"/>
			<SP type="PluripotentStemCellSymmetricDivision"/>
		</SPs>
	</ModelCell>

	<!-->Cell TotipotentStemCell<-->
	<ModelCell baseType="StemCell" id="TotipotentStemCell">
		<!-->All SPs available to this cell<-->
		<SPs>
			<SP type="TSCSpread"/>
		</SPs>
	</ModelCell>

</ModelCells>
```

Now let's combine the description blocks into the Model.xml file:

```xml
<BCNNM>
    <System>
        <Space limit="100"/>
        <Pipeline>
            <Stage id="0" mode="Development" time="100000"/>
        </Pipeline>
    </System>

    <Mechanisms>
        <Mechanism type="DivideAndDifferentiate">
            <!-->The signaling pathway parameterizes the basic mechanism with specific arguments<-->
            <SignalingPathway id="PluripotentStemCellASymmetricDivision" duration="500" initial="false">
                <!-->Set the result of this mechanism: creating an object<-->
                <ExecuteParameter type="ObjectCreateParameters" creationType="TotipotentStemCell"/>
            </SignalingPathway>
        </Mechanism>

        <Mechanism type="Divide">
            <SignalingPathway id="PluripotentStemCellSymmetricDivision" duration="600" initial="false">
                <ExecuteParameter type="ObjectCreateParameters" creationType="PluripotentStemCell"/>
            </SignalingPathway>
        </Mechanism>

        <Mechanism type="CheckSignals">
            <SignalingPathway id="DivisionChoice" duration="1000" initial="true">
                <!-->Define the conditions checked by this SP<-->
                <Condition>
                    <Predicate rule="TSC _lt 0.5 OR TSC _gt 5" args="TSC"/>
                </Condition>
                <Cascades>
                    <!-->Set the behavior depending on whether conditions are met<-->
                    <Cascade pathway="PluripotentStemCellASymmetricDivision" action="Execute" condition="OnConditionTrue"/>
                    <Cascade pathway="PluripotentStemCellSymmetricDivision" action="Execute" condition="OnConditionFalse"/>
                    <!-->Regardless of whether the condition is met, the SP will restart itself<-->
                    <Cascade pathway="DivisionChoice" action="Execute" condition="Default"/>
                </Cascades>
            </SignalingPathway>
        </Mechanism>

        <Mechanism type="SpreadChemicalSignal">
            <SignalingPathway id="TSCSpread" duration="100" initial="true">
                <!-->Set the result of this mechanism: emitting a signal into space<-->
                <ExecuteParameter type="ChemicalSignalParameters" ligand="TSC"/>
                <Cascades>
                    <Cascade pathway="TSCSpread" action="Execute" condition="Default"/>
                </Cascades>
            </SignalingPathway>
        </Mechanism>
    </Mechanisms>

    <Ligands>
        <Ligand id="TSC" radius="5"/>
    </Ligands>

    <ModelCells>
        <!-->Cell PluripotentStemCell<-->
        <ModelCell baseType="StemCell" id="PluripotentStemCell">
            <!-->All SPs available to this cell<-->
            <SPs>
                <SP type="DivisionChoice"/>
                <SP type="PluripotentStemCellASymmetricDivision"/>
                <SP type="PluripotentStemCellSymmetricDivision"/>
            </SPs>
        </ModelCell>

        <!-->Cell TotipotentStemCell<-->
        <ModelCell baseType="StemCell" id="TotipotentStemCell">
            <!-->All SPs available to this cell<-->
            <SPs>
                <SP type="TSCSpread"/>
            </SPs>
        </ModelCell>

    </ModelCells>
</BCNNM>
```

Now we can set the starting object of the simulation in Individual.xml:

```xml
<Individual id="00000000-0000-0000-0000-000000000000">
    <Objects>
        <!--START CELL-->
        <Object id="0" type="PluripotentStemCell" x="50" y="50" z="50"/>
    </Objects>
</Individual>
```
