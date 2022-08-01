package com.yast.masterthesis.usecase01;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {

    private static Validator validator;

    @BeforeAll
    public static void setup() {
        validator = new Validator();
    }

    @Test
    public void testMACAddressIsValid() {
        assertTrue(validator.isMACAddress("01:23:45:67:89:AB"));
    }

    @Test
    public void testMACAddressIsInvalid() {
        assertFalse(validator.isMACAddress("01:23:45:67:89:XY"));
    }

    @Test
    public void testIBANIsValid() {
        assertTrue(validator.isIBAN("DE86390700240173134800"));
    }

    @Test
    public void testIBANIsInvalid() {
        assertFalse(validator.isIBAN("DE863907002401731348"));
    }

    @Test
    public void testHexColorIsValid() {
        assertTrue(validator.isHexColor("#FFFFFF"));
    }

    @Test
    public void testHexColorIsInvalid() {
        assertFalse(validator.isHexColor("#12345"));
    }

    @Test
    public void testJWTIsValid() {
        assertTrue(validator.isJWT("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"));
    }

    @Test
    public void testJWTIsInvalid() {
        assertFalse(validator.isJWT("eyJhbGciOiJIUzI1NiIs"));
    }

    @Test
    public void testCreditCardIsValid() {
        assertTrue(validator.isCreditCard("2453876988550878"));
    }

    @Test
    public void testCreditCardIsInvalid() {
        assertFalse(validator.isCreditCard("ABCD"));
    }

    @Test
    public void testEthereumAddressIsValid() {
        assertTrue(validator.isEthereumAddress("0x0749B848Fb42781e6E8292a376B2f43184fA1f37"));
    }

    @Test
    public void testEthereumAddressIsInvalid() {
        assertFalse(validator.isEthereumAddress("x00749B848Fb42781e6E8292a376B2f43184fA1f37"));
    }

    @Test
    public void testPasswordIsStrong() {
        assertTrue(validator.isStrongPassword("zFsp23#-hif0fs"));
    }

    @Test
    public void testPasswordIsWeak() {
        assertFalse(validator.isStrongPassword("password1234"));
    }
}