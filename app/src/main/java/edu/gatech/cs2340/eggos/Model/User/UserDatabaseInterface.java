package edu.gatech.cs2340.eggos.Model.User;

import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by chateau86 on 21-Mar-18.
 */

public interface UserDatabaseInterface {
    //boolean addUser(User newUser);
    boolean addUser(String username, String password, UserTypeEnum type);
    void addUser(String username, String password, UserTypeEnum type, OnSuccessListener<Void> callback);
    boolean userExists(String username);
    User getUser(String username, String password);
}
