import sys
import os
import unittest
import numpy as np
import java

os.chdir("../../..")
mainResourcesPath = 'src/main/resources'
testResourcesPath = 'src/test/resources/'
sys.path.insert(0, mainResourcesPath)
#sys.path.append('/home/yastao/.local/lib/python3.6/site-packages/')
#sys.path.append('/usr/lib/python3/dist-packages')

import image_utils
from simple_file_checksum import get_checksum
from PIL import Image, ImageChops

class ImageUtilsTestCase(unittest.TestCase):
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
        img_path = testResourcesPath + "test_rotate_image.png"
        img = Image.open(img_path)
        w1, h1 = img.size
        image_utils.rotate(img_path)
        img = Image.open(img_path)
        w2, h2 = img.size
        self.assertEqual(w1, h2)
        self.assertEqual(w2, h1)

if __name__ == '__main__':
    unittest.main()
