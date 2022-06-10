import numpy as np
from PIL import Image

img = np.zeros([400,300,3], dtype=np.uint8)
img.fill(255) # or img[:] = 255
im = Image.fromarray(img) #convert numpy array to image
im.save('/opt/work/workspaces/idea/master-thesis/usecase-03/target/empty.png')

#improting the module
import qrcode


qr = qrcode.QRCode(
    version=12,
    error_correction=qrcode.constants.ERROR_CORRECT_H,
    box_size=4,
    border=8
)
qr.add_data('test text')
qr.make()
img = qr.make_image(fill_color="red", back_color="yellow")
img.save('/opt/work/workspaces/idea/master-thesis/usecase-03/target/qrcode.png')

# -----------------------------------------------------------------------
img_bg = Image.open('/opt/work/workspaces/idea/master-thesis/usecase-03/target/qrcode.png')

qr = qrcode.QRCode(box_size=2)
qr.add_data('I am Lena')
qr.make()
img_qr = qr.make_image()

pos = (img_bg.size[0] - img_qr.size[0], img_bg.size[1] - img_qr.size[1])

img_bg.paste(img_qr, pos)
img_bg.save('/opt/work/workspaces/idea/master-thesis/usecase-03/target/qr_lena.png')

# -----------------------------------------------------------------------



# importing EAN13 from the python-barcode module
from barcode import EAN13

# add any 12 digit number you would like to
number = '5909876123457'

# Now, let's create an object of EAN13
# make a class and pass the variable number created above
my_code = EAN13(number)

# save it
my_code.save('/opt/work/workspaces/idea/master-thesis/usecase-03/target/bar_code')