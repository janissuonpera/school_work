
import json
with open("json1.txt") as file:
    data = json.load(file)

countries = [x["team A"] for x in data]
countries.extend([x["team B"] for x in data if x["team B"] not in countries])
matches = {x: {"losses":0, "ties":0, "wins":0, "matches":[]} for x in countries}

for item in data:
    team1 = item["team A"]
    team2 = item["team B"]
    date = item["date"]
    result = item["result"]
    
    country_game = {}
    
    whole_result = result
    result = result.split(" ")  
    reverse_result = whole_result.split(" ")[0]
    if(len(result)>1):
        result = result[1][1:4]
        result = result.split(":")
        reverse_result =  reverse_result + " (" + result[1] + ":" + result[0] + ")"
    else:
        result = result[0].split(":")
        reverse_result = result[1] + ":" + result[0]
        
    if(int(result[0])>int(result[1])):
        matches[team1]["wins"] += 1
        matches[team2]["losses"] += 1
        result[0], result[1] = result[1], result[0]
    elif(int(result[0])==int(result[1])):
        matches[team1]["ties"] += 1
        matches[team2]["ties"] += 1
    else:
        matches[team2]["wins"] += 1
        matches[team1]["losses"] += 1
        result[0], result[1] = result[1], result[0]

    matches[team1]["matches"].append({"against":team2, "date":date, "score":whole_result})
    matches[team2]["matches"].append({"against":team1, "date":date, "score":reverse_result})
    matches[team1]["matches"].sort(key=lambda a: a["date"][0:2])
    matches[team1]["matches"].sort(key=lambda a: a["date"][3:5])
    matches[team2]["matches"].sort(key=lambda a: a["date"][0:2])
    matches[team2]["matches"].sort(key=lambda a: a["date"][3:5])


print(json.dumps(matches, indent=2, sort_keys=True))
