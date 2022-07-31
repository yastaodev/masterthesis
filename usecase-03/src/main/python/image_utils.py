import polyglot
import numpy as np
import qrcode
from PIL import Image, ImageDraw, ImageFont
from barcode import EAN13


@polyglot.export_value
def create_empty_image():
    img_array = np.zeros([475, 800, 3], dtype=np.uint8)
    img_array.fill(255)  # or img[:] = 255
    img = Image.fromarray(img_array)  # convert numpy array to image
    out = 'target/empty.png'
    img.save(out)
    print("Python :: An empty image has been created.")
    return out


@polyglot.export_value
def create_qrcode_image(text, bg_color, fg_color):
    qr = qrcode.QRCode(
        version=12,
        error_correction=qrcode.constants.ERROR_CORRECT_H,
        box_size=5,
        border=5
    )
    qr.add_data(text)
    qr.make()
    img = qr.make_image(fill_color=fg_color, back_color=bg_color) #fill_color="#A5FF00", back_color="#5A5A5A")
    out = 'target/qrcode.png'
    img.save(out)
    print("Python :: QR Code has been created.")
    return out


@polyglot.export_value
def create_barcode_image(number):
    my_code = EAN13(number)
    out = 'target/barcode'
    my_code.save(out, {"module_width":1, "module_height":10, "font_size": 18, "text_distance": 10, "quiet_zone": 20})
    print("Python :: Barcode has been created.")
    return out + '.svg'


@polyglot.export_value
def rotate(img_path):
    img = Image.open(img_path)
    rotated_img = img.rotate(90, expand=True)
    rotated_img.save(img_path)
    print("Python :: Image has been rotated.")


@polyglot.export_value
def merge(bg_img_path, fg_img_path, pos_x, pos_y):
    bg_img = Image.open(bg_img_path)
    fg_img = Image.open(fg_img_path)
    pos = (pos_x, pos_y)
    bg_img.paste(fg_img, pos)
    out = 'target/out.png'
    bg_img.save(out)
    print("Python :: Images have been merged.")
    return out


@polyglot.export_value
def add_text(img_path, text, pos_x, pos_y):
    img = Image.open(img_path)
    draw = ImageDraw.Draw(img)
    font = ImageFont.truetype("Ubuntu-R.ttf", 20)
    draw.text((pos_x, pos_y), text, (0, 0, 0), font=font)
    img.save(img_path)
    print("Python :: Text has been added.")


@polyglot.export_value
def add_watermark(input_image_path, watermark_image_path):
    base_image = Image.open(input_image_path)
    watermark = Image.open(watermark_image_path).convert("RGBA")
    position = base_image.size
    new_size = (int(position[0] * 8 / 100), int(position[0] * 8 / 100))
    watermark = watermark.resize(new_size)
    new_position = position[0] - new_size[0] - 20, position[1] - new_size[1] - 20
    transparent = Image.new(mode='RGBA', size=position, color=(0, 0, 0, 0))
    transparent.paste(base_image, (0, 0))
    transparent.paste(watermark, new_position, watermark)
    image_mode = base_image.mode
    if image_mode == 'RGB':
        transparent = transparent.convert(image_mode)
    else:
        transparent = transparent.convert('P')
    transparent.save(input_image_path, optimize=True, quality=100)
    print("Python :: Watermark has been added.")