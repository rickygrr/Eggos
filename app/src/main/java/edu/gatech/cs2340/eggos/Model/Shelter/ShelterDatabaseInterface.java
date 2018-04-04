package edu.gatech.cs2340.eggos.Model.Shelter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by chateau86 on 21-Mar-18.
 */

public interface ShelterDatabaseInterface {
    /*ShelterDatabaseFilter SHOW_ALL_FILTER = new ShelterDatabaseFilter() {
        @Override
        public boolean keepShelter(Shelter s) {
            return true;
        }
    };*/
    Shelter getShelterByID(int id);
    boolean addShelter(Shelter s);
    boolean updateShelter(Shelter s);
    List<Shelter> getShelterList();
    List<Shelter> getFilteredShelterList(String name, List<String> genderList, List<String> ageList);
    List<Integer> packShelterList(Iterable<Shelter> shelterList);
    List<Shelter> unpackShelterList(Iterable<Integer> shelterIndexList);
    void _initTestDatabase();
    void initFromJSON(InputStream f_in)throws IOException;

}
