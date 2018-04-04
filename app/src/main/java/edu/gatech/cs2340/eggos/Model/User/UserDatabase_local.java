package edu.gatech.cs2340.eggos.Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chateau86 on 14-Feb-18.
 */

public final class UserDatabase_local implements UserDatabaseInterface {
    private static final UserDatabase_local ourInstance = new UserDatabase_local();
    public static UserDatabase_local getInstance() {
        return ourInstance;
    }

    private List<User> _Userlist;

    private UserDatabase_local() {
       this. _Userlist = new ArrayList<>();
       this._initTestDatabase();
    }

    @Override
    public boolean addUser(User newUser){
        if (userExists(newUser.getUsername())){
            //duplicate user
            return false;
        }
        this._Userlist.add(newUser);
        return true;
    }
    @Override
    public boolean updateUser(User s) {
        return false;
    }

    private User _getUser(String username){
        for(User usr: this._Userlist) {
            if (usr.getUsername().equalsIgnoreCase(username)) {
                return usr;
            }
        }
        return null;
    }

    @Override
    public boolean userExists(String username){
        return _getUser(username) != null;
    }

    @Override
    public User getUser(String username, String password){
        //Only return user if password matches, null otherwise
        User usr = this._getUser(username);
        if((usr != null) && usr.checkPassword(password)){
            return usr;
        } else {
            return null;
        }
    }

    @Override
    public void _initTestDatabase(){ //Fake database for debugging only
        this.addUser(new User("Monika","JustMonika",UserTypeEnum.ADMIN.toString()));// Just Monika [ok]
        this.addUser(new User("a","b",UserTypeEnum.ADMIN.toString()));
        this.addUser(new User("homelessdude","password",UserTypeEnum.USER.toString()));
        this.addUser(new User("shelterguy","gimmeshelter",UserTypeEnum.EMPLOYEE.toString())); //Denzel Washington rolling over an MD-80 not included.
    }
}
