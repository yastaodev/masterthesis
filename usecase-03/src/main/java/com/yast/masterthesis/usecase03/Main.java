package com.yast.masterthesis.usecase03;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class Main {
    private static final String PYTHON = "python";
    private static final String VENV_EXECUTABLE = Paths.get("venv", "bin", "graalpython").toString();

    // 2. Python code should be reorganized in functions which take the path of the newly created image and return the processed image as a
    // result, which can be forwarded later to another function
    // 3.
    //      a. All functions should be included in a single .py file with one input and one output to demonstrate that testing of the guest
    // language cannot be ignored
    //      b. Every function in its own file: Nachteil: Man muss jede Funktion einzeln getMemberKeys und aufrufen
//      aus pragmatischer Sicht: Wenn man die Wahl hat, dann die Funktionen verteilen und mit den bekannten Tools z.B. JUnit polyglot testen.
    // Wenn das Skript bereits steht und die zugehörigen Tests bereits vorhanden sind, dann ist die bessere Alternative dies separat zu machen
    // und im Java-Code ignorieren. Die Tests erfolgen hier mit den bekannten Werkzeugen der Gastsprache.

    // Ein Grund warum nur ein Skript verwenden mit einer einzelnen Eingangsfunktion ist die Notwendigeit von "imnport Polyglot" und
    // "@polyglot.export_value" vor jeder Funktion

    // Wir haben aber ein Problem, nämlich, dass wir eine Java-Funktion auf

    // 4. Write the tests

    // Wichtige Erkenntnis!!! Integrationstest kann nur in der Hostsprache geschrieben werden

    // Was ist hier besser? Java als Hostsprache oder Python
    // Noch ein interessanter Fall: Java ruft Python auf, und Python ruft Java auf
        // das heißt: Ein Script ist in python geschrieben, der das ganze Zeug erledigt. Beim Konvertieren von SVG to PNG Java Converter in py
        // aufrufen
        // Hier wieder die Pragmatik und gleichzeitig die Schönheit und Flexibilität von GraalVM: Der SVG Converter von Python wollte nicht
        // auf meinem Ubuntu funktionieren, also habe ich einfach das von Java verwendet und it works like a charm. Diese Felxibilität bezahlt
    // man mit CPU/RAM, da sehr viel braucht
        // By the way: GraalVM ist ganz nützlich wenn man schnell einen Snippet im Internet findet, z.B in Rust oder Go oder was auch immer:
        // einfach einfügen und glücklich sein
    // Man ruft Funktionen auf und gibt Argumente und bekommt Results zurück, als wären sie in der Hostsprache geschrieben. Leider keine
    // Rückmeldung der IDE, Argumente sind einfach Strings, die IDE saggt nicht welcher Rückgabetyp die Funktion der Gastsprache ist, und auch
    // welche Argumente sie entgegen nimmt

    // Integrationstest in Java vergleichen mit Integrationstest in Python
    public static void main(String[] args) {
        try (Context context = buildPythonContext(VENV_EXECUTABLE)) {
            context.eval(PYTHON, "import site");
            context.eval(PYTHON, "import sys; print(sys.version)");
            context.eval(PYTHON, "import sys; print(sys.executable)");

            evalSource(context, "image_utils.py");

            //TODO String in Konstanten extrahieren

            String outImgPath = null;

            Value funcCreateEmptyImage = context.getPolyglotBindings().getMember("create_empty_image");
            String emptyImgPath = funcCreateEmptyImage.execute().asString();

            Value funcCreateQrcodeImage = context.getPolyglotBindings().getMember("create_qrcode_image");
            String qrcodeImgPath = funcCreateQrcodeImage.execute("lorem ipsum dolor sit amet").asString();

            Value funcCreateBarcodeImage = context.getPolyglotBindings().getMember("create_barcode_image");
            String barcodeSvgImgPath = funcCreateBarcodeImage.execute("5909876183457").asString();

            String barcodePngImgPath = ImageUtils.convertSvgToPng(Paths.get(barcodeSvgImgPath));

            Value funcRotate = context.getPolyglotBindings().getMember("rotate");
            funcRotate.execute(barcodePngImgPath);

            Value funcMerge = context.getPolyglotBindings().getMember("merge");
            outImgPath = funcMerge.execute(emptyImgPath, qrcodeImgPath, 50, 50).asString();
            outImgPath = funcMerge.execute(outImgPath, barcodePngImgPath, 700, -2).asString();

            Value funcAddText = context.getPolyglotBindings().getMember("add_text");
            funcAddText.execute(outImgPath, "Automatically generated!", 470, 230);

            Value funcAddWatermark = context.getPolyglotBindings().getMember("add_watermark");
            funcAddWatermark.execute(outImgPath, getResourceUrl("stamp.png").getPath());

            ImageUtils.openImage(outImgPath);
        }
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
                .build();
    }

    private static URL getResourceUrl(String relativePath) {
        return Main.class.getClassLoader().getResource(relativePath);
    }
}