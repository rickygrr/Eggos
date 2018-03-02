#! python3
#Convert .csv to .json offline for simpler handling on the android app side.
#Android supports .json read natively, while CSV solution without external
#libraries are frankly so hacky they're pure cancer. 

#FilthyFrank perfectly captured my reaction to existing CSV readers in Java 
#in this clip:(https://www.youtube.com/watch?v=Qz2Fz0OO8ag)

import csv
import json
import pprint

json_out = open('shelter.json','w')
f_in = open('Homeless Shelter Database.csv','r')

def parse_notes(in_str):
    #return list of notes
    return list(map(str.strip, in_str.strip().split(',')))

print(f_in.readline())
rdr = csv.reader(f_in,delimiter=',', quotechar='"')
csv_col = {
    'UID':  (0, int),
    'Name': (1, str),
    'Capacity': (2, int),
    'Restriction': (3, str),
    'Lon': (4, float),
    'Lat': (5, float),
    'Address': (6, str),
    'Notes': (7, parse_notes),
    'Phone': (8, str)
}
shelter_list = []

for row in rdr:
    #print(row)
    shelter = dict()
    for col_name in csv_col:
        ind, conv = csv_col[col_name]
        shelter[col_name] = conv(row[ind].strip())
    shelter_list.append(shelter)
pprint.pprint(shelter_list)

print(json.dumps(shelter_list,sort_keys=True, indent=4))
json_out.write(json.dumps(shelter_list,sort_keys=True, indent=4))
json_out.close()