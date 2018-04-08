package edu.gatech.cs2340.eggos.Model.Shelter;

import android.util.JsonReader;
import android.util.SparseArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static edu.gatech.cs2340.eggos.Model.Shelter.AgeEnum.enum2Mask;

/**
 * Created by chateau86 on 26-Feb-18.
 */

@SuppressWarnings("MagicNumber")
public final class ShelterDatabase_local implements ShelterDatabaseInterface{

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
        this._ShelterList = new SparseArray<>();
        //this._initTestDatabase();
        this._jsonReadDone = false;
    }

    @Override
    public Shelter getShelterByID(int id){
        return _ShelterList.get(id);
    }

    @Override
    public boolean addShelter(Shelter s){
        if(_ShelterList.get(s.getUID()) != null){
            return false; //duplicate
        }
        _ShelterList.append(s.getUID(), s);
        return true;
    }
    @Override
    public void updateShelter(Shelter s) {
    }

    public int getNextShelterID(){
        //For shelter addition without manual UID
        return _ShelterList.keyAt(_ShelterList.size()-1) + 1;
    }

    @Override
    public List<Shelter> getShelterList(){
        //Copy the content of this function for filtering implementations.
        return this.getFilteredShelterList("", null, null );
    }
    @Override
    public List<Shelter> getFilteredShelterList(
            String nameFilter,
            List<String> genderFilter,
            List<String> ageFilter){
        int genderMask = 0;
        int ageMask = 0;
        if(genderFilter != null) {
            genderMask = GenderEnum.enum2Mask(GenderEnum.list2Enums(genderFilter));
        }
        if(ageFilter != null) {
            ageMask = AgeEnum.enum2Mask(AgeEnum.list2Enums(ageFilter));
        }
        List<Shelter> outList = new ArrayList<>();
        for(int i = 0; i < _ShelterList.size(); i++){
            if(nameFilter.isEmpty() ||
                (_ShelterList.get(i).getName().toLowerCase().contains(nameFilter.toLowerCase()))) {
                int gm = _ShelterList.get(i)._GenderMask;
                int am = _ShelterList.get(i)._AgeMask;
                if( ((ageMask & am) == ageMask)
                        && ((genderMask & gm) == genderMask)){
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

    @Override
    public List<Integer> packShelterList(Iterable<Shelter> shelterList) {
        List<Integer> out = new ArrayList<>();
        for(Shelter s: shelterList){
            out.add(s.getUID());
        }
        return out;
    }

    @Override
    public List<Shelter> unpackShelterList(Iterable<Integer> shelterIndexList) {
        List<Shelter> out = new ArrayList<>();
        for(int s: shelterIndexList){
            out.add(getShelterByID(s));
        }
        return out;
    }

    @SuppressWarnings({"MagicNumber", "FeatureEnvy", "LawOfDemeter", "ChainedMethodCall"})
    //It's a test database. OF COURSE it will have magic numbers.
    //It's a shelter*BUILDER*. Of course we will access it a bunch to *BUILD* the shelter object.
    //There goes FeatureEnvy and LawOfDemeter.
    //It's 2018: Builder pattern should be common knowledge by now.
    @Override
    public void _initTestDatabase(){
        this.addShelter(new ShelterBuilder()
                .setUID(0)
                .setName("Test Shelter")
                .setCapacity(20)
                //.setRestrictions("blah")
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
                //.setRestrictions("bleugh")
                .setGendersMask(GenderEnum.enum2Mask(GenderEnum.Men))
                .setAgeMask(enum2Mask(AgeEnum.Children))
                .setNotes("bleugh")
                .setCoord(421.0, 69.0)
                .setAddr("124 Fake St.\n 42069")
                .setPhone("477-CARS4KIDS")
                .createShelter());
    }

    @Override
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

    @SuppressWarnings({"FeatureEnvy", "LawOfDemeter", "ChainedMethodCall"})
    //It's a shelter*BUILDER*. Of course we will access it a bunch to *BUILD* the shelter object.
    //There goes FeatureEnvy and LawOfDemeter.
    //It's 2018: Builder pattern should be common knowledge by now.
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
                                //.setRestrictions(restriction)
                                .setGendersMask(genderMask)
                                .setAgeMask(ageMask)
                                .setNotes(notes)
                                .setCoord(lat, lon)
                                .setAddr(addr)
                                .setPhone(phone)
                                .createShelter());
    }
}
