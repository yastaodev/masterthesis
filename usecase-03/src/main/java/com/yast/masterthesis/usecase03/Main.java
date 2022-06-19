package com.yast.masterthesis.usecase03;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class Main {
    public static final String PYTHON = "python";
    private static final String VENV_EXECUTABLE = Paths.get("venv", "bin", "graalpython").toString();
    //private static InputStream SOURCE_FILE_INPUT = Main.class.getClassLoader().getResourceAsStream(SOURCE_FILE_NAME);

    // 1. Resolve relative path
    // 2. Python code should be reorganized in functions which take the path of the newly created image and return the processed image as a
    // result, which can be forwarded later to another function
    // 3.
    //      a. All functions should be included in a single .py file with one input and one output to demonstrate that testing of the guest
    // language cannot be ignored
    //      b. Every function in its own file:
//      aus pragmatischer Sicht: Wenn man die Wahl hat, dann die Funktionen verteilen und mit den bekannten Tools z.B. JUnit polyglot testen.
    // Wenn das Skript bereits steht und die zugehörigen Tests bereits vorhanden sind, dann ist die bessere Alternative dies separat zu machen
    // und im Java-Code ignorieren. Die Tests erfolgen hier mit den bekannten Werkzeugen der Gastsprache.

    // Ein Grund warum nur ein Skript verwenden mit einer einzelnen Eingangsfunktion ist die Notwendigeit von "imnport Polyglot" und
    // "@polyglot.export_value" vor jeder Funktion

    // Wir haben aber ein Problem, nämlich, dass wir eine Java-Funktion auf

    // 4. Write the tests
    public static void main(String[] args) {
        System.out.println("Hey!");
        try (Context context = buildPythonContext(VENV_EXECUTABLE)) {
            context.eval(PYTHON, "import site");
            context.eval(PYTHON, "import sys; print(sys.version)");
            context.eval(PYTHON, "import sys; print(sys.executable)");

            Source source;
            try {
                source = Source.newBuilder(PYTHON, getResourceUrl("step01.py")).build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            context.eval(source);

            convertSvgToPng(Paths.get("target/bar_code.svg"), Paths.get("target/bar_code.png"));

            try {
                source = Source.newBuilder(PYTHON, getResourceUrl("step02.py")).build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            context.eval(source);


            try {
                source = Source.newBuilder(PYTHON, getResourceUrl("step03.py")).build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            context.eval(source);
            Value function = context.getPolyglotBindings().getMember("watermark_photo");
            function.execute(getTargetFileLocation("prefinal.png"), getResourceUrl("stamp.png").getPath(), getTargetFileLocation("final.png"));


            try {
                source = Source.newBuilder(PYTHON, getResourceUrl("step04.py")).build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            context.eval(source);

        }


    }

    private static String getTargetFileLocation(String fileName) {
        return Paths.get("target/" + fileName).toAbsolutePath().toString();
    }

    private static void convertSvgToPng(Path input, Path output) {
        try {
            String svgUriInputLocation = input.toAbsolutePath().toString();
            TranscoderInput transcoderInput = new TranscoderInput(svgUriInputLocation);

            OutputStream outputStream = new FileOutputStream(output.toAbsolutePath().toString());
            TranscoderOutput transcoderOutput = new TranscoderOutput(outputStream);

            PNGTranscoder pngTranscoder = new PNGTranscoder();
            pngTranscoder.transcode(transcoderInput, transcoderOutput);

            outputStream.flush();
            outputStream.close();
        } catch (IOException | TranscoderException ex) {
            System.out.println("Exception Thrown: " + ex);
        }
    }

    private static Context buildPythonContext(String VENV_EXECUTABLE) {
        return Context.newBuilder("python").allowNativeAccess(true).allowAllAccess(true).option("python.ForceImportSite", "true").option("python.Executable", VENV_EXECUTABLE).build();
    }

    private static URL getResourceUrl(String relativePath) {
        return Main.class.getClassLoader().getResource(relativePath);
    }
}