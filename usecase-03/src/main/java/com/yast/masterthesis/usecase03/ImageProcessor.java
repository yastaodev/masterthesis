package com.yast.masterthesis.usecase03;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class ImageProcessor {
    private static final String PYTHON = "python";
    private static final String VENV_EXECUTABLE = Paths.get("venv", "bin", "graalpython").toString();
    private Context context;

    public ImageProcessor() {
        context = buildPythonContext(VENV_EXECUTABLE);
        context.eval(PYTHON, "import site");
        context.eval(PYTHON, "import sys; print(sys.version)");
        context.eval(PYTHON, "import sys; print(sys.executable)");
        evalSource(context, "image_utils.py");
    }

    public String createEmptyImage() {
        Value funcCreateEmptyImage = context.getPolyglotBindings().getMember("create_empty_image");
        String emptyImgPath = funcCreateEmptyImage.execute().asString();
        return emptyImgPath;
    }

    public String createQrcodeImage(String input, String backgroundColor, String foregroundColor) {
        Value funcCreateQrcodeImage = context.getPolyglotBindings().getMember("create_qrcode_image");
        String qrcodeImgPath = funcCreateQrcodeImage.execute(input, backgroundColor, foregroundColor).asString();
        return qrcodeImgPath;
    }

    public String createBarcodeImage(String input) {
        Value funcCreateBarcodeImage = context.getPolyglotBindings().getMember("create_barcode_image");
        String barcodeSvgImgPath = funcCreateBarcodeImage.execute(input).asString();
        return barcodeSvgImgPath;
    }

    public void rotateImage(String imagePath) {
        Value funcRotate = context.getPolyglotBindings().getMember("rotate");
        funcRotate.execute(imagePath);
    }

    public String merge(String backgroundImagePath, String foregroundImagePath, int x, int y) {
        Value funcMerge = context.getPolyglotBindings().getMember("merge");
        String outImgPath = funcMerge.execute(backgroundImagePath, foregroundImagePath, x, y).asString();
        return outImgPath;
    }

    public void addText(String imagePath, String text, int x, int y) {
        Value funcAddText = context.getPolyglotBindings().getMember("add_text");
        funcAddText.execute(imagePath, text, x, y);
    }

    public void addWatermark(String imagePath, String resourceName) {
        Value funcAddWatermark = context.getPolyglotBindings().getMember("add_watermark");
        String watermarkPath = getResourceUrl(resourceName).getPath();
        funcAddWatermark.execute(imagePath, watermarkPath);
    }

    public static String convertSvgToPng(String input) {
        SvgToPngConverter svgToPngConverter = new SvgToPngConverter();
        System.out.println("Java :: Image has been converted from SVG to PNG.");
        return svgToPngConverter.convert(Paths.get(input));
    }

    public static String decode(String imageFilePath) {
        String text = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(imageFilePath));
            BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(bufferedImage);
            HybridBinarizer hybridBinarizer = new HybridBinarizer(bufferedImageLuminanceSource);
            BinaryBitmap binaryBitmap = new BinaryBitmap(hybridBinarizer);
            MultiFormatReader multiFormatReader = new MultiFormatReader();

            Result result = multiFormatReader.decode(binaryBitmap);
            text = result.getText();
        } catch (IOException | NotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Java :: Image has been decoded.");
        return text;
    }

    public void openImage(String imagePath) {
        try {
            System.out.println("Java :: Opening image with the OS default program...");
            Desktop.getDesktop().open(new File(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void evalSource(Context context, String pythonScript) {
        Source source;
        try {
            source = Source.newBuilder(PYTHON, getResourceUrl(pythonScript)).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        context.eval(source);
    }

    private Context buildPythonContext(String VENV_EXECUTABLE) {
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
