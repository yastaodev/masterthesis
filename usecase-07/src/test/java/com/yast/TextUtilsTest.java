package com.yast;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

public class TextUtilsTest {

    @Test
    public void testExtractWords() throws IOException {
        String base64 = readText("/base64.txt");
        testExtractWords();
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