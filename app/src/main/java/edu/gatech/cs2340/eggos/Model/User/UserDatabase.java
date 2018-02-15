package edu.gatech.cs2340.eggos.Model.User;

import java.util.ArrayList;

/**
 * Created by chateau86 on 14-Feb-18.
 */

public class UserDatabase {
    private static final UserDatabase ourInstance = new UserDatabase();
    public static UserDatabase getInstance() {
        return ourInstance;
    }

    private ArrayList<User> _Userlist;

    private UserDatabase() {
       this. _Userlist = new ArrayList<User>();
       this._initTestDatabase(); //TODO: Replace with database read or something
    }

    public boolean addUser(User newUser){
        if (this._getUser(newUser.getUsername()) != null){
            //duplicate user
            return false;
        }
        this._Userlist.add(newUser);
        return true;
    }

    private User _getUser(String username){
        for(User usr: this._Userlist) {
            if (usr.getUsername().equalsIgnoreCase(username)) {
                return usr;
            }
        }
        return null;
    }

    public User getUser(String username, String password){
        //Only return user if password matches, null otherwise
        //TODO
        User usr = this._getUser(username);
        if(usr != null && usr.checkPassword(password)){
            return usr;
        } else {
            return null;
        }
    }

    public void _initTestDatabase(){ //Fake database for debugging only
        this.addUser(new User("Monika","JustMonika",UserTypeEnum.ADMIN));// Just Monika [ok]
        this.addUser(new User("homelessdude","password",UserTypeEnum.USER));
        this.addUser(new User("shelterguy","gimmeshelter",UserTypeEnum.EMPLOYEE)); //Denzel Washington rolling over an MD-80 not included.
    }
}
