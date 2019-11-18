package ru.conveyor.test;

import org.junit.jupiter.api.Test;
import ru.conveyor.config.FactoryConfig;
import ru.conveyor.data.ConveyorType;
import ru.conveyor.data.IntersectionPoint;
import ru.conveyor.service.FactoryService;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConveyorNegativeTest {

    @Test
    public void outOfLengthTest() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> {
            // Prepare factory manager
            List<IntersectionPoint> crossingIndices = new LinkedList<>();
            crossingIndices.add(new IntersectionPoint(3, 4));
            crossingIndices.add(new IntersectionPoint(6, 8));

            new FactoryConfig(crossingIndices, 3, 2, ConveyorType.LINKED_LIST, false);
        });
    }

    @Test
    public void pushNegativeTest() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> {
            // Prepare factory manager
            List<IntersectionPoint> crossingIndices = new LinkedList<>();
            crossingIndices.add(new IntersectionPoint(3, 4));
            crossingIndices.add(new IntersectionPoint(6, 8));

            FactoryConfig factoryConfig = new FactoryConfig(crossingIndices, 10, 12, ConveyorType.LINKED_LIST, false);
            FactoryService factoryManager = new FactoryService(factoryConfig);

            factoryManager.pushA(-1);
        });
    }

    @Test
    public void pushNotPrimeNumber() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> {
            // Prepare factory manager
            List<IntersectionPoint> crossingIndices = new LinkedList<>();
            crossingIndices.add(new IntersectionPoint(3, 4));
            crossingIndices.add(new IntersectionPoint(6, 8));

            FactoryConfig factoryConfig = new FactoryConfig(crossingIndices, 10, 12, ConveyorType.LINKED_LIST, false);
            FactoryService factoryManager = new FactoryService(factoryConfig);

            factoryManager.pushA(2);
            factoryManager.pushB(6);
        });
    }

    @Test
    public void pushIllegalValuesTest() throws IllegalArgumentException {
        boolean exceptionIsThrown = false;

        // Negative case Length A
        try {
            new FactoryService(new FactoryConfig(Collections.emptyList(), -5, 5, ConveyorType.LINKED_LIST, false));
        } catch (IllegalArgumentException exception) {
            exceptionIsThrown = true;
        }

        assertTrue(exceptionIsThrown);
        exceptionIsThrown = false;

        // Negative case Length B
        try {
            new FactoryService(new FactoryConfig(Collections.emptyList(), 5, -5, ConveyorType.LINKED_LIST, false));
        } catch (IllegalArgumentException exception) {
            exceptionIsThrown = true;
        }

        assertTrue(exceptionIsThrown);
        exceptionIsThrown = false;

        // Negative case null variables
        try {
            new FactoryService(new FactoryConfig(null, 5, 5, ConveyorType.LINKED_LIST, false));
        } catch (IllegalArgumentException exception) {
            exceptionIsThrown = true;
        }

        assertTrue(exceptionIsThrown);
        exceptionIsThrown = false;


        // Negative case null variables
        try {
            List<IntersectionPoint> crossingIndices = Arrays.asList(new IntersectionPoint(2, 2));
            new FactoryService(new FactoryConfig(crossingIndices, 5, 5, null, false));
        } catch (IllegalArgumentException exception) {
            exceptionIsThrown = true;
        }

        assertTrue(exceptionIsThrown);
        exceptionIsThrown = false;

        // Negative case negative intersection values
        try {
            List<IntersectionPoint> crossingIndices = Arrays.asList(new IntersectionPoint(-2, 2), null);
            new FactoryService(new FactoryConfig(crossingIndices, 5, 5, ConveyorType.LINKED_LIST, false));
        } catch (IllegalArgumentException exception) {
            exceptionIsThrown = true;
        }

        assertTrue(exceptionIsThrown);
        exceptionIsThrown = false;

        // Negative case negative intersection values
        try {
            List<IntersectionPoint> crossingIndices = Arrays.asList(new IntersectionPoint(2, -2), null);
            new FactoryService(new FactoryConfig(crossingIndices, 5, 5, ConveyorType.LINKED_LIST, false));
        } catch (IllegalArgumentException exception) {
            exceptionIsThrown = true;
        }

        assertTrue(exceptionIsThrown);

    }

}
