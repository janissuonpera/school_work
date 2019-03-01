from pyspark import SparkContext, SparkConf


conf = SparkConf().setAppName("lengthcounts_js")
sc = SparkContext(conf=conf)

#Creates value pairs of form: ("americana.txt", len(word)) for every word in each americana.txt
textfiles = sc.wholeTextFiles("/data/americana/").flatMap(lambda pair: [(str(pair[0]).split("/")[-1], len(x)) for x in pair[1].split()])

#Creates value pairs of form: ("americana.txt", (length, count))
counts = textfiles.map(lambda word: (word, 1)).groupByKey().map(lambda p: (p[0][0], (p[0][1], sum(p[1])))).groupByKey().mapValues(list).sortByKey(ascending=True)


def toJSONLine(data):
    obj = {"file": data[0]}
    for x in data[1]:
        obj[x[0]] = x[1]
    return obj

json_objects = counts.map(toJSONLine).collect()

with open("lencounts.json", 'w') as outfile:
    import json
    json.dump(json_objects, outfile)


