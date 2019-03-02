keepgoing = True
usdeur = 0
eurusd = 0

commands = ["USD x EUR", "EUR x USD", "x USD", "x EUR", "quit"]

while(keepgoing):
  try:
    command = input("Give a command: ")
    command.strip()
    command = command.split(" ")
    if len(command)==3:
      if((command[0] + " x " + command[2]).casefold() == "USD x EUR".casefold()):
        usdeur = float(command[1])
      elif((command[0] + " x " + command[2]).casefold() == "EUR x USD".casefold()):
        eurusd = float(command[1])
      else:
        print("Illegal command!")
    elif(len(command)==2):
      if(("x " + command[1]).casefold() == "x USD".casefold()):
        if(usdeur == 0):
          print("No USD-EUR rate defined yet!")
        else:
          euros = float(command[0]) * usdeur
          print("{} USD = {} EUR".format(float(command[0]), euros))
      elif(("x " + command[1]).casefold() == "x EUR".casefold()):
        if(eurusd == 0):
          print("No EUR-USD rate defined yet!")
        else:
          dollars = float(command[0]) * eurusd
          print("{} EUR = {} USD".format(float(command[0]), dollars))
      else:
        print("Illegal command!")
    elif(len(command)==1):
      if(command[0].casefold() == "quit"):
        keepgoing = False
      else:
        print("Illegal command!")
    else:
      print("Illegal command!")
  except:
    print("Illegal command!")
