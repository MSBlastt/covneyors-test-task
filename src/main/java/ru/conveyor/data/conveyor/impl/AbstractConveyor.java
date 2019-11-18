package ru.conveyor.data.conveyor.impl;

import ru.conveyor.data.conveyor.Conveyor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class AbstractConveyor implements Conveyor {

    protected List<Integer> intersectionIndices;
    protected int length;

    public AbstractConveyor() {
    }

    public void setIntersectionIndices(List<Integer> intersectionIndices) {
        this.intersectionIndices = intersectionIndices;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public abstract void init();

    protected void fillConveyorWithZeroes(Collection<Integer> queue) {
        for (int i = 0; i < length; i++) {
            queue.add(0);
        }
    }

    protected int pushValue(List<Integer> queue, int value) {
        queue.add(0, value);
        return queue.remove(queue.size() - 1);
    }

    protected List<Integer> getIntersectionValues(List<Integer> queue) {
        return intersectionIndices.stream()
            .map(queue::get)
            .collect(Collectors.toUnmodifiableList());
    }

    protected void updateIntersectionPoints(List<Integer> queue, List<Integer> values) {
        if (values.size() != intersectionIndices.size()) {
            throw new IllegalArgumentException("Incoming values size should match config");
        }

        IntStream.range(0, intersectionIndices.size())
            .forEach(i -> queue.set(intersectionIndices.get(i), values.get(i)));
    }

    protected List<Integer> getStatus(List<Integer> queue) {
        return Collections.unmodifiableList(queue);
    }

}
