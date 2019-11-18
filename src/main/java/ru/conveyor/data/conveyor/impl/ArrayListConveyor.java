package ru.conveyor.data.conveyor.impl;

import java.util.ArrayList;
import java.util.List;

public class ArrayListConveyor extends AbstractConveyor {

    private List<Integer> queue;

    public ArrayListConveyor() {
    }

    @Override
    public void init() {
        this.queue = new ArrayList<>(length);
        fillConveyorWithZeroes(queue);
    }

    @Override
    public int pushValue(int value) {
        return pushValue(queue, value);
    }

    @Override
    public List<Integer> getIntersectionValues() {
        return getIntersectionValues(queue);
    }

    @Override
    public void updateIntersectionValues(List<Integer> values) {
        updateIntersectionPoints(queue, values);
    }

    @Override
    public List<Integer> getStatus() {
        return getStatus(queue);
    }
}
