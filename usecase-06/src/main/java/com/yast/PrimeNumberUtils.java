package com.yast;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import org.graalvm.polyglot.*;

import static java.util.Arrays.stream;

public class PrimeNumberUtils {

    private static Context context;

    public static long count(int low, int high) throws IOException {
        List<Integer> primeNumbers = getPrimeNumbers(low, high);
        return primeNumbers.stream().count();
    }

    public static int sum(int low, int high) throws IOException {
        List<Integer> primeNumbers = getPrimeNumbers(low, high);
        return primeNumbers.stream().reduce(0, Integer::sum);
    }

    public static double avg(int low, int high) throws IOException {
        List<Integer> primeNumbers = getPrimeNumbers(low, high);
        return primeNumbers.stream().mapToInt(i -> i).summaryStatistics().getAverage();
    }

    public static int min(int low, int high) throws IOException {
        List<Integer> primeNumbers = getPrimeNumbers(low, high);
        return primeNumbers.stream().mapToInt(i -> i).summaryStatistics().getMin();
    }

    public static int max(int low, int high) throws IOException {
        List<Integer> primeNumbers = getPrimeNumbers(low, high);
        return primeNumbers.stream().mapToInt(i -> i).summaryStatistics().getMax();
    }

    public static List<Integer> getPrimeNumbers(int from, int to) throws IOException {
        if (context == null) {
            initContext();
        }
        Value cppPrimeFunction = context.getPolyglotBindings().getMember("prime");
        Value primeNumbers = cppPrimeFunction.execute(from, to);
        String[] split = primeNumbers.asString().split(" ");
        return stream(split).map(s -> Integer.parseInt(s)).collect(Collectors.toList());
    }

    private static void initContext() throws IOException {
        context = Context.newBuilder().allowAllAccess(true).build();
        File file = new File("target/lib/prime.so");
        Source source = Source.newBuilder("llvm", file).build();
        Value cppProgram = context.eval(source);
        cppProgram.execute();
    }
}
