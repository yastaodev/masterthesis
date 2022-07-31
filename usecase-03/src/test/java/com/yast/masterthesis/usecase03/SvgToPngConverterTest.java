package com.yast.masterthesis.usecase03;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SvgToPngConverterTest {

    private static final String SVG_MIMETYPE = "image/svg+xml";
    private static final String PNG_MIMETYPE = "image/png";

    @Test
    public void shouldConvertFromSvgToPng() throws IOException {
        SvgToPngConverter converter = new SvgToPngConverter();
        File svg = new File("target/barcode.svg");
        Path svgPath = svg.toPath();
        String svgMimetype = Files.probeContentType(svgPath);
        String pngPathAsString = converter.convert(svgPath);
        File png = new File(pngPathAsString);
        String pngMimetype = Files.probeContentType(png.toPath());
        assertEquals(SVG_MIMETYPE, svgMimetype);
        assertEquals(PNG_MIMETYPE, pngMimetype);
    }

    private static URL getResourceUrl(String relativePath) {
        return Main.class.getClassLoader().getResource(relativePath);
    }

}