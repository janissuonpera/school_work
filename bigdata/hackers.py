
data = []
with open("logins.txt") as file:
    for line in file.readlines():
        if "Tue Oct  2" in line:
            data.append(line)


checked_ips = []
times = []
import re
import json
import time
from urllib.request import urlopen
for line in data:
    ip = re.search(r"\d+\.\d+\.\d+\.\d+", line).group()
    start = re.search(r"\d{2}\:\d{2}", line).group()
    print(ip)
    if any(ip in x for x in checked_ips) == False:
        try:
            with urlopen(f"https://www.iplocate.io/api/lookup/{ip}") as request:
                response = json.load(request)
                country = response["country"]
                ip_and_country = (ip, country)
                checked_ips.append(ip_and_country)
                times.append(start)
        except:
            print(ip, "location not found.")
        time.sleep(1)

for x in checked_ips:
    print(x)

countries = [x[1] for x in checked_ips]
rdy_list = []
for x in range(len(countries)):
	if any(countries[x] in z for z in rdy_list) == False:
		rdy_list.append([countries[x], countries.count(countries[x])])
rdy_list.sort(key=lambda a: a[1], reverse=True)

num_of_countries = len(rdy_list)
complete=[]

if(num_of_countries>10):
    for x in rdy_list:
        complete.append(x)
        if(len(complete)==num_of_countries or len(complete)==10):
            break

complete.sort(key=lambda a: a[1], reverse=True)
for x in complete:
    print(x)
    
import matplotlib.pyplot as plt
labels = [x[0] for x in complete]
values = [x[1] for x in complete]


plt.figure(1)
plt.pie(values,labels=labels, autopct=lambda a: '{:.0f}'.format(a * sum(values) / 100), shadow=True, startangle=90)
plt.axis('equal')
plt.savefig("hackpie.png")

hours = [0] * 24
for x in times:
    x = int(x.split(":")[0])
    hours[x] = hours[x] + 1
    
plt.figure(2)
plt.bar(list(range(24)), hours)
plt.ylabel("Number of attemps")
plt.xlabel("Time of day")
plt.title("Number of hacking attemps during a day")
plt.savefig("hackbar.png")
plt.show()



















