

def check_vals(sp, dp):
	
	if(sp>180 or dp>120):
		print("Hypertensive crisis (consult your doctor immediately)")
	elif(sp<90 or dp<60):
		print("Low")
	elif(sp>140 or dp>=90):
		print("High (hypertension) stage 2")
	elif(sp>129 or dp>79):
		print("High (hypertension) stage 1")
	elif(sp>119 and dp<80):
		print("Elevated")
	elif(sp>89 and dp<80):
		print("Normal")



exit = False

while(exit==False):
	sp_acceptable = False
	dp_acceptable = False

	while(sp_acceptable==False):
		sp = input("Enter systolic pressure (or \"quit\" to stop): ")
		if(sp == "quit"):
			print("Quit-command received. Stopping the program.")
			exit = True
			sp_acceptable = True
		else:
			try:
				sp = int(sp)
				if(sp<0):
					print("Enter a positive integer!")
				else:
					sp_acceptable = True
			except ValueError:
				print("Enter a positive integer!")


	while(dp_acceptable==False and exit==False):
		dp = input("Enter diastolic pressure (or \"quit\" to stop): ")
		if(dp == "quit"):
			print("Quit-command received. Stopping the program.")
			exit = True
			dp_acceptable = True

		else:
			try:
				dp = int(dp)
				if(dp<0):
					print("Enter a positive integer!")
				else:
					dp_acceptable = True
					check_vals(sp, dp)
			except ValueError:
				print("Enter a positive integer!")
		

	
