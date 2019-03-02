class President:
    __slots__ = '_number', '_name', '_startdate', '_enddate'
    
    def __init__(self, number, name, startdate, enddate):
        self._number = number
        self._name = name
        self._startdate = startdate
        self._enddate = enddate
        

    def number(self):
        return self._number

    def name(self):
        return self._name    

    def startdate(self):
        return self._startdate
   
    def enddate(self):
        return self._enddate
    
    def __str__(self):
        date1 = str(self._startdate[2]) + "." + str(self._startdate[1]) + "." + str(self._startdate[0])
        date2 = str(self._enddate[2]) + "." + str(self._enddate[1]) + "." + str(self._enddate[0])
        president = f"{self._number}: {self._name} (from {date1} until {date2})"
        return president

p = President(43, "George W. Bush", (2001, 1, 20), (2009, 1, 20))

print(p)
p.age = 72
