
r = int(input())
c = int(input())
h = int(input())
w = int(input())


for z in range(1, r+1):
	for x in range(1, h+1):
		for c in range(1, c+1):
			for v in range(1, w+1):
				if(z%2 != 0):
					if(c%2 != 0):
						print(" ", end="")
					else:
						print("#", end="")
				else:
					if(c%2 == 0):
						print(" ", end="")
					else:
						print("#", end="")	
		print()
