package com.yast.masterthesis.usecase03;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {
    @Test
    public void testFinalImageGenerationIsSuccessful() throws NoSuchAlgorithmException, IOException {
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
        imageProcessor.addWatermark(outImgPath, "stamp.png");

        MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
        String checksum1 = checksum(outImgPath, shaDigest);
        String checksum2 = checksum("src/test/resources/test_final_image.png", shaDigest);
        assertEquals(checksum1, checksum2);
    }

    private static String checksum(String filepath, MessageDigest messageDigest) throws IOException {
        try (DigestInputStream dis = new DigestInputStream(new FileInputStream(filepath), messageDigest)) {
            while (dis.read() != -1) ;
            messageDigest = dis.getMessageDigest();
        }
        StringBuilder result = new StringBuilder();
        for (byte b : messageDigest.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
