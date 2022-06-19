package com.yast.masterthesis.usecase03;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

public class SvgToPngConverter {
    public String convert(Path input) {
        String result = null;
        try {
            String svgInputLocation = input.toAbsolutePath().toString();
            TranscoderInput transcoderInput = new TranscoderInput(svgInputLocation);

            String pngOutputLocation = svgInputLocation.replace(".svg", ".png");
            OutputStream outputStream = new FileOutputStream(pngOutputLocation);
            TranscoderOutput transcoderOutput = new TranscoderOutput(outputStream);

            PNGTranscoder pngTranscoder = new PNGTranscoder();
            pngTranscoder.transcode(transcoderInput, transcoderOutput);

            outputStream.flush();
            outputStream.close();

            result = pngOutputLocation;
        } catch (IOException | TranscoderException ex) {
            System.out.println("Exception Thrown: " + ex);
        }
        return result;
    }
}
