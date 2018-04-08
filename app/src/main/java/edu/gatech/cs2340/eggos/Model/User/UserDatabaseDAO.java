package edu.gatech.cs2340.eggos.Model.User;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by chateau86 on 26-Mar-18.
 */
@Dao
public interface UserDatabaseDAO {

    @Query("SELECT * FROM Users " +
            "WHERE UPPER(_Username) = UPPER(:username) " +
            "AND _Password = :password ")
    List<User> getUser(String username, String password);

    @Insert
    void insertAll(User... user);

    @Query("SELECT * FROM Users " +
            "WHERE UPPER(_Username) = UPPER(:username) ")
    List<User> userExists(String username);

    @Query("SELECT Count(*) FROM Users " +
            "WHERE UPPER(_Username) = UPPER(:username) ")
    int userExistsCount(String username);

    @Delete
    void delete(User user);

    @Update
    int update(User... users); //return row count

    @Query("SELECT Count(*) FROM Users")
    int getRowCount();
}
