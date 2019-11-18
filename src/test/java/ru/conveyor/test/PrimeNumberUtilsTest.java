package ru.conveyor.test;

import org.junit.jupiter.api.Test;
import ru.conveyor.util.PrimeNumberUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrimeNumberUtilsTest {

    @Test
    public void utilsTest() {
        assertFalse(PrimeNumberUtils.isPrime(-5));
        assertFalse(PrimeNumberUtils.isPrime(0));
        assertFalse(PrimeNumberUtils.isPrime(1));
        assertFalse(PrimeNumberUtils.isPrime(99));

        assertTrue(PrimeNumberUtils.isPrime(2));
        assertTrue(PrimeNumberUtils.isPrime(5));
        assertTrue(PrimeNumberUtils.isPrime(17));
        assertTrue(PrimeNumberUtils.isPrime(83));
    }

    @Test
    public void primeNumberTest() {
        List<Integer> primeNumberUtils = PrimeNumberUtils.generatePrimeNumbers();

        assertTrue(primeNumberUtils.size() > 0);

        for (Integer element : primeNumberUtils) {
            assertTrue(PrimeNumberUtils.isPrime(element));
        }
    }
}
