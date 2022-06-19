from PIL import Image
from PIL import ImageFont
from PIL import ImageDraw

img = Image.open("/opt/work/workspaces/idea/master-thesis/usecase-03/target/final.png")
draw = ImageDraw.Draw(img)
font = ImageFont.truetype("Ubuntu-R.ttf", 20)
draw.text((470, 230),"Automatically generated!",(0,0,0),font=font)
img.save('/opt/work/workspaces/idea/master-thesis/usecase-03/target/final-final.png')
