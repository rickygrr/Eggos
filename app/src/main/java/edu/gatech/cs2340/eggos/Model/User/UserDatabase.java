package edu.gatech.cs2340.eggos.Model.User;

/**
 * Created by chateau86 on 14-Feb-18.
 */

public class UserDatabase {
    private static final UserDatabase ourInstance = new UserDatabase();
    public static UserDatabase getInstance() {
        return ourInstance;
    }

    private User[] _Userlist;

    private UserDatabase() {
    }

    public boolean addUser(User newUser){
        //TODO
        return false;
    }
    public User getUser(String username, String password){
        //Only return user if password matches, null otherwise
        //TODO
        return null;
    }
}
