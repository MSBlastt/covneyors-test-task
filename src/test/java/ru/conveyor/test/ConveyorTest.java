package ru.conveyor.test;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.conveyor.config.FactoryConfig;
import ru.conveyor.data.ConveyorType;
import ru.conveyor.data.IntersectionPoint;
import ru.conveyor.service.FactoryService;
import ru.conveyor.util.PrimeNumberUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ConveyorTest {


    public static final int CONVEYORS_LENGTH = 2_500;

    @ParameterizedTest
    @EnumSource(ConveyorType.class)
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    public void conveyorTest(ConveyorType conveyorType) throws InterruptedException {
        // Prepare factory manager
        List<IntersectionPoint> crossingIndices = new LinkedList<>();

        for (int i = 20; i < CONVEYORS_LENGTH; i += 20) {
            crossingIndices.add(new IntersectionPoint(i, i - 5));
        }

        FactoryConfig factoryConfig = new FactoryConfig(crossingIndices, CONVEYORS_LENGTH, CONVEYORS_LENGTH, conveyorType, true);

        FactoryService factoryManager = new FactoryService(factoryConfig);

        // Get Conveyors status
        List<Integer> statusConveyorA = factoryManager.getStatusConveyorA();
        List<Integer> statusConveyorB = factoryManager.getStatusConveyorB();

        // Assert size
        assertThat(statusConveyorA.size(), CoreMatchers.is(CONVEYORS_LENGTH));
        assertThat(statusConveyorB.size(), CoreMatchers.is(CONVEYORS_LENGTH));

        // Push values
        int valueToBeReturned = statusConveyorA.get(statusConveyorA.size() - 1);
        int returnedValue = factoryManager.pushA(17);

        // Assert values
        assertThat(returnedValue, CoreMatchers.is(valueToBeReturned));

        // Update conveyor status
        statusConveyorA = factoryManager.getStatusConveyorA();

        // Assert pushed value
        assertThat(statusConveyorA.get(0), CoreMatchers.is(17));

        // Push values
        valueToBeReturned = statusConveyorB.get(statusConveyorB.size() - 1);
        returnedValue = factoryManager.pushB(19);

        // Assert values
        assertThat(returnedValue, CoreMatchers.is(valueToBeReturned));

        // Update conveyor status
        statusConveyorB = factoryManager.getStatusConveyorB();

        // Assert pushed value
        assertThat(statusConveyorB.get(0), CoreMatchers.is(19));

        // InterSection verification
        for (IntersectionPoint point : factoryConfig.getIntersectionPoints()) {
            int valueA = factoryManager.getStatusConveyorA().get(point.getIndexA());
            int valueB = factoryManager.getStatusConveyorB().get(point.getIndexB());

            assertThat(valueA, CoreMatchers.is(valueB));
        }

        if (conveyorType == ConveyorType.THREAD_SAFE) {
            startThreadSafeConveyorTest(factoryManager);
        }
    }

    private void startThreadSafeConveyorTest(FactoryService factoryManager) throws InterruptedException {
        List<Integer> primes = PrimeNumberUtils.generatePrimeNumbers();

        Random random = new Random();
        int low = 1;
        int high = 200;

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Callable<Integer>> callables = new ArrayList<>();

        // Push A only
        for (int i = 0; i < CONVEYORS_LENGTH; i++) {

            callables.add(() -> {
                int valueToPush = primes.get(random.nextInt(high - low) + low);
                int returnedValue = factoryManager.pushA(valueToPush);

                List<Integer> statusConveyorA = factoryManager.getStatusConveyorA();
                assertTrue(statusConveyorA.contains(valueToPush));

                return returnedValue;
            });
        }

        awaitFutures(service, callables);


        // Push B only
        for (int i = 0; i < CONVEYORS_LENGTH; i++) {

            callables.add(() -> {
                int valueToPush = primes.get(random.nextInt(high - low) + low);
                int returnedValue = factoryManager.pushB(valueToPush);

                List<Integer> statusConveyorB = factoryManager.getStatusConveyorB();
                assertTrue(statusConveyorB.contains(valueToPush));

                return returnedValue;
            });
        }

        awaitFutures(service, callables);


        // Push both A and B
        for (int i = 0; i < CONVEYORS_LENGTH; i++) {

            callables.add(() -> {
                if (random.nextBoolean()) {
                    int valueToPush = primes.get(random.nextInt(high - low) + low);
                    int returnedValue = factoryManager.pushA(valueToPush);

                    List<Integer> statusConveyorA = factoryManager.getStatusConveyorA();
                    assertTrue(statusConveyorA.contains(valueToPush));

                    return returnedValue;
                } else {
                    int valueToPush = primes.get(random.nextInt(high - low) + low);
                    int returnedValue = factoryManager.pushB(valueToPush);

                    List<Integer> statusConveyorB = factoryManager.getStatusConveyorB();
                    assertTrue(statusConveyorB.contains(valueToPush));

                    return returnedValue;
                }
            });
        }

        awaitFutures(service, callables);
    }

    private void awaitFutures(ExecutorService service, List<Callable<Integer>> callables) throws InterruptedException {
        List<Future<Integer>> futures = service.invokeAll(callables);

        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                fail();
            }
        });

        callables.clear();
    }
}
