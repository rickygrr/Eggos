package edu.gatech.cs2340.eggos.Model.User;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by chateau86 on 26-Mar-18.
 */

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabaseBackend_Room extends RoomDatabase {
    public abstract UserDatabaseDAO userDBDAO();
}
