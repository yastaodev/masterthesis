package com.yast;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrimeNumberUtilsTest {

    @Test
    public void testPrimeNumbers() throws IOException {
        List<Integer> primeNumbers = PrimeNumberUtils.getPrimeNumbers(0, 100);
        assertEquals(List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97), primeNumbers);
    }

    @Test
    public void testCount() throws IOException {
        long count = PrimeNumberUtils.count(0, 100);
        assertEquals(25, count);
    }

    @Test
    public void testSum() throws IOException {
        int sum = PrimeNumberUtils.sum(0, 100);
        assertEquals(1060, sum);
    }

    @Test
    public void testAvg() throws IOException {
        double avg = PrimeNumberUtils.avg(0, 100);
        assertEquals(42.4, avg);
    }

    @Test
    public void testMin() throws IOException {
        int min = PrimeNumberUtils.min(0, 100);
        assertEquals(2, min);
    }

    @Test
    public void testMax() throws IOException {
        int max = PrimeNumberUtils.max(0, 100);
        assertEquals(97, max);
    }
}