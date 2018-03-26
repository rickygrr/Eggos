package edu.gatech.cs2340.eggos.Model.Shelter;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.gatech.cs2340.eggos.Model.User.User;

/**
 * Created by chateau86 on 26-Mar-18.
 */
@Dao
public interface ShelterDatabaseDAO {

    @Query("SELECT * FROM Shelters " +
            "WHERE _UID = :UID")
    List<Shelter> getShelter(int UID);

    @Query("SELECT * FROM Shelters " +
            "WHERE _Genders LIKE :likeGender")
    List<Shelter> getShelterByGender(String likeGender);

    @Insert
    void insertAll(Shelter... shelter);


    @Delete
    void delete(Shelter shelter);

    @Query("SELECT Count(*) FROM Shelters")
    int getRowCount();
}
