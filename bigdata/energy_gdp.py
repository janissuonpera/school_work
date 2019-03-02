import xml.etree.ElementTree as et
from urllib.request import urlopen

energy_page_data = []
gdp_page_data = []

with urlopen("http://api.worldbank.org/countries/fin/indicators/EG.USE.PCAP.KG.OE") as response1:
    energy_page_data.append(et.parse(response1))
    root1 = energy_page_data[0].getroot()
    pages = root1.get("pages")
    
    for x in range(1, int(pages)):
        with urlopen(f"http://api.worldbank.org/countries/fin/indicators/EG.USE.PCAP.KG.OE?page={x+1}") as response2:
            energy_page_data.append(et.parse(response2))
  
    
with urlopen("http://api.worldbank.org/countries/fin/indicators/NY.GDP.PCAP.CD") as response1:
    gdp_page_data.append(et.parse(response1))
    root1 = gdp_page_data[0].getroot()
    pages = root1.get("pages")
    
    for x in range(1, int(pages)):
        with urlopen(f"http://api.worldbank.org/countries/fin/indicators/NY.GDP.PCAP.CD?page={x+1}") as response2:
            gdp_page_data.append(et.parse(response2))
            

energy = {}
gdp = {}
ns = {"wb" : "http://www.worldbank.org"}

for page in energy_page_data:
    for child in page.getroot():
        date = int(child.find('wb:date', ns).text)
        value = child.find('wb:value', ns).text
        if value != None:
            value = float(value)
        else:
            value = 0
        energy[date] = value

completevalues = []

for page in gdp_page_data:
    for child in page.getroot():
        date = int(child.find('wb:date', ns).text)
        value = child.find('wb:value', ns).text
        if value != None:
            value = float(value)
        else:
            value = 0
        completevalues.append({"date": date, "energy":energy[date], "gdp":value})

completevalues.sort(key=lambda a: a["date"])


import matplotlib.pyplot as plt
years = []
energy = []
gdp = []
for x in range(len(completevalues)):
    years.append(completevalues[x]["date"])
    energy.append(completevalues[x]["energy"])
    gdp.append(completevalues[x]["gdp"])


fig, ax1 = plt.subplots()
color = 'tab:red'
ax1.set_xlabel('Year')
ax1.set_ylabel("Energy", color=color)
ax1.plot(years, energy, color=color, label='Energy')

ax2 = ax1.twinx()
color = 'tab:blue'
ax2.set_ylabel("GDP", color=color)
ax2.plot(years, gdp, color=color, label='GDP')
fig.legend()
fig.savefig("energy_vs_gdp.pdf")