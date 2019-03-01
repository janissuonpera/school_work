import pandas as pd

df = pd.read_csv("Ajoneuvojen avoin data 5.3.csv", sep=";", encoding="Latin")


df = df[(df["ajoneuvoluokka"]=="M1") | (df["ajoneuvoluokka"]=="M1G")] #Choosing only vehicles that have a vehicle class of "M1" or "M1G"
df = df.dropna(subset=['ensirekisterointipvm', 'Co2']) #Removing all rows that have invalid values in either of those columns

new_df = df.groupby(df['ensirekisterointipvm'].map(lambda x: int(x[:4])))['Co2'].agg(['mean', 'count']) #New df with year as index and mean and count columns

print(new_df)

years = new_df.reset_index()['ensirekisterointipvm'].tolist()
co2 = new_df['mean'].tolist()

#for x in range(2019, 2031):
#	years.append(x)
    
#for x in range(2019, 2031):
 #   co2.append(None)


import matplotlib.pyplot as plt
from scipy import stats
import numpy as np
from scipy.optimize import curve_fit

def function(x, a, b, c):
  return a * np.exp(-b * x) + c


slope, intercept, r_value, p_value, std_err = stats.linregress(years, co2)


plt.scatter(years, co2)
plt.plot(years,np.poly1d([slope,intercept])(years), 'r', label='fitted line')
plt.legend(["linear fit"])

#p3 = np.poly1d(np.polyfit(years, co2, 3))

#plt.plot(years, p3(years), label='polynomial fit')
#plt.legend(["linear fit", "polynomial fit"])

years2 = list(range(0, len(years)))
print(years2)
popt, pcov = curve_fit(function, years2, co2, maxfev = 1600)

plt.plot(years, function(years, *popt))
plt.legend(["linear fit", "polynomial fit", 'exponential fit'])

plt.show()




