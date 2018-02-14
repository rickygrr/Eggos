package edu.gatech.cs2340.eggos.Model.User;

/**
 * Created by chateau86 on 14-Feb-18.
 */

public class UserDatabase {
    private static final UserDatabase ourInstance = new UserDatabase();

    public static UserDatabase getInstance() {
        return ourInstance;
    }

    private UserDatabase() {
    }
}
