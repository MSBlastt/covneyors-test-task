package ru.conveyor.data.conveyor.impl;

import org.apache.commons.collections4.list.TreeList;

import java.util.List;

public class ApacheTreeListConveyor extends AbstractConveyor {

    private TreeList<Integer> queue;

    @Override
    public void init() {
        this.queue = new TreeList<>();
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
