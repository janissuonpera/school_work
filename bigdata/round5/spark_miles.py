from pyspark import SparkConf, SparkContext

conf = SparkConf().setAppName("mileages_js")
sc = SparkContext(conf=conf)

data = sc.textFile("/data/vehicles/vehsample.csv")

header = "vehicleClass;registrationDate;vehicleGroup;usageType;\
variant;version;deploymentDate;colour;numDoors;chassisType;cabinType;\
numSeats;weight;vehMaxLoad;regMaxLoad;length;width;height;fuelType;\
engineCapacity;power(kW);numCylinders;turbo;hybrid;make;model;transmission;\
numGears;name;brakeType;modelRegNumber;euroFuelType;municipality;co2;\
mileage(km);area;serialNumber;lineNumber"

make_index = header.split(";").index("make")
year_index = header.split(";").index("deploymentDate")
mileage_index = header.split(";").index("mileage(km)")

#Removes header from the dataset
no_header = data.filter(lambda line: line!=header)

#Makes a dataset of only the makes, groups them by the key and counts how many there are of each
makes = no_header.map(lambda line: (line.split(";")[make_index], 1)).groupByKey()\
                                                                    .map(lambda p: (p[0], sum(p[1])))
#Gives the 5 most popular car makes
top5makes = makes.takeOrdered(5, key=lambda p: -p[1])
top5makes = [x[0] for x in top5makes]

#Checks if the year data is a correct type
def filterYear(data):
    try:
        return int(data)
    except:
        return False
    
#Filter out makes that are not on top 5 list and older than 1980 and that mileage is not missing
filtered_data = no_header.filter(lambda line: line.split(";")[make_index] in top5makes and\
                                 1980 <= filterYear(line.split(";")[year_index].split("-")[0]) <= 2018\
                                 and line.split(";")[mileage_index] != "")


date_make = filtered_data.map(lambda line: ((line.split(";")[year_index].split("-")[0],line.split(";")[make_index]),\
                                            float(line.split(";")[mileage_index])))

date_make = date_make.groupByKey().map(lambda p: (p[0], sum(p[1])))

#This function returns a dict of form: {"year": x, "make": y, "mileage": z}
def turnDict(data):
    dicti = {"year": int(data[0])}
    for x in data[1]:
        dicti[x[0]] = x[1]
    return dicti
    
#Maps the data into form: (year, (make, mileage))
final = date_make.map(lambda pair: (pair[0][0], (pair[0][1], pair[1]))).groupByKey().sortByKey()

final = final.map(turnDict)

import pandas as pd
df = pd.DataFrame(final.collect())
df = df.set_index("year")

import matplotlib.pyplot as plt
fig = df.plot(figsize=(15, 10))
plt.ticklabel_format(style='plain')

plt.savefig("miles.png")








