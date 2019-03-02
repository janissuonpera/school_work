import sys
keepgoing = True
#cipher = sys.argv[1]

while(keepgoing):
  command = input("Give a command: ")
  if(command[:9] == "encrypt "):
    print("hei")
  elif(command[:9] == "decrypt "):
    print("hei")
  elif(command[:5] == "quit "):
    keepgoing = False
