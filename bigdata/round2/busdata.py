import sys

output_file = sys.argv[1]
interval = int(sys.argv[2])
duration = int(sys.argv[3])

from urllib.request import urlopen
import json
import csv
import re
import time

start_time = time.time()
current_time = 0.0

with open(output_file, 'w', newline='') as new_file:
    csv_writer = csv.writer(new_file, delimiter=';')
    csv_writer.writerow(["Date", "Time", "Line", "Vehicle", "Direction", "Latitude", "Longitude", "Speed"])
    
    while(current_time - start_time <= duration):
        print("Retrieving data..")
        with urlopen("http://data.itsfactory.fi/journeys/api/1/vehicle-activity") as response:
            data = json.loads(response.read())
            
            for item in data['body']:
                date = item['monitoredVehicleJourney']['framedVehicleJourneyRef']['dateFrameRef']
                bustime = re.search(r"\d\d:\d\d:\d\d", item['recordedAtTime']).group()
                line = item['monitoredVehicleJourney']['lineRef']
                vehicle = item['monitoredVehicleJourney']['vehicleRef']
                direction = item['monitoredVehicleJourney']['directionRef']
                latitude = item['monitoredVehicleJourney']['vehicleLocation']['latitude']
                longitude = item['monitoredVehicleJourney']['vehicleLocation']['longitude']
                speed = item['monitoredVehicleJourney']['speed']            
                
                row = [date, bustime, line, vehicle, direction, latitude, longitude, speed]
                csv_writer.writerow(row)
        print("Sleeping 5 seconds.")
        time.sleep(interval)
        current_time = time.time()
        print("Time remaining:", duration - (current_time - start_time))

            
                