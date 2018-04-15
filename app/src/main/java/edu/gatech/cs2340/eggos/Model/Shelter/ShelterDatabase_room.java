package edu.gatech.cs2340.eggos.Model.Shelter;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import edu.gatech.cs2340.eggos.Model.User.UserDatabaseBackend_Room;
import edu.gatech.cs2340.eggos.Model.User.UserDatabase_room;

import static edu.gatech.cs2340.eggos.Model.Shelter.AgeEnum.enum2Mask;

/**
 * Created by chateau86 on 27-Mar-18.
 */

public class ShelterDatabase_room implements ShelterDatabaseInterface {
    private static final ShelterDatabase_room ourInstance = new ShelterDatabase_room();
    private ShelterDatabaseBackend_Room db;
    private ShelterDatabaseDAO dao;
    private boolean _jsonReadDone = false;

    private ShelterDatabase_room() {
    }

    public static ShelterDatabase_room getInstance() {
        if(ourInstance.db == null || ourInstance.dao.getRowCount() == 0){
            throw new IllegalStateException("Database not initialized");
        }
        //Log.e("ShelterDatabase", "Instance given with row count "+ourInstance.dao.getRowCount());
        return ourInstance;
    }

    public static ShelterDatabase_room getFirstInstance(Context cont) {
        if(ourInstance.db == null){
            //throw new IllegalStateException("Database not initialized");
            ourInstance.db = Room.databaseBuilder(cont,
                    ShelterDatabaseBackend_Room.class, "Shelters").allowMainThreadQueries().build();
            ourInstance.dao = ourInstance.db.shelterDBDAO();
            ourInstance._jsonReadDone = (ourInstance.getRowCount() > 5);
        }
        return ourInstance;
    }

    public int getRowCount(){
        return dao.getRowCount();
    }

    @Override
    public Shelter getShelterByID(int id) {
        List<Shelter> match_list = dao.getShelter(id);
        if(match_list.isEmpty()){
            return null;
        } else {
            return match_list.get(0);
        }
    }

    @Override
    public boolean addShelter(Shelter s) {
        this.dao.insertAll(s);
        return true;
    }

    @Override
    public boolean updateShelter(Shelter s) {
        int count = this.dao.update(s);
        if(count == 0){
            return false;
        } else {
            if(count > 1){
                throw new IllegalStateException("Shelter update wrote too many rows. Database probably clobbered. Rows: "+count);
            }
            return true;
        }
    }

    @Override
    public List<Shelter> getShelterList() {
        return getFilteredShelterList("",null,null);
    }

    @Override
    public List<Shelter> getFilteredShelterList(String name, List<String> genderFilter, List<String> ageFilter) {
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

    @Override
    public void _initTestDatabase() {
        ourInstance._jsonReadDone = true;
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

}
