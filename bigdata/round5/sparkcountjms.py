from pyspark import SparkContext, SparkConf

conf = SparkConf().setAppName("wordcount_js")
sc = SparkContext(conf=conf)



import re
text = sc.textFile("/data/books/*.txt").flatMap(lambda line: [re.sub(r'\W', "", x.lower()) for x in line.split()])

counts = text.map(lambda word: (word, 1)).groupByKey().map(lambda p: (p[0], sum(p[1])))

counts.saveAsTextFile('/user/group18/wordcountjms_spark')

