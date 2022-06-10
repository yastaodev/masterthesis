package com.yast.masterthesis.usecase03;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static String PYTHON = "python";
    private static String SOURCE_FILE_NAME = "/opt/work/workspaces/idea/master-thesis/usecase-03/src/main/resources/step01.py";
    //private static InputStream SOURCE_FILE_INPUT = Main.class.getClassLoader().getResourceAsStream(SOURCE_FILE_NAME);

    public static void main(String[] args) {
        Path path = Paths.get("venv", "bin", "graalpython");
        String VENV_EXECUTABLE = "/opt/work/workspaces/idea/master-thesis/usecase-03/venv/bin/graalpython";//path.toAbsolutePath().toString();
        try (Context context = Context.newBuilder("python").
                allowNativeAccess(true).
                allowAllAccess(true).
                option("python.ForceImportSite", "true").
                option("python.Executable", VENV_EXECUTABLE).
                build();
        ) {
            context.eval(PYTHON, "import site");
            context.eval(PYTHON, "print('Hello Python!')");

            context.eval(PYTHON, "import sys; print(sys.version)");
            context.eval(PYTHON, "import sys; print(sys.executable)");



            //InputStreamReader reader = new InputStreamReader(SOURCE_FILE_INPUT);
            System.out.println("#####" + SOURCE_FILE_NAME);
            Source source;
            try {
                source = Source.newBuilder(PYTHON, new File(SOURCE_FILE_NAME)).build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(source.toString());
            context.eval(source);

            try {

                // Use this to Read a Local Path
                // String svgUriImputLocation = Paths.get("https://www.contradodigital.com/logo.svg").toUri().toURL().toString();
                // Read Remote Location for SVG
                String svgUriImputLocation = "/opt/work/workspaces/idea/master-thesis/usecase-03/target/bar_code.svg";
                TranscoderInput transcoderInput = new TranscoderInput(svgUriImputLocation);

                // Define OutputStream Location
                OutputStream outputStream = new FileOutputStream("/opt/work/workspaces/idea/master-thesis/usecase-03/target/bar_code.png");
                TranscoderOutput transcoderOutput = new TranscoderOutput(outputStream);

                // Convert SVG to PNG and Save to File System
                PNGTranscoder pngTranscoder = new PNGTranscoder();
                pngTranscoder.transcode(transcoderInput, transcoderOutput);

                // Clean Up
                outputStream.flush();
                outputStream.close();

            } catch (IOException | TranscoderException ex) {
                System.out.println("Exception Thrown: " + ex);
            }

            try {
                source = Source.newBuilder(PYTHON, new File("/opt/work/workspaces/idea/master-thesis/usecase-03/src/main/resources/step02.py")).build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            context.eval(source);


            try {
                source = Source.newBuilder(PYTHON,
                        new File("/opt/work/workspaces/idea/master-thesis/usecase-03/src/main/resources/step03.py")).build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            context.eval(source);
            Value function = context.getPolyglotBindings().getMember("watermark_photo");
            function.execute("/opt/work/workspaces/idea/master-thesis/usecase-03/target/prefinal.png", "/opt/work/workspaces/idea/master-thesis/usecase-03/src/main/resources/stamp.png", "/opt/work/workspaces/idea/master-thesis/usecase-03/target/final.png");

        }


    }
}