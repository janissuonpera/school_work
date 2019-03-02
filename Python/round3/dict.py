# -*- coding: utf-8 -*-
"""
Created on Wed Oct 31 03:30:24 2018

@author: Janis
"""

def wordTypes(file):
    words = []
    valuetypes = {int:[], float:[], bool:[], str:[]}
    with open(file) as f:
        for line.rstrip("\n") in f.readlines():
            line = line.split(" ")
            for word in line:
                words.append(word)
    
    for word in words:
        try:
            word = int(word)
            valuetypes[int].append(word)
        except:
            try:
                word = float(word)
                valuetypes[float].append(word)
            except:
                if word=="True" or word=="true":
                    word = True
                    valuetypes[bool].append(word)
                else if word=="False" or word="false":
                    word = False
                    valuetypes[bool].append(word)
                else:
                    valuetypes[str].append(word)
    
    for key in valuetypes:
        valuetypes[key].sort()
    
    return valuetypes
    