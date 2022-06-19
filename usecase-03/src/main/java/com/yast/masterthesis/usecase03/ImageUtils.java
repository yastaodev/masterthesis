package com.yast.masterthesis.usecase03;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ImageUtils {
    public static String convertSvgToPng(Path input) {
        SvgToPngConverter svgToPngConverter = new SvgToPngConverter();
        System.out.println("Java :: Image has been from SVG to PNG converted.");
        return svgToPngConverter.convert(input);
    }

    public static void openImage(String imagePath) {
        try {
            System.out.println("Java :: Opening image with the OS default program...");
            Desktop.getDesktop().open(new File(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
