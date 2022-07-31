package com.yast.masterthesis.usecase03;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class ImageProcessorTest {

    private static final String PYTHON = "python";
    private static final String VENV_EXECUTABLE = Paths.get("venv", "bin", "graalpython").toString();
    private static Context context;


    @BeforeAll
    public static void setup() {
        context = buildPythonContext(VENV_EXECUTABLE);
        context.eval(PYTHON, "import site");
        context.eval(PYTHON, "import sys; print(sys.version)");
        context.eval(PYTHON, "import sys; print(sys.executable)");
        evalSource(context, "image_utils_test.py");
    }

    @Test
    public void testCreateEmptyImage() {
        Value testCase = context.getPolyglotBindings().getMember("constructor").execute();
        Value val = testCase.getMember("test_create_empty_image").execute();
        System.out.println("OKAY");
    }

    private static void evalSource(Context context, String pythonScript) {
        Source source;
        try {
            source = Source.newBuilder(PYTHON, getResourceUrl(pythonScript)).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        context.eval(source);
    }

    private static Context buildPythonContext(String VENV_EXECUTABLE) {
        return Context.newBuilder(PYTHON)
                .allowNativeAccess(true)
                .allowAllAccess(true)
                .option("python.ForceImportSite", Boolean.toString(true))
                .option("python.Executable", VENV_EXECUTABLE)
                .arguments(PYTHON, new String[] {"-m", "unittest"})
                .build();
    }

    private static URL getResourceUrl(String relativePath) {
        return Main.class.getClassLoader().getResource(relativePath);
    }

}
