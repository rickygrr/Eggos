package edu.gatech.cs2340.eggos.Model.Shelter;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static edu.gatech.cs2340.eggos.Model.Shelter.AgeEnum.enum2Mask;

/**
 * Created by chateau86 on 27-Mar-18.
 */

public final class ShelterDatabase_room implements ShelterDatabaseInterface {
    private static final ShelterDatabase_room ourInstance = new ShelterDatabase_room();
    private ShelterDatabaseBackend_Room db;
    private ShelterDatabaseDAO dao;
    private boolean _jsonReadDone = false;

    private ShelterDatabase_room() {
    }

    /**
     * Get instance of shelter database interface
     * @return instance of shelter database interface
     */
    public static ShelterDatabaseInterface getInstance() {
        if((ourInstance.dao == null) || (ourInstance.dao.getRowCount() == 0)){
            throw new IllegalStateException("Database not initialized");
        }
        //Log.e("ShelterDatabase", "Instance given with row count "+ourInstance.dao.getRowCount());
        return ourInstance;
    }

    /**
     * Get first instance of shelter database interface
     * @param cont context of shelter
     * @return instance of shelter database interface
     */
    @SuppressWarnings("ChainedMethodCall")
    //Builder pattern strikes again.
    public static ShelterDatabaseInterface getFirstInstance(Context cont) {
        if(ourInstance.db == null){
            //throw new IllegalStateException("Database not initialized");
            ourInstance.db = Room.databaseBuilder(cont,
                    ShelterDatabaseBackend_Room.class, "Shelters").allowMainThreadQueries().build();
            ourInstance.dao = ourInstance.db.shelterDBDAO();
            ourInstance._jsonReadDone = (ourInstance.getRowCount() > 5);
        }
        return ourInstance;
    }

    /**
     * Get first instance of shelter database interface
     * @param ext_dao external data access object
     * @return instance of shelter database interface
     */
    public static ShelterDatabaseInterface getFirstInstance(ShelterDatabaseDAO ext_dao) {
        if(ourInstance.db == null){
            //throw new IllegalStateException("Database not initialized");
            ourInstance.db = null;
            ourInstance.dao = ext_dao;
            ourInstance._jsonReadDone = (ourInstance.getRowCount() > 5);
        }
        return ourInstance;
    }

    /**
     * Get row count
     * @return row count of database
     */
    public int getRowCount(){
        return dao.getRowCount();
    }

    /**
     * Get shelter by ID
     * @param id ID of shelter
     * @return list of shelters by ID
     */
    @Override
    public Shelter getShelterByID(int id) {
        List<Shelter> match_list = dao.getShelter(id);
        if(match_list.isEmpty()){
            return null;
        } else {
            return match_list.get(0);
        }
    }

    /**
     * Add shelter to database
     * @param s shelter to add
     * @return whether or not the shelter was added
     */
    @Override
    public boolean addShelter(Shelter s) {
        this.dao.insertAll(s);
        return true;
    }

    /**
     * Update shelter list
     * @param s shelter to update
     */
    @Override
    public void updateShelter(Shelter s) {
        int count = this.dao.update(s);
        if(count == 0){
        } else {
            if(count > 1){
                throw new IllegalStateException(
                    "Shelter update wrote too many rows. Database probably clobbered. Rows: "+count
                );
            }
        }
    }

    /**
     * Get shelter list
     * @return shelter list
     */
    @Override
    public List<Shelter> getShelterList() {
        return getFilteredShelterList("",null,null);
    }

    /**
     * Get filtered shelter list
     * @param name name of shelter
     * @param genderFilter gender filter of shelter
     * @param ageFilter age filter of shelter
     * @return filtered shelter list
     */
    @Override
    public List<Shelter> getFilteredShelterList(
                String name,
                List<String> genderFilter,
                List<String> ageFilter) {
        int genderMask = 0;
        int ageMask = 0;
        if(genderFilter != null) {
            genderMask = GenderEnum.enum2Mask(GenderEnum.list2Enums(genderFilter));
        }
        if(ageFilter != null) {
            ageMask = AgeEnum.enum2Mask(AgeEnum.list2Enums(ageFilter));
        }
        if(name.isEmpty()){
            return dao.getFilteredShelterList(genderMask, ageMask);
        }else {
            return dao.getFilteredShelterList("%"+name+"%", genderMask, ageMask);
        }
    }

    /**
     * Pack shelter list
     * @param shelterList shelter list
     * @return packed shelter list
     */
    @Override
    public List<Integer> packShelterList(Iterable<Shelter> shelterList) {
        List<Integer> out = new ArrayList<>();
        for(Shelter s: shelterList){
            out.add(s.getUID());
        }
        return out;
    }

    /**
     * Unpack shelter list
     * @param shelterIndexList shelter index list
     * @return unpacked shelter list
     */
    @Override
    public List<Shelter> unpackShelterList(Iterable<Integer> shelterIndexList) {
        List<Shelter> out = new ArrayList<>();
        for(int s: shelterIndexList){
            out.add(getShelterByID(s));
        }
        return out;
    }

    /**
     * Transfer reservation between shelter
     * @param oldOccupancy old occupancy of shelter
     * @param oldShelterID old shelter's ID
     * @param newOccupancy new occupancy of shelter
     * @param newShelterID new shelter's ID
     */
    public void transferReservation(int oldOccupancy, int oldShelterID, int newOccupancy, int newShelterID){
        if(oldShelterID != -1) {
            //return beds
            Shelter s = this.getShelterByID(oldShelterID);
            if(s != null) {
                s.freeRoom(oldOccupancy);
                this.updateShelter(s);
            }
        }
        if (newOccupancy > 0) {
            Shelter s = this.getShelterByID(newShelterID);
            if(s != null) {
                s.requestRoom(newOccupancy);
                this.updateShelter(s);
            }
        }
    }

    /**
     * Initialize test database of shelters
     */
    @SuppressWarnings({"MagicNumber", "FeatureEnvy", "LawOfDemeter", "ChainedMethodCall"})
    //It's a test database. OF COURSE it will have magic numbers.
    //It's a shelter*BUILDER*. Of course we will access it a bunch to *BUILD* the shelter object.
    //There goes FeatureEnvy, LawOfDemeter, and ChainedMethodCall.
    //It's 2018: Builder pattern should be common knowledge by now.
    @Override
    public void _initTestDatabase() {
        ourInstance._jsonReadDone = true;
        //noinspection MagicNumber,MagicNumber,MagicNumber
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
        //noinspection MagicNumber,MagicNumber,MagicNumber
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

    /**
     * Initialize database from json data
     * @param f_in data in json format
     * @throws IOException
     */
    @Override
    public void initFromJSON(InputStream f_in)throws IOException {
        if (_jsonReadDone){
            return;
        }
        this.dao.clearDatabase();
        _jsonReadDone = true;
        //InputStream f_in = cont.getResources().openRawResource(R.raw.shelter);
        JsonReader reader = new JsonReader(new InputStreamReader(f_in, "UTF-8"));
        reader.beginArray();
        while(reader.hasNext()){
            _readShelter(reader);
        }
        reader.endArray();
    }

    /**
     * Read shelter from database
     * @param reader json reader of database
     * @throws IOException
     */
    @SuppressWarnings({"FeatureEnvy", "LawOfDemeter", "ChainedMethodCall"})
    //It's a shelter*BUILDER*. Of course we will access it a bunch to *BUILD* the shelter object.
    //There goes FeatureEnvy and LawOfDemeter.
    //It's 2018: Builder pattern should be common knowledge by now.
    private void _readShelter(JsonReader reader) throws IOException{
        reader.beginObject();
        String addr = "";
        String name = "";
        String phone = "";
        String restriction = "";
        int uid = 0;
        int cap = 0;
        double lat = 0;
        double lon = 0;
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
