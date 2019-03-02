class Point2D:
  def __init__(self, x=0, y=0):
    self.x, self.y = x, y
    
  def __str__(self):
    return f"Point2D(x={round(self.x, 1)},y={round(self.y, 1)})"

p1 = Point2D(2.5123, 1.2541233)
print(p1)
