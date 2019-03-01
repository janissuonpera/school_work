from urllib.request import urlopen
with urlopen("https://www.textfixer.com/tutorials/common-english-words.txt") as response:
    stop_words = response.read().decode('utf8')
    
stop_words = stop_words.split(",") #A list of all the stop words to be ignored

with open("wikitexts.txt") as file: #Opening the file from a text file as I couldnt open it from weto's url
    all_documents = file.read()

#with urlopen("https://wetodev.sis.uta.fi/weto5/downloadTaskDocument.action?&taskId=6804&tabId=0&dbId=1&documentId=329613") as response:
    #all_documents = response.read()

documentlist = all_documents.split("\n\n")

import re
wordpattern = re.compile(r'\b\w+\b') #A regex pattern to match any word

all_words = re.findall(wordpattern, all_documents) #Finding all the words(including stop words for now)
all_words = list(set(all_words)) #remove duplicate words

word_values = [] #A list where I'll store all the 27 values of a word later

import math

for word in all_words: #Looping through everyword
    if word not in stop_words: #Ignoring stop words
        total_term_counter = 0 #How many occurences of word in all documents
        doc_frequency = 0 #How many documents in the collection D contain at least one occurrence of word?
        tfidf_wd = [] #Term frequency–inverse document frequency
        for doc_index in range(0, len(documentlist)): #Looping through every document
            words_in_document = re.findall(f"\\b{word}\\b", documentlist[doc_index]) #sidenote: singular and plural are treated as different words
            term_count = len(words_in_document) #Count of word in document
            tfidf_wd.append(term_count)
            if(term_count)>0:
                doc_frequency += 1
            total_term_counter += term_count
        inverse_doc_frequency = math.log((len(documentlist)/(1+doc_frequency)))
        tfidf_wd = [i*inverse_doc_frequency for i in tfidf_wd]
        word_values.append([word, total_term_counter]) #Appending word and its count in all docs
        word_values[len(word_values)-1].extend(tfidf_wd) #Adding all the rest of the values after the word and its count

word_values.sort(key=lambda a: a[0]) #Sorting alphabetically
for x in word_values:
    print(" ".join([str(i) for i in x]))
    
total_term_frequencies = [i[1] for i in word_values] #Making a list of only the total frequencies
total_term_frequencies.sort(reverse=True) #Sorting it in a descending order

import matplotlib.pyplot as plt

plt.plot(total_term_frequencies) #Plotting it
plt.show()