import os
import sys
import unittest
import java
from PIL import Image, ImageChops
from simple_file_checksum import get_checksum

os.chdir("../../..")
testResourcesPath = 'src/test/resources/'
pyScriptsPath = 'src/main/python/'
sys.path.insert(0, os.path.abspath(pyScriptsPath))

import image_utils

class ImageUtilsTestCase(unittest.TestCase):
    def test_final_image_generation_is_successful(self):
        empty_img_path = image_utils.create_empty_image()
        qrcode_img_path = image_utils.create_qrcode_image("lorem ipsum dolor sit amet", "#A5FF00", "#5A5A5A")
        barcode_svg_img_path = image_utils.create_barcode_image("5909876183457")
        image_processor_class = java.type("com.yast.masterthesis.usecase03.ImageProcessor")
        barcode_png_img_path = image_processor_class.convertSvgToPng(os.path.abspath(barcode_svg_img_path))
        image_utils.rotate(barcode_png_img_path)
        out_img_path = image_utils.merge(empty_img_path, qrcode_img_path, 50, 50)
        out_img_path = image_utils.merge(out_img_path, barcode_png_img_path, 700, -60)
        image_utils.add_text(out_img_path, "Automatically generated!", 450, 230);
        image_utils.add_watermark(out_img_path, testResourcesPath + "stamp.png");
        test_img_path = testResourcesPath + "test_final_image.png"
        test_img = Image.open(test_img_path)
        out_img = Image.open(out_img_path)
        diff = ImageChops.difference(out_img, test_img)
        self.assertEqual(diff.getbbox(), None)
        checksum1 = get_checksum(out_img_path)
        checksum2 = get_checksum(test_img_path)
        self.assertEqual(checksum1, checksum2)