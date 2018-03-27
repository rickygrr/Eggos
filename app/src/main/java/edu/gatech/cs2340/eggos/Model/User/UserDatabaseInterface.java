package edu.gatech.cs2340.eggos.Model.User;

import android.content.Context;

/**
 * Created by chateau86 on 21-Mar-18.
 */

public interface UserDatabaseInterface {
    boolean addUser(User newUser);
    boolean updateUser(User user);
    boolean userExists(String username);
    User getUser(String username, String password);
    void _initTestDatabase();
}
