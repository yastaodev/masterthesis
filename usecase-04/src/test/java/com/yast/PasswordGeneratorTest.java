package com.yast;

import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordGeneratorTest {

    @Test
    public void shouldPasswordBeRandomlyGenerated() {
        IsolateThread isolateThread = null;
        CCharPointer cCharPointer1 = PasswordGenerator.generatePassword(isolateThread);
        String password1 = cCharPointer1.toString();
        CCharPointer cCharPointer2 = PasswordGenerator.generatePassword(isolateThread);
        String password2 = cCharPointer2.toString();
        Assertions.assertNotEquals(password1, password2);
    }

    @Test
    public void shouldPasswordContainsExactlyEightChars() {
        IsolateThread isolateThread = null;
        CCharPointer cCharPointer = PasswordGenerator.generatePassword(isolateThread);
        String password = cCharPointer.toString();
        Assertions.assertEquals(8, password.length());
    }

    @Test
    public void shouldPasswordContainsExactlyFourAlphabets() {
        IsolateThread isolateThread = null;
        CCharPointer cCharPointer = PasswordGenerator.generatePassword(isolateThread);
        String password = cCharPointer.toString();
        String subString = password.replaceAll("[^A-Za-z]+", "");
        Assertions.assertEquals(4, subString.length());
    }

    @Test
    public void shouldPasswordContainsExactlyTwoDigits() {
        IsolateThread isolateThread = null;
        CCharPointer cCharPointer = PasswordGenerator.generatePassword(isolateThread);
        String password = cCharPointer.toString();
        String subString = password.replaceAll("\\D+","");
        Assertions.assertEquals(2, subString.length());
    }

    @Test
    public void shouldPasswordContainsExactlyTwoSpecialChars() {
        IsolateThread isolateThread = null;
        CCharPointer cCharPointer = PasswordGenerator.generatePassword(isolateThread);
        String password = cCharPointer.toString();
        String subString = password.replaceAll("[^A-Za-z0-9]", "");
        Assertions.assertEquals(2, subString.length());
    }

}