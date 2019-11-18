package ru.conveyor.data;

import ru.conveyor.data.conveyor.Conveyor;
import ru.conveyor.data.conveyor.impl.AbstractConveyor;

import java.util.List;

public class ConveyorStrategy {

    public static Conveyor getConveyorStrategy(ConveyorType conveyorType, int length, List<Integer> intersectionIndices) {
        AbstractConveyor abstractConveyor = conveyorType.newStrategy();

        abstractConveyor.setLength(length);
        abstractConveyor.setIntersectionIndices(intersectionIndices);
        abstractConveyor.init();

        return abstractConveyor;
    }
}
