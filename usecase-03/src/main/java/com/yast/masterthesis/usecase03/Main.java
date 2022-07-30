package com.yast.masterthesis.usecase03;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import javax.imageio.ImageIO;

public class Main {

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
    // einfach einfügen und glücklich sein, Probleme besser gelöst als in der Lieblingssprache, z.B. Crypto-Operationen wie Hashing,
    // Bildbearbeitung etc.
    // Man ruft Funktionen auf und gibt Argumente und bekommt Results zurück, als wären sie in der Hostsprache geschrieben. Leider keine
    // Rückmeldung der IDE, Argumente sind einfach Strings, die IDE saggt nicht welcher Rückgabetyp die Funktion der Gastsprache ist, und auch
    // welche Argumente sie entgegen nimmt

    //Wenn sich die Namen der Python-Funktionen ändern, habe ich im ImageProcessor ein Problem

    // Integrationstest in Java vergleichen mit Integrationstest in Python

    // Nach 1000 Versuchen konnte ich in Python den QR-Code nicht auslesen (OpenCV, zybar, etc.)
        // Dann versucht mit Java zxing Library
        // Es hat geklappt, aber ich konnte den Code nicht in Python Test verwenden, da ich JAR exportieren und Classpath setzen müsste
        // Also sind die Tests in Python nicht vollständig, also in Java machen
        // Das ist auch ein Anwendungsfall, wo man merkt, dass der pragmatische Ansatz der beste ist

    // Ein weiteres Ding: Wenn ich eine Java-Funktion in Python aufrufe, zeigt mir die IDE die Funktion als wäre sie unused
        // Beispiel: ImageProcessor#readQrCode
        // Zu viele Strings

    // In test_create_qrcode_image wurde Java Code aufgerufen, da nicht möglich war OpenCV/zybar zum laufen zu kriegen -> Pragmatischer Ansatz
        // OpenCv funktioniert überigens sehr gut mit Python, nur mit Graalpython nicht

    // Eine schöne Sache: siehe image_utils_test#test_create_barcode_image man merkt gar nicht, dass man Java Code verwendet. Es sieht alles
    // aus wie Python
    public static void main(String[] args) {
        String outImgPath = null;
        ImageProcessor imageProcessor = new ImageProcessor();
        String emptyImgPath = imageProcessor.createEmptyImage();
        String qrcodeImgPath = imageProcessor.createQrcodeImage("lorem ipsum dolor sit amet", "#A5FF00", "#5A5A5A");
        String barcodeSvgImgPath = imageProcessor.createBarcodeImage("5909876183457");
        String barcodePngImgPath = imageProcessor.convertSvgToPng(barcodeSvgImgPath);
        imageProcessor.rotateImage(barcodePngImgPath);
        outImgPath = imageProcessor.merge(emptyImgPath, qrcodeImgPath, 50, 50);
        outImgPath = imageProcessor.merge(outImgPath, barcodePngImgPath, 700, -60);
        imageProcessor.addText(outImgPath, "Automatically generated!", 450, 230);
        imageProcessor.addWatermark(outImgPath, getResourceUrl("stamp.png").getPath());
        imageProcessor.openImage(outImgPath);
    }

    private static URL getResourceUrl(String relativePath) {
        return Main.class.getClassLoader().getResource(relativePath);
    }
}