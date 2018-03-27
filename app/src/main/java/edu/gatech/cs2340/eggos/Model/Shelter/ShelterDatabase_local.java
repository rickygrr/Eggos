package edu.gatech.cs2340.eggos.Model.Shelter;

import android.util.JsonReader;
import android.util.SparseArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

import static edu.gatech.cs2340.eggos.Model.Shelter.AgeEnum.enum2Mask;

/**
 * Created by chateau86 on 26-Feb-18.
 */

public class ShelterDatabase_local implements ShelterDatabaseInterface{

    private static final ShelterDatabase_local ourInstance = new ShelterDatabase_local();

    private SparseArray<Shelter> _ShelterList;
    private boolean _jsonReadDone;
    /*public static final ShelterDatabaseFilter SHOW_ALL_FILTER = new ShelterDatabaseFilter() {
        @Override
        public boolean keepShelter(Shelter s) {
            return true;
        }
    };*/

    public static ShelterDatabase_local getInstance() {
        return ourInstance;
    }

    private ShelterDatabase_local() {
        this._ShelterList = new SparseArray<Shelter>();
        //this._initTestDatabase();
        this._jsonReadDone = false;
    }

    public Shelter getShelterByID(int id){
        return _ShelterList.get(id);
    }

    public boolean addShelter(Shelter s){
        if(_ShelterList.get(s.getUID()) != null){
            return false; //duplicate
        }
        _ShelterList.append(s.getUID(), s);
        return true;
    }

    public int getNextShelterID(){
        //For shelter addition without manual UID
        return _ShelterList.keyAt(_ShelterList.size()-1) + 1;
    }

    public List<Shelter> getShelterList(){ //Copy the content of this function for filtering implementations.
        return this.getFilteredShelterList("", null, null );
    }

    /*public List<Shelter> getFilteredShelterList(ShelterDatabaseFilter filt){
        /*
        "Wow, talk about a completely unloved class, conforms to ZERO collection interfaces..."
             -user166390, https://stackoverflow.com/questions/7999211/how-to-iterate-through-sparsearray
         *//*
        //SparseArray<> is now officially my spirit animal. :(
        //Pls get me in the screenshot for the obligatory /r/me_irl post - W.K.
        ArrayList<Shelter> outList = new ArrayList<Shelter>();
        for(int i = 0; i < _ShelterList.size(); i++){
            if(filt.keepShelter(_ShelterList.valueAt(i))) {
                outList.add(_ShelterList.valueAt(i));
            }
        }
        return outList;
    }*/
    public List<Shelter> getFilteredShelterList(String nameFilter, List<String> genderFilter, List<String> ageFilter){
        int genderMask = 0;
        int ageMask = 0;
        if(genderFilter != null) {
            genderMask = GenderEnum.enum2Mask(GenderEnum.list2Enums(genderFilter));
        }
        if(ageFilter != null) {
            ageMask = AgeEnum.enum2Mask(AgeEnum.list2Enums(ageFilter));
        }
        if (genderMask == 0){
            genderMask = GenderEnum.ALL_MASK;
        }
        if (ageMask == 0){
            ageMask = AgeEnum.ALL_MASK;
        }
        ArrayList<Shelter> outList = new ArrayList<Shelter>();
        for(int i = 0; i < _ShelterList.size(); i++){
            if(nameFilter.isEmpty() || (_ShelterList.get(i).getName().toLowerCase().contains(nameFilter.toLowerCase()))) {
                if(((_ShelterList.get(i)._GenderMask) & genderMask)>0 && (((_ShelterList.get(i)._AgeMask) & ageMask)>0)){
                    outList.add(_ShelterList.get(i));
                }
            }
        }
        return outList;
    }


    private static ArrayList<Shelter> SparseArrToArrayList(SparseArray<Shelter> sp){
        if(sp==null){ return null;}
        ArrayList<Shelter> ar = new ArrayList<>(sp.size());
        for(int i = 0; i < sp.size(); i++){
            ar.add(sp.get(sp.keyAt(i)));
        }
        return ar;
    }

    public void _initTestDatabase(){
        this.addShelter(new ShelterBuilder()
                .setUID(0)
                .setName("Test Shelter")
                .setCapacity(20)
                .setRestrictions("blah")
                .setGendersMask(GenderEnum.enum2Mask(GenderEnum.Men, GenderEnum.Women))
                .setAgeMask(enum2Mask(AgeEnum.All))
                .setNotes("bleugh")
                .setCoord(420.0, 69.0)
                .setAddr("123 Fake St.\n 42069")
                .setPhone("867-5309")
                .createShelter());
        this.addShelter(new ShelterBuilder()
                .setUID(1)
                .setName("Test Shelter 2")
                .setCapacity(420)
                .setRestrictions("bleugh")
                .setGendersMask(GenderEnum.enum2Mask(GenderEnum.Men))
                .setAgeMask(enum2Mask(AgeEnum.Children))
                .setNotes("bleugh")
                .setCoord(421.0, 69.0)
                .setAddr("124 Fake St.\n 42069")
                .setPhone("477-CARS4KIDS")
                .createShelter());
    }

    public void initFromJSON(InputStream f_in)throws IOException {
        if (_jsonReadDone){
            return;
        }
        _jsonReadDone = true;
        //InputStream f_in = cont.getResources().openRawResource(R.raw.shelter);
        JsonReader reader = new JsonReader(new InputStreamReader(f_in, "UTF-8"));
        reader.beginArray();
        while(reader.hasNext()){
            _readShelter(reader);
        }
        reader.endArray();
    }

    private void _readShelter(JsonReader reader) throws IOException{
        reader.beginObject();
        String addr = "", name = "", phone = "", restriction = "";
        int uid = 0, cap = 0;
        double lat = 0, lon = 0;
        String notes = "";
        int genderMask = 0;
        int ageMask = 0;

        while(reader.hasNext()){
            String token_name = reader.nextName();
            switch(token_name){
                case "Name":
                    name = reader.nextString();
                    break;
                case "Address":
                    addr = reader.nextString();
                    break;
                case "Phone":
                    phone = reader.nextString();
                    break;
                case "Restriction":
                    restriction = reader.nextString();
                    break;
                case "UID":
                    uid = reader.nextInt();
                    break;
                case "Capacity":
                    cap = reader.nextInt();
                    break;
                case "Lat":
                    lat = reader.nextDouble();
                    break;
                case "Lon":
                    lon = reader.nextDouble();
                    break;
                case "Notes":
                    notes = reader.nextString();
                    break;
                case "GenderRestriction":
                    reader.beginArray();
                    while(reader.hasNext()){
                        genderMask = GenderEnum.addToMask(reader.nextString(), genderMask);
                    }
                    reader.endArray();
                    break;
                case "AgeRestriction":
                    reader.beginArray();
                    while(reader.hasNext()){
                        ageMask = AgeEnum.addToMask(reader.nextString(), ageMask);
                    }
                    reader.endArray();
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        this.addShelter(new ShelterBuilder()
                                .setUID(uid)
                                .setName(name)
                                .setCapacity(cap)
                                .setRestrictions(restriction)
                                .setGendersMask(genderMask)
                                .setAgeMask(ageMask)
                                .setNotes(notes)
                                .setCoord(lat, lon)
                                .setAddr(addr)
                                .setPhone(phone)
                                .createShelter());
    }
    //TODO: Read CSV/JSON
}
