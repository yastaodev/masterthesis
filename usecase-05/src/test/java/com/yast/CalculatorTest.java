package com.yast;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.Ratio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {

    @Test
    public void testAddition() {
        int i1 = add(1, 2);
        int i2 = add(2, 1);
        assertEquals(i1, i2);
        int i3 = add(1, 0);
        assertEquals(1, i3);
        int i4 = add(0, 0);
        assertEquals(0, i4);
    }

    @Test
    public void testSubtraction() {
        int i1 = subtract(2, 1);
        int i2 = subtract(1, 2);
        assertEquals(i1, -i2);
        int i3 = subtract(1, 0);
        assertEquals(1, i3);
        int i4 = subtract(0, 0);
        assertEquals(0, i4);
    }

    @Test
    public void testMultiplication() {
        int i1 = multiply(2, 1);
        int i2 = multiply(1, 2);
        assertEquals(i1, i2);
        int i3 = multiply(1, 0);
        assertEquals(0, i3);
        int i4 = multiply(0, 0);
        assertEquals(0, i4);
    }

    @Test
    public void testDivision() {
        int i1 = divide(4, 2);
        int i2 = divide(4, 2);
        assertEquals(i1, i2);
        int i3 = divide(10, 5);
        assertEquals(2, i3);
        int i4 = divide(1, 2);
        assertEquals(0, i4);
        assertThrows(RuntimeException.class, () -> divide(1, 0));
    }

    private int add(int a, int b) {
        return Addition.add(a, b);
    }

    private int subtract(int a, int b) {
        return new Subtraction().subtract(a, b);
    }

    private int multiply(int a, int b) {
        return (int) Multiplication.multiply(a, b);
    }

    private int divide(int a, int b) {
        IFn require = Clojure.var("clojure.core", "require");
        require.invoke(Clojure.read("com.yast.Division"));
        IFn divide = Clojure.var("com.yast.Division", "divide");
        return (int) divide.invoke(a, b);
    }

}
