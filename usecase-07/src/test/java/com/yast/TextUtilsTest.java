package com.yast;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

public class TextUtilsTest {

    @Test
    public void testExtractWords() throws IOException {
        String base64 = readText("/base64.txt");
        String[] words = TextUtils.extractWords(base64);

        assertNotNull(words);
        assertEquals("At", words[0]);
        assertEquals("At", words[1]);
        assertEquals("gubergren", words[54]);
        assertEquals("voluptua", words[99]);
        assertEquals(8, stream(words).filter(w -> w.equals("et")).count());
        assertEquals(4, stream(words).filter(w -> w.equals("sed")).count());
    }

    private String readText(String filePath) throws IOException {
        InputStream inputStream = TextUtilsTest.class.getResourceAsStream(filePath);
        return readFromInputStream(inputStream);
    }

    private String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

}