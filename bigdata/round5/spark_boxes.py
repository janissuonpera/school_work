from pyspark import SparkConf, SparkContext

conf = SparkConf().setAppName("busspeeds_js")
sc = SparkContext(conf=conf)

start_data = sc.textFile("/data/busdata/*.csv")
header = start_data.first()

#Function I used to filter out some of the differently formatted csv files that
#caused my script to crash
def isFloat(data):
    try:
        float(data)
        return True
    except:
        return False

#Filter out header and lines that contain differently formatted csv that causes my script to crash
complete_data = start_data.filter(lambda line: line != header and len(line.split(";"))==8 and isFloat(line.split(";")[7])==True)

#Map into pairs of form: (line, speed)
lines_speeds = complete_data.map(lambda line: (line.split(";")[2], float(line.split(";")[7])))

#Group the pairs by key, which is the busline
grouped_data = lines_speeds.groupByKey().mapValues(list)


import matplotlib as mpl
mpl.use('Agg')
import matplotlib

#This functions calls boxplot_stats function to get stats for the busline speeds
def getStats(data):
    line = data[0]
    stat = matplotlib.cbook.boxplot_stats(data[1])
    return (line, stat)

stats_data = grouped_data.map(getStats).collect()

#Collects all the data into a single list of dicts of stats
complete = []
for x in stats_data:
    tmp = {"label": x[0]}
    for key in x[1][0].keys():
        tmp[key] = x[1][0][key]
    complete.append(tmp)

import matplotlib.pyplot as plt

fig, axes = plt.subplots(figsize=(20, 10))
axes.bxp(complete)
fig.savefig("speedboxes.png")
