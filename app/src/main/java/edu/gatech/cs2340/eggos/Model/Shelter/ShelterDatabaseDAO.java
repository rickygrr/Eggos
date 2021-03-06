package edu.gatech.cs2340.eggos.Model.Shelter;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Query("SELECT * FROM Shelters "
            + "WHERE _Name like :name "
            + "AND (:genderMask & _GenderMask) = :genderMask "
            + "AND (:ageMask & _AgeMask) = :ageMask ")
    List<Shelter> getFilteredShelterList(String name, int genderMask, int ageMask);

    @Query("SELECT * FROM Shelters "
            + "WHERE (:genderMask & _GenderMask) = :genderMask "
            + "AND (:ageMask & _AgeMask) = :ageMask ")
    List<Shelter> getFilteredShelterList(int genderMask, int ageMask);

    @Insert
    void insertAll(Shelter... shelter);

    @Update
    public int update(Shelter... shelters); //return row count

    @Delete
    void delete(Shelter shelter);

    @Query("SELECT Count(*) FROM Shelters")
    int getRowCount();

    @Query("DELETE FROM Shelters")
    void clearDatabase();
}
