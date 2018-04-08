package edu.gatech.cs2340.eggos.Model.User;

/**
 * Created by chateau86 on 21-Mar-18.
 */

public interface UserDatabaseInterface {
    boolean addUser(User newUser);
    void updateUser(User user);
    boolean userExists(String username);
    User getUser(String username, String password);
    void _initTestDatabase();
}
