from mrjob.job import MRJob

class LengthCounts(MRJob):
    import mrjob
    OUTPUT_PROTOCOL = mrjob.protocol.JSONValueProtocol
    
    def mapper(self, _, line):
        import os
        file_name = os.getenv('mapreduce_map_input_file') #Gives the current file
        for x in line.split(): #Splitting the line into words
            yield file_name, len(x) #Yielding the filename and the length of each word
    
    def combiner(self, key, values):
        dicti = {}
        #Goes through the lengths and increments the cound by 1 for the specific length
        for x in values: 
            dicti.setdefault(x, 0)
            dicti[x] += 1
        yield key, dicti #yields the filename and dict of length counts
    
    def reducer(self, key, values):
        complete_dict = {"file": key} 
        for x in values: #Goes through the dicts and sums the count of each length into the complete_dict
            for y in x:
                complete_dict.setdefault(y, 0)
                complete_dict[y] += x[y]
        
        #import json
        #yield None, json.dumps(complete_dict)
        
        '''I tried creating a JSON object using json.dumps but it added extra \ to the output as it seems to
           double encode the dictionary. MRJobs uses json as default output'''
        yield None, complete_dict
        
if __name__ == '__main__':
    LengthCounts.run()