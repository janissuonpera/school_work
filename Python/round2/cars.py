class Car:
  
  def __init__(self, make, model, year, mileage):
    self.make = make
    self.model = model
    self.year = year
    self.mileage = mileage
  
  def __str__(self):
    str = ("Make: {}\nModel: {}\nYear: {}\nMileage: {}\n".format(self.make, self.model, self.year, self.mileage))
    return str

def carsByYear(cars):
  return sorted(sorted(cars, key=lambda a: (a.make, a.model, a.mileage)), key=lambda x: x.year, reverse=True)
  
def filterCars(cars, minyear, maxyear, minkm, maxkm): 
  filtered_cars = sorted(sorted((c for c in cars if minyear<=c.year<=maxyear and minkm<=c.mileage<=maxkm), key=lambda a: (a.make, a.model, a.mileage)), key=lambda x: x.year, reverse=True)
  return filtered_cars

cars = [Car("Ford", "Mondeo", 2008, 222260),
        Car("Toyota", "Rav4", 1997, 115780),
        Car("Volvo", "960", 1995, 365064),
        Car("Toyota", "Corolla", 2010, 32698),
        Car("Bmw", "520I", 2004, 173314),
        Car("Toyota", "Corolla", 1995, 301091),
        Car("Volkswagen", "Golf", 2008, 256000),
        Car("Audi", "A4", 2014, 185723),
        Car("Nissan", "Qashqai+2", 2011, 58129),
        Car("Nissan", "Qashqai", 2010, 62151)]

carz = [cars[0], cars[1], cars[2]]

print(carsByYear(carz))
print(filterCars(carz, 2000, 2010, 0, 256000))


