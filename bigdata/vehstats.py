import csv
import pandas as pd
from IPython.display import display as disp
make_shares = []
car_mileages = []
car_ages = []
car_emissions = []

veh = pd.read_csv("Ajoneuvojen avoin data 5.3.csv", sep=";", encoding="latin")

sample = veh[(veh['ajoneuvoluokka'] == "M1") | (veh['ajoneuvoluokka'] == "MG1")]
sample = sample.merkkiSelvakielinen.value_counts()

carcounts = sample.head(5).append(pd.Series({"Others": sample.iloc[5:].sum()})) #5 most common car brands, sum of others
print(carcounts)


ranges = [0, 50000, 100000, 150000, 200000, 250000, 300000, float("inf")]
sample2 = veh[(veh['ajoneuvoluokka'] == "M1") | (veh['ajoneuvoluokka'] == "MG1")]
sample3 = sample2.matkamittarilukema.groupby(pd.cut(sample2.matkamittarilukema, ranges)).count()

print(sample3)

sample4 = pd.to_datetime(sample2.ensirekisterointipvm).dt.year.value_counts()
print(sample4)

ranges = [0, 6, 11, 16, 21, float("inf")]
sample5 = sample2.ensirekisterointipvm.groupby(pd.cut(pd.datetime.today().year - pd.to_datetime(sample2.ensirekisterointipvm).dt.year, \
                                                      ranges, right=False, include_lowest=True)).count()

        
print("done")
        