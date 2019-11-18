package ru.conveyor.data.conveyor.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PrimitiveArrayConveyor extends AbstractConveyor {

    private int[] queue;

    @Override
    public void init() {
        this.queue = new int[length];
    }

    @Override
    public int pushValue(int value) {
        int returnValue = queue[queue.length - 1];

        int[] both = Arrays.copyOf(queue, queue.length);
        System.arraycopy(queue, 0, both, 1, queue.length - 1);
        both[0] = value;

        queue = both;

        return returnValue;
    }

    @Override
    public List<Integer> getIntersectionValues() {
        return intersectionIndices.stream()
            .map(intersectionIndex -> queue[intersectionIndex])
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void updateIntersectionValues(List<Integer> values) {
        if (values.size() != intersectionIndices.size()) {
            throw new IllegalArgumentException("Incoming values size should match config");
        }

        for (int i = 0; i < intersectionIndices.size(); i++) {
            queue[intersectionIndices.get(i)] = values.get(i);
        }
    }

    @Override
    public List<Integer> getStatus() {
        return Arrays.stream(queue)
            .boxed()
            .collect(Collectors.toUnmodifiableList());
    }
}
