#Function for finding pairs of artists and their distance
def findpairs(dists, list):
    couples = []
    for y in range(0, len(dists)):
        smallestdist = 1
        index = 0
        for x in range(0, len(dists[y])):
            if(dists[x][y]<smallestdist and dists[x][y]!=0): #This condition finds the smallest distance that is not the item itself aka 0
                smallestdist = dists[x][y]
                index = x
        couples.append([dists[index][y], list[y], list[index]])
    return couples

#Function for finding the best pair or pairs if there are many pairs with same distance
#The way I understood the exercise was that I'm meant to find only the closest pair/pairs and print them
def bestpairs(list=[]):
    list.sort(key=lambda a: a[0])
    
    most_similar = [list[0]]
    
    for x in list:
        test = [x[0], x[2], x[1]] #Checking for duplicates and removing them
        if(test in list):
            list.remove(x)
        if(x[0]==most_similar[0][0] and x!=most_similar[0]):
            most_similar.append(x)
        
    pairs = f"{most_similar[0][0]}: ({most_similar[0][1]}, {most_similar[0][2]})"
    if(len(most_similar)>1):
        for x in range(1, len(most_similar)):
            pairs += f" ({most_similar[x][1]}, {most_similar[x][2]})" #appending the additional pairs to the best match
    return pairs

import pandas as pd
import numpy as np
from scipy.spatial.distance import pdist, squareform


df = pd.read_csv("lastfm-matrix-germany.csv") #Reading the csv file into a dataframe
df.set_index('user', inplace=True) #Setting the users as index
df = df.loc[(df).any(1), (df!=0).any(0)] #Drop all rows and columns with only zeroes, probably works heh

artdists = squareform(pdist(df.T, "cosine")) 
artistnames = list(df) #List of artist names derived from column names
artists = findpairs(artdists, artistnames) #Calling the function which finds the pairs and their distance
print(bestpairs(artists)) #Calling the function which finds the closest pair and then prints it

usrdists = squareform(pdist(df, "cosine"))
usernums = list(df.T) #List of user nums derived from row values
users = findpairs(usrdists, usernums) #Calling the function which finds the pairs and their distance
print(bestpairs(users)) #Calling the function which finds the closest pair and then prints it
print() #Makes the output cleaner

mj_index = artistnames.index("michael jackson") #Index of the michael jackson row
close_to_mj = []
madonna_index = artistnames.index("madonna") #Index of madonna row
close_to_madonna = []
scooter_index = artistnames.index("scooter") #Index of scooter row
close_to_scooter = []

for y in range(0, len(artdists)):
    if(artistnames[y]!="michael jackson" and artistnames[y]!="madonna" and artistnames[y]!="scooter"): #Adding all the artist(excluding the artists themselves) and their distances
        close_to_mj.append([artdists[mj_index][y], artistnames[y]])
        close_to_madonna.append([artdists[madonna_index][y], artistnames[y]])
        close_to_scooter.append([artdists[scooter_index][y], artistnames[y]])

close_to_mj.sort(key=lambda a: a[0]) #Sorting the artists close to MJ
close_to_madonna.sort(key=lambda a: a[0]) #Sorting the artists close to Madonna
close_to_scooter.sort(key=lambda a: a[0]) #Sorting the artists close to Scooter
close_to_mj = close_to_mj[:10] #Limiting the list to only the 10 most similar artist
close_to_madonna = close_to_madonna[:10] #Limiting the list to only the 10 most similar artist
close_to_scooter = close_to_scooter[:10] #Limiting the list to only the 10 most similar artist

def print_artists(list, name):
    print("Artists similar to", name, "are:")
    for x in range(len(list)):
        print(list[x][0], list[x][1])
    print()
    
print_artists(close_to_mj, "Michael Jackson")
print_artists(close_to_madonna, "Madonna")
print_artists(close_to_scooter, "Scooter")

