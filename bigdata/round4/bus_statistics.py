from mrjob.job import MRJob
import re
import numpy as np

pattern = re.compile(r'2015\-03\-25T(\d\d)\:\d\d\:\d\d\.') #Probably overkill but a pattern for finding the time

class BusStatsAnalyzer(MRJob):
    import mrjob 
    #Getting the output in csv was probably the hardest part so hopefully this is correct
    OUTPUT_PROTOCOL = mrjob.protocol.JSONValueProtocol 
    
    #Mapper function which finds the bus line, time and speed from the current line
    def mapper(self, _, line):
        line = line.split(",")
        bus_line = line[1]
        
        #Couldnt figure out how to handle the csv header so I just used a try-except-clause since it always raised
        #an exception. If you had a better way to handle it, could u tell me in the review?
        try:
            time = int(re.search(pattern, line[0]).group(1))
            speed = float(line[-3])        
            yield (bus_line, time), speed
        except:
            pass
    
    #Reducer function which counts the min speed, max speed, mean speed and variance of the bus
    def reducer(self, key, values):
        val_list = []
        for x in values:
            val_list.append(x)
        min_speed = min(val_list)
        max_speed = max(val_list)
        mean = sum(val_list)/len(val_list)
        variance = np.var(val_list)
        
        boi = f'{key[0]};{key[1]}:00-{key[1]+1}:00;{min_speed};{max_speed};{mean};{variance}'
        yield None, boi

if __name__ == '__main__':
    BusStatsAnalyzer.run()