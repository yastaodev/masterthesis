import numpy as np
from PIL import Image
import qrcode

img = np.zeros([475,800,3], dtype=np.uint8)
img.fill(255) # or img[:] = 255
im = Image.fromarray(img) # convert numpy array to image
im.save('/opt/work/workspaces/idea/master-thesis/usecase-03/target/empty.png')



qr = qrcode.QRCode(
    version=12,
    error_correction=qrcode.constants.ERROR_CORRECT_H,
    box_size=5,
    border=5
)
qr.add_data('test text')
qr.make()
img = qr.make_image(fill_color="#A5FF00", back_color="#5A5A5A")
img.save('/opt/work/workspaces/idea/master-thesis/usecase-03/target/qrcode.png')

# -----------------------------------------------------------------------
img_bg = Image.open('/opt/work/workspaces/idea/master-thesis/usecase-03/target/empty.png')
img_hu = Image.open('/opt/work/workspaces/idea/master-thesis/usecase-03/target/qrcode.png')

pos2 = (50, 50)
img_bg.paste(img_hu, pos2)

img_bg.save('/opt/work/workspaces/idea/master-thesis/usecase-03/target/qr_lena.png')

# -----------------------------------------------------------------------



# importing EAN13 from the python-barcode module
from barcode import EAN13

# add any 12 digit number you would like to
number = '5909876183457'

# Now, let's create an object of EAN13
# make a class and pass the variable number created above
my_code = EAN13(number)

# save it
my_code.save('/opt/work/workspaces/idea/master-thesis/usecase-03/target/bar_code')