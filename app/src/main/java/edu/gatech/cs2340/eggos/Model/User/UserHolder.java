package edu.gatech.cs2340.eggos.Model.User;

/**
 * UserHolder singleton to store the app's currently logged-in user
 * Created by chateau86 on 14-Feb-18.
 */

public class UserHolder {
    private static final UserHolder ourInstance = new UserHolder();
    public static UserHolder getInstance() {
        return ourInstance;
    }
    private UserDatabaseInterface _UserDBInstance;

    private User _currentUser;
    private UserHolder() {
        this._currentUser = null; //Might default to guest when we implement guest.
        //this._UserDBInstance = UserDatabase_local.getInstance();
        this._UserDBInstance = UserDatabase_room.getInstance();
    }

    private boolean _setUser(User usr){
        this._currentUser = usr;
        return true;
    }

    public User getUser(){
        return this._currentUser;
    }

    public boolean login(String username, String password){
        User usr = this._UserDBInstance.getUser(username, password);
        if (usr == null){
            return false;
        } else{
            this._currentUser = usr;
            return true;
        }
    }

    public boolean logout(){
        this._currentUser = null;
        return true;
    }

}
