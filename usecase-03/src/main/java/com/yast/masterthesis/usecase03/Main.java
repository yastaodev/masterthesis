package com.yast.masterthesis.usecase03;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static String PYTHON = "python";
    private static String SOURCE_FILE_NAME = "step01.py";
    private static InputStream SOURCE_FILE_INPUT = Main.class.getClassLoader().getResourceAsStream(SOURCE_FILE_NAME);

    public static void main(String[] args) {
        Path path = Paths.get("venv", "bin", "graalpython");
        String VENV_EXECUTABLE = path.toString();
        try (Context context = Context.newBuilder("python").
                allowAllAccess(true).
                option("python.ForceImportSite", "true").
                option("python.Executable", VENV_EXECUTABLE).
                build();) {
            context.eval(PYTHON, "print('Hello Python!')");

            context.eval(PYTHON, "import sys; print(sys.version)");

            InputStreamReader reader = new InputStreamReader(SOURCE_FILE_INPUT);
            Source source;
            try {
                source = Source.newBuilder(PYTHON, reader, SOURCE_FILE_NAME).build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            context.eval(source);
        }
    }
}