package edu.gatech.cs2340.eggos.Model.Shelter;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import edu.gatech.cs2340.eggos.Model.User.User;
import edu.gatech.cs2340.eggos.Model.User.UserDatabaseDAO;

/**
 * Created by chateau86 on 26-Mar-18.
 */

@Database(entities = {Shelter.class}, version = 1)
public abstract class ShelterDatabaseBackend_Room extends RoomDatabase {
    public abstract ShelterDatabaseDAO shelterDBDAO();
}
