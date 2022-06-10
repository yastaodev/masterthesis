from PIL import Image

img_bg = Image.open('/opt/work/workspaces/idea/master-thesis/usecase-03/target/qr_lena.png')
img_fg = Image.open('/opt/work/workspaces/idea/master-thesis/usecase-03/target/bar_code.png')

pos = (img_bg.size[0] - img_fg.size[0], img_bg.size[1] - img_fg.size[1])

img_bg.paste(img_fg, pos)
img_bg.save('/opt/work/workspaces/idea/master-thesis/usecase-03/target/prefinal.png')
