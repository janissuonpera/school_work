from pyspark import SparkContext, SparkConf

conf = SparkConf().setAppName("authors_js")
sc = SparkContext(conf=conf)

books = sc.textFile("/data/books/BX-Books_10k.csv")

header = books.first()

with open("kirjatesti.txt", 'w') as file:
    file.write(header)