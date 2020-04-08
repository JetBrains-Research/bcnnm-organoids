package com.synstorm.common.Utils.FileModelExport;

import com.synstorm.common.Utils.EnumTypes.SimulationEvents;
import com.synstorm.common.Utils.ModelExport.IModelDataExporter;
import com.synstorm.common.Utils.SimulationEvents.ISimulationEvent;
import com.synstorm.common.Utils.SimulationEvents.IndividualEvents.ObjectAddEventR;
import com.synstorm.common.Utils.SimulationEvents.IndividualEvents.ObjectDeleteEventR;
import com.synstorm.common.Utils.SimulationEvents.IndividualEvents.ObjectDifferentiatedEventR;
import com.synstorm.common.Utils.SimulationEvents.IndividualEvents.ObjectMoveEventR;

public class CellMovementExporter extends CsvFileExporter implements IModelDataExporter {
    public CellMovementExporter() { super(".Movements.csv");}

    @Override
    public void writeHeader(String header)
    {
        stringBuilder.append("Tick;Type;CellId;ParentId;Info\n");
    }

    @Override
    public void write(ISimulationEvent event) {
        if (canExport(event)) {
            update("\n");

            SimulationEvents eventType = event.getEventMethod();
            long eventTick = -1;
            String parentId = "-1";
            String cellId = "-1";
            String eventInfo = "";

            switch(eventType) {
                case ObjectMoveEvent:
                    ObjectMoveEventR moveEvent = (ObjectMoveEventR)event;

                    eventTick = moveEvent.getTick();
                    cellId = Integer.toString(moveEvent.getId());

                    eventInfo = coordToString(moveEvent.getCoordinate());
                    break;
                case ObjectAddEvent:
                    ObjectAddEventR addEvent = (ObjectAddEventR)event;
                    int[] coord = addEvent.getCoordinate();

                    eventTick = addEvent.getTick();
                    parentId = Integer.toString(addEvent.getParentId());
                    cellId = Integer.toString(addEvent.getId());

                    eventInfo = coordToString(coord) + "|" + addEvent.getType();
                    break;
                case ObjectDifferentiatedEvent:
                    ObjectDifferentiatedEventR proliferateEvent = (ObjectDifferentiatedEventR)event;

                    eventTick = proliferateEvent.getTick();
                    parentId = Integer.toString(proliferateEvent.getParentId());
                    cellId = Integer.toString(proliferateEvent.getParentId()) + "->" + Integer.toString(proliferateEvent.getId());

                    eventInfo = proliferateEvent.getPreviousType() + "->" + proliferateEvent.getType();
                    break;
                case ObjectDeleteEvent:
                    ObjectDeleteEventR deleteEvent = (ObjectDeleteEventR)event;

                    eventTick = deleteEvent.getTick();
                    cellId = Integer.toString(deleteEvent.getId());
                    break;
//                case AxonGrowEvent:
//                    AxonGrowEvent axgrowEvent = (AxonGrowEvent)event;
//
//                    eventTick = axgrowEvent.getTick();
//                    cellId = axgrowEvent.getId();
//
//                    eventInfo = coordToString(axgrowEvent.getCurrentCoordinate()) + "->" + coordToString(axgrowEvent.getCoordinateToGrow());
//
//                    break;
//                case InitialAxonGrowEvent:
//                    InitialAxonGrowEvent ingrowEvent = (InitialAxonGrowEvent)event;
//
//                    eventTick = 0;
//                    cellId = ingrowEvent.getId();
//
//                    eventInfo = ingrowEvent.getCurrentCoordinate() + "->" + ingrowEvent.getCoordinateToGrow();
//
//                    break;
            }

            stringBuilder
                    .append(eventTick).append(";")
                    .append(eventType.toString()).append(";")
                    .append(cellId).append(";")
                    .append(parentId).append(";")
                    .append(eventInfo).append("\n");
        }
    }
    //endregion

    //region Package-local Methods
    //endregion

    //region Protected Methods
    @Override
    protected void initializeAllowedEvents() {
        addAllowedEvent(SimulationEvents.ObjectMoveEvent);
        addAllowedEvent(SimulationEvents.ObjectAddEvent);
        addAllowedEvent(SimulationEvents.ObjectDeleteEvent);
        addAllowedEvent(SimulationEvents.ObjectDifferentiatedEvent);
        addAllowedEvent(SimulationEvents.AxonGrowEvent);
    }

    private String coordToString(int[] coord) {
            final StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(coord[0]);
            stringBuilder.append(",");
            stringBuilder.append(coord[1]);
            stringBuilder.append(",");
            stringBuilder.append(coord[2]);

            return stringBuilder.toString();
    }
}
