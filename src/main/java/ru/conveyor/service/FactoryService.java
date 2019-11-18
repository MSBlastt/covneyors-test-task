package ru.conveyor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.conveyor.api.dto.FactoryStatusDto;
import ru.conveyor.config.FactoryConfig;
import ru.conveyor.data.ConveyorStrategy;
import ru.conveyor.data.conveyor.Conveyor;
import ru.conveyor.util.PrimeNumberUtils;

import java.util.List;
import java.util.Random;

@Service
public final class FactoryService {

    private final FactoryConfig config;

    private final Conveyor conveyorA;
    private final Conveyor conveyorB;

    @Autowired
    public FactoryService(FactoryConfig config) {
        this.config = config;

        conveyorA = ConveyorStrategy.getConveyorStrategy(config.getConveyorType(), config.getConveyorALength(), config.getIntersectionIndicesForA());
        conveyorB = ConveyorStrategy.getConveyorStrategy(config.getConveyorType(), config.getConveyorBLength(), config.getIntersectionIndicesForB());

        if (config.isPrefillConveyors()) {
            prefillConveyors();
        }
    }

    /**
     * Returns Config (properties) and both conveyors values
     */
    public FactoryStatusDto getFactoryStatus() {
        return new FactoryStatusDto(config, conveyorA.getStatus(), conveyorB.getStatus());
    }

    /**
     * Pushes value to Conveyor A
     */
    public int pushA(int value) throws IllegalArgumentException {
        validateConveyorInput(value);
        return pushConveyor(value, conveyorA, conveyorB);
    }

    /**
     * Pushes value to Conveyor B
     */
    public int pushB(int value) throws IllegalArgumentException {
        validateConveyorInput(value);
        return pushConveyor(value, conveyorB, conveyorA);
    }


    /**
     * Returns Conveyor A values
     */
    public List<Integer> getStatusConveyorA() {
        return conveyorA.getStatus();
    }

    /**
     * Returns Conveyor B values
     */
    public List<Integer> getStatusConveyorB() {
        return conveyorB.getStatus();
    }

    public FactoryConfig getConfig() {
        return config;
    }

    /**
     * Pre-populates conveyors with some random prime numbers
     */
    private void prefillConveyors() {
        List<Integer> primes = PrimeNumberUtils.generatePrimeNumbers();

        Random random = new Random();
        int low = 1;
        int high = 200;

        for (int i = 0; i < conveyorA.getStatus().size(); i++) {
            conveyorA.pushValue(primes.get(random.nextInt(high - low) + low));
        }

        for (int i = 0; i < conveyorB.getStatus().size(); i++) {
            conveyorB.pushValue(primes.get(random.nextInt(high - low) + low));
        }
    }

    private void validateConveyorInput(int value) {
        if (!PrimeNumberUtils.isPrime(value)) {
            throw new IllegalArgumentException(value + " - is not a prime number!");
        }
    }

    private int pushConveyor(int value, Conveyor conveyorToPush, Conveyor conveyorToUpdate) {
        int valueToReturn = conveyorToPush.pushValue(value);

        conveyorToUpdate.updateIntersectionValues(conveyorToPush.getIntersectionValues());

        return valueToReturn;
    }

}
