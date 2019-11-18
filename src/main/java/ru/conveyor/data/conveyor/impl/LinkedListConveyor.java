package ru.conveyor.data.conveyor.impl;

import java.util.LinkedList;
import java.util.List;

public class LinkedListConveyor extends AbstractConveyor {

    private LinkedList<Integer> queue;

    public LinkedListConveyor() {
        this.queue = new LinkedList<>();
    }

    @Override
    public void init() {
        for (int i = 0; i < length; i++) {
            queue.add(0);
        }
    }

    @Override
    public int pushValue(int value) {
        queue.addFirst(value);
        return queue.removeLast();
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
