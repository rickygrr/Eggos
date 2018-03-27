package edu.gatech.cs2340.eggos.Model.Shelter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by chateau86 on 21-Mar-18.
 */

public interface ShelterDatabaseInterface {
    ShelterDatabaseFilter SHOW_ALL_FILTER = new ShelterDatabaseFilter() {
        @Override
        public boolean keepShelter(Shelter s) {
            return true;
        }
    };
    Shelter getShelterByID(int id);
    boolean addShelter(Shelter s);
    int getNextShelterID();
    ArrayList<Shelter> getShelterList();
    ArrayList<Shelter> getFilteredShelterList(ShelterDatabaseFilter filt);
    void _initTestDatabase();
    void initFromJSON(InputStream f_in)throws IOException;

}
