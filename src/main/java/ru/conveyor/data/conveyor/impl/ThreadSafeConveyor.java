package ru.conveyor.data.conveyor.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadSafeConveyor extends AbstractConveyor {

    private List<Integer> queue;

    @Override
    public void init() {
        this.queue = new CopyOnWriteArrayList<>();
        fillConveyorWithZeroes(queue);
    }

    @Override
    public synchronized int pushValue(int value) {
        return pushValue(queue, value);
    }

    @Override
    public synchronized List<Integer> getIntersectionValues() {
        return getIntersectionValues(queue);
    }

    @Override
    public synchronized void updateIntersectionValues(List<Integer> values) {
        updateIntersectionPoints(queue, values);
    }

    @Override
    public synchronized List<Integer> getStatus() {
        return getStatus(queue);
    }
}
