package edu.gatech.cs2340.eggos.Model.User;

import java.util.ArrayList;

/**
 * Created by chateau86 on 14-Feb-18.
 */

public class UserDatabase_local implements UserDatabaseInterface {
    private static final UserDatabase_local ourInstance = new UserDatabase_local();
    public static UserDatabase_local getInstance() {
        return ourInstance;
    }

    private ArrayList<User> _Userlist;

    private UserDatabase_local() {
       this. _Userlist = new ArrayList<User>();
       this._initTestDatabase(); //TODO: Replace with database read or something
    }

    public boolean addUser(User newUser){
        if (userExists(newUser.getUsername())){
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

    public boolean userExists(String username){
        return _getUser(username) != null;
    }

    public User getUser(String username, String password){
        //Only return user if password matches, null otherwise
        User usr = this._getUser(username);
        if(usr != null && usr.checkPassword(password)){
            return usr;
        } else {
            return null;
        }
    }

    public void _initTestDatabase(){ //Fake database for debugging only
        this.addUser(new User("Monika","JustMonika",UserTypeEnum.ADMIN));// Just Monika [ok]
        this.addUser(new User("a","b",UserTypeEnum.ADMIN));
        this.addUser(new User("homelessdude","password",UserTypeEnum.USER));
        this.addUser(new User("shelterguy","gimmeshelter",UserTypeEnum.EMPLOYEE)); //Denzel Washington rolling over an MD-80 not included.
    }
}
