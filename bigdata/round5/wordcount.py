from mrjob.job import MRJob

class WordCounts(MRJob):
    
    def mapper(self, _, line):
        import re
        words = [re.sub(r'\W', "", word.lower()) for word in line.split()]
        for word in words:
            yield word, 1
    
    def combiner(self, key, values):
       yield key, sum(values)
    
    def reducer(self, key, values):
        yield key, sum(values)
    
    
    
    
if __name__ == '__main__':
    WordCounts.run()