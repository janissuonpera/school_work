x_cell ="       \n x   x \n  x x  \n   x   \n  x x  \n x   x \n       "
o_cell="       \n  ooo  \n o   o \n o   o \n o   o \n  ooo  "
empty_cell = "       \n" * 6 + "       "

grid = [[x_cell, x_cell, x_cell], [x_cell, x_cell, x_cell], [x_cell, x_cell, x_cell]]

string_grid = ""
for x in range(len(grid)):
    for y in range(3):
        print(grid[x][y], end="")
        print("hei", end="")