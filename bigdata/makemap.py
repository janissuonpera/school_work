import math
from PIL import Image
import urllib
import io

def deg2num(lat_deg, lon_deg, zoom):
  lat_rad = math.radians(lat_deg)
  n = 2.0 ** zoom
  xtile = int((lon_deg + 180.0) / 360.0 * n)
  ytile = int((1.0 - math.log(math.tan(lat_rad) + (1 / math.cos(lat_rad))) / math.pi) / 2.0 * n)
  return (xtile, ytile)



import sys
ul_x, ul_y = deg2num(float(sys.argv[1]), float(sys.argv[2]), int(sys.argv[5]))
lr_x, lr_y = deg2num(float(sys.argv[3]), float(sys.argv[4]), int(sys.argv[5]))


images=[]
for y in range(ul_y, lr_y+1):
    row = []
    for x in range(ul_x, lr_x+1):
        image = Image.open(io.BytesIO(urllib.request.urlopen(f"https://tile.openstreetmap.org/{sys.argv[5]}/{x}/{y}.png").read()))
        row.append(image)
    images.append(row)
    

width, height = images[0][0].size

completeMap = Image.new("RGB", ((lr_x - ul_x)*width, len(images)*height))

x = 0
y = 0
print()
for row in range(len(images)):
    for col in range(len(images[row])):
        completeMap.paste(images[row][col], (x, y))
        x += width
    x = 0
    y += height

completeMap.save(sys.argv[6])