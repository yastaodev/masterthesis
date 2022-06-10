from PIL import Image

img_bg = Image.open('/opt/work/workspaces/idea/master-thesis/usecase-03/target/qr_lena.png')
img_fg = Image.open('/opt/work/workspaces/idea/master-thesis/usecase-03/target/bar_code.png')
img_fg = img_fg.rotate(90, expand=True)

pos = (700,-2)

img_bg.paste(img_fg, pos)
img_bg.save('/opt/work/workspaces/idea/master-thesis/usecase-03/target/prefinal.png')
