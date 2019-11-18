package ru.conveyor.api.dto;

import ru.conveyor.config.FactoryConfig;
import ru.conveyor.data.IntersectionPoint;

import java.util.List;

public class FactoryStatusDto {

    private List<IntersectionPoint> intersectionPoints;

    private List<Integer> conveyorA;
    private List<Integer> conveyorB;

    public FactoryStatusDto() {
    }

    public FactoryStatusDto(FactoryConfig config, List<Integer> statusA, List<Integer> statusB) {
        this.intersectionPoints = config.getIntersectionPoints();
        this.conveyorA = statusA;
        this.conveyorB = statusB;
    }

    public List<IntersectionPoint> getIntersectionPoints() {
        return intersectionPoints;
    }

    public List<Integer> getConveyorA() {
        return conveyorA;
    }

    public List<Integer> getConveyorB() {
        return conveyorB;
    }
}
