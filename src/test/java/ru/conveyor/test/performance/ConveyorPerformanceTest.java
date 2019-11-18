package ru.conveyor.test.performance;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import ru.conveyor.config.FactoryConfig;
import ru.conveyor.data.ConveyorType;
import ru.conveyor.data.IntersectionPoint;
import ru.conveyor.service.FactoryService;
import ru.conveyor.util.PrimeNumberUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Warmup(iterations = 5, time = 300, timeUnit = MILLISECONDS)
@Measurement(iterations = 5, time = 500, timeUnit = MILLISECONDS)
@Fork(1)
@BenchmarkMode(Mode.Throughput)
@State(Scope.Benchmark)
public class ConveyorPerformanceTest {

    @Param
    private ConveyorType conveyorType;

    private static final int CONVEYORS_LENGTHS = 1000;

    @Benchmark
    public void conveyorPerformanceTest() {
        FactoryService factoryManager = prepareFactory(conveyorType);
        startFactoryLoad(factoryManager);
    }

    private FactoryService prepareFactory(ConveyorType conveyorType) {
        List<IntersectionPoint> crossingIndices = new LinkedList<>();

        for (int i = 20; i < 1000; i += 20) {
            crossingIndices.add(new IntersectionPoint(i, i - 5));
        }

        FactoryConfig factoryConfig = new FactoryConfig(crossingIndices, CONVEYORS_LENGTHS, 1000, conveyorType, false);

        return new FactoryService(factoryConfig);
    }

    private void startFactoryLoad(FactoryService factoryManager) {
        List<Integer> primes = PrimeNumberUtils.generatePrimeNumbers();

        Random random = new Random();

        for (int i = 0; i < CONVEYORS_LENGTHS; i++) {
            factoryManager.pushA(primes.get(random.nextInt(100)));
            factoryManager.pushB(primes.get(random.nextInt(100)));

            if (i % 10 == 0) {
                factoryManager.getStatusConveyorA();
                factoryManager.getStatusConveyorB();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}