package com.yast;

import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.*;

class Java8UtilsTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    public void testParseBoolean() {
        boolean booleanTrue = Java8Utils.parseBoolean(Boolean.toString(true));
        assertEquals(true, booleanTrue);
        boolean booleanFalse = Java8Utils.parseBoolean(Boolean.toString(false));
        assertEquals(false, booleanFalse);
        boolean string = Java8Utils.parseBoolean("Lorem Ipsum");
        assertEquals(false, string);
        boolean number = Java8Utils.parseBoolean("38031");
        assertEquals(false, number);
        boolean nil = Java8Utils.parseBoolean(null);
        assertEquals(false, nil);
    }

    @Test
    public void testParseDouble() {
        double d1 = Java8Utils.parseDouble("48926424.3122");
        double d2 = Java8Utils.parseDouble("48926424.3122");
        assertEquals(48926424.3122, d1);
        assertEquals(48926424.3122, d2);
        assertEquals(d1, d2);
        assertThrows(PolyglotException.class, () -> Java8Utils.parseDouble("1d"));
    }

    @Test
    public void testPrintHexBinary() throws UnsupportedEncodingException {
        String text = "Lorem ipsum dolor sit amet";
        byte[] inputBytes = text.getBytes("UTF-8");
        String hexText = Java8Utils.printHexBinary(inputBytes);
        assertEquals("4c6f72656d20697073756d20646f6c6f722073697420616d6574".toLowerCase(), hexText.toLowerCase());
        byte[] outputBytes = HexFormat.of().parseHex(hexText);
        assertEquals(text, new String(outputBytes, "UTF-8"));
    }

    @Test
    public void testCreateJava8Thread() {
        Value java8Thread = Java8Utils.createJava8Thread();
        assertEquals("java.lang.Thread", java8Thread.getMetaObject().getMetaQualifiedName());
        assertFalse(java8Thread.isHostObject());
        assertFalse(java8Thread.invokeMember("isAlive").asBoolean());
        assertEquals(Thread.State.NEW.toString(), java8Thread.invokeMember("getState").toString());
    }

    @Test
    public void testStartJava8ThreadAsHost() {
        Value java8Thread = Java8Utils.createJava8Thread();
        Runnable runnable = java8Thread.as(Runnable.class);
        Thread thread = new Thread(runnable);
        thread.start();
        assertTrue(thread.isAlive());
    }

    @Test
    public void testStartJava8ThreadAsGuest() {
        Value java8Thread = Java8Utils.createJava8Thread();
        Java8Utils.startJava8Thread(java8Thread);
        assertTrue(java8Thread.invokeMember("isAlive").asBoolean());
    }

    @Test
    public void testInterruptJava8ThreadAsHost() {
        Value java8Thread = Java8Utils.createJava8Thread();
        Runnable runnable = java8Thread.as(Runnable.class);
        Thread thread = new Thread(runnable);
        thread.start();
        assertTrue(thread.isAlive());
        thread.interrupt();
        assertTrue(thread.isInterrupted());
    }

    @Test
    public void testDestroyJava8ThreadNotPossible() {
        Value java8Thread = Java8Utils.createJava8Thread();
        Java8Utils.startJava8Thread(java8Thread);
        assertThrows(PolyglotException.class, () -> Java8Utils.destroyJava8Thread(java8Thread));
    }

    @Test
    public void testCreateJava8StringUsingDeclaredConstructor() {
        Value string = Java8Utils.createJava8StringUsingDeclaredConstructor("Lorem Ipsum");
        assertEquals("Lorem Ipsum", string.asString());
    }

    @Test
    public void testPrintHostJavaInfo() {
        System.setOut(new PrintStream(outputStreamCaptor));
        Java8Utils.printHostJavaInfo();
        assertEquals("Name:\t17.0.3.1\nVersion:\tJava HotSpot(TM) 64-Bit Server VM", outputStreamCaptor.toString().trim());
        System.setOut(standardOut);
    }

    @Test
    public void testPrintGuestJavaInfo() {
        System.setOut(new PrintStream(outputStreamCaptor));
        Java8Utils.printGuestJavaInfo();
        assertEquals("Name:\tEspresso 64-Bit VM\nVersion:\t1.8.0_242", outputStreamCaptor.toString().trim());
        System.setOut(standardOut);
    }
}