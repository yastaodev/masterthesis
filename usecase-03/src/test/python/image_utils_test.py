import sys
import os
import unittest
import numpy as np
import java
import polyglot
from pathlib import Path
from simple_file_checksum import get_checksum
from PIL import Image, ImageChops

os.chdir(Path().absolute())
if not os.getcwd().endswith('usecase-03'):
    os.chdir("../../..")
mainResourcesPath = 'src/main/resources/'
testResourcesPath = 'src/test/resources/'
pyScriptsPath = 'src/main/python/'
sys.path.insert(0, os.path.abspath(pyScriptsPath))
#sys.path.append('/home/yastao/.local/lib/python3.6/site-packages/')
#sys.path.append('/usr/lib/python3/dist-packages')

import image_utils

class ImageUtilsTestCase(unittest.TestCase):
    @staticmethod
    @polyglot.export_value
    def constructor():
        return ImageUtilsTestCase()


    @polyglot.export_value
    def test_create_empty_image(self):
        img_array = np.zeros([475, 800, 3], dtype=np.uint8)
        img_array.fill(255)  # or img[:] = 255
        img1 = Image.fromarray(img_array)  # convert numpy array to image
        out_img_path = image_utils.create_empty_image()
        img2 = Image.open(out_img_path)
        diff = ImageChops.difference(img1, img2)
        self.assertEqual(diff.getbbox(), None)
        checksum1 = get_checksum(out_img_path)
        checksum2 = get_checksum(testResourcesPath + "test_create_empty_image.png")
        self.assertEqual(checksum1, checksum2)

    def test_create_qrcode_image(self):
        out_img_path = image_utils.create_qrcode_image("lorem ipsum dolor sit amet", "#FFFFFF", "#000000")
        image_processor_class = java.type("com.yast.masterthesis.usecase03.ImageProcessor")
        out_qrcode_val = image_processor_class.decode(out_img_path)
        test_qrcode_val = image_processor_class.decode(testResourcesPath + "test_create_qrcode_image.png")
        self.assertEqual(out_qrcode_val, test_qrcode_val)


    def test_create_barcode_image(self):
        out_img_path = image_utils.create_barcode_image("5909876183457")
        image_processor_class = java.type("com.yast.masterthesis.usecase03.ImageProcessor")
        out_img_path = image_processor_class.convertSvgToPng(os.path.abspath(out_img_path))
        out_barcode_val = image_processor_class.decode(out_img_path)
        test_barcode_val = image_processor_class.decode(testResourcesPath + "test_create_barcode_image.png")
        self.assertEqual(out_barcode_val, test_barcode_val)


    def test_rotate_image(self):
        test_img_path = testResourcesPath + "test_rotate_image.png"
        img = Image.open(test_img_path)
        w1, h1 = img.size
        image_utils.rotate(test_img_path)
        img = Image.open(test_img_path)
        w2, h2 = img.size
        self.assertEqual(w1, h2)
        self.assertEqual(w2, h1)


    def test_merge_image(self):
        bg_img_path = image_utils.create_empty_image()
        fg_img_path = testResourcesPath + "test_create_barcode_image.png"
        merged_img_path = image_utils.merge(bg_img_path, fg_img_path, 0, 0)
        test_img_path = testResourcesPath + "test_merge_image.png"
        merged_img = Image.open(merged_img_path)
        test_img = Image.open(test_img_path)
        diff = ImageChops.difference(merged_img, test_img)
        self.assertEqual(diff.getbbox(), None)
        checksum1 = get_checksum(merged_img_path)
        checksum2 = get_checksum(test_img_path)
        self.assertEqual(checksum1, checksum2)


    def test_add_text(self):
        bg_img_path = image_utils.create_empty_image()
        image_utils.add_text(bg_img_path, "lorem ipsum dolor sit amet", 200, 200)
        bg_img = Image.open(bg_img_path)
        test_img_path = testResourcesPath + "test_add_text.png"
        test_img = Image.open(test_img_path)
        diff = ImageChops.difference(bg_img, test_img)
        self.assertEqual(diff.getbbox(), None)
        checksum1 = get_checksum(bg_img_path)
        checksum2 = get_checksum(test_img_path)
        self.assertEqual(checksum1, checksum2)


    def test_add_watermark(self):
        out_img_path = image_utils.create_empty_image()
        watermark_img_path = mainResourcesPath + "stamp.png"
        image_utils.add_watermark(out_img_path, watermark_img_path)
        out_img = Image.open(out_img_path)
        test_img_path = testResourcesPath + "test_add_watermark.png"
        test_img = Image.open(test_img_path)
        diff = ImageChops.difference(out_img, test_img)
        self.assertEqual(diff.getbbox(), None)
        checksum1 = get_checksum(out_img_path)
        checksum2 = get_checksum(test_img_path)
        self.assertEqual(checksum1, checksum2)

#if __name__ == '__main__':
    #unittest.main()
