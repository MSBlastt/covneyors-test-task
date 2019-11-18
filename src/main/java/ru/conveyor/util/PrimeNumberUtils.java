package ru.conveyor.util;

import org.apache.commons.math3.primes.Primes;

import java.util.ArrayList;
import java.util.List;

public class PrimeNumberUtils {

    /**
     * Returns few hundred prime numbers
     */
    public static List<Integer> generatePrimeNumbers() {
        List<Integer> list = new ArrayList<>();

        int lastPrime = 1;

        for (int i = 1; i <= 200; i += 1) {
            lastPrime = Primes.nextPrime(lastPrime);
            list.add(lastPrime);
            lastPrime++;
        }

        return list;
    }

    /**
     * Primality test
     */
    public static boolean isPrime(int number) {
        return Primes.isPrime(number);
    }

}
