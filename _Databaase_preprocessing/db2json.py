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
f_in = open('Homeless Shelter Database2.csv','r')

def parse_notes(in_str):
    #return list of notes
    return list(map(str.strip, in_str.strip().split(',')))

print(f_in.readline())
rdr = csv.reader(f_in,delimiter=',', quotechar='"')
csv_col = {
    'UID':  (0, int),
    'Name': (1, str),
    'Capacity': (2, int),
    'GenderRestriction': (3, parse_notes),
    'AgeRestriction': (4, parse_notes),
    'Restriction': (5, str),
    'Lon': (6, float),
    'Lat': (7, float),
    'Address': (8, str),
    'Notes': (9, str),
    'Phone': (10, str)
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