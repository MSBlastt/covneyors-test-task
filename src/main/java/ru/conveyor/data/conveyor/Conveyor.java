package ru.conveyor.data.conveyor;

import java.util.List;

public interface Conveyor {

    /**
     * Pushes a value in the beginning of conveyor and removes the value from the end.
     *
     * @param value - value to push (add as first)
     * @return - value to be returned (removed last)
     */
    int pushValue(int value);

    /**
     * Returns values at intersection points
     *
     * @return unmodifiable list of values
     */
    List<Integer> getIntersectionValues();


    /**
     * Updates intersection points with given array
     *
     * @param values - values to update
     * @return - value to be returned (removed last)
     */
    void updateIntersectionValues(List<Integer> values);

    /**
     * Returns current conveyor status (numbers within)
     *
     * @return unmodifiable list of current values
     */
    List<Integer> getStatus();
}
