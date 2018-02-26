package edu.gatech.cs2340.eggos.Model.User;

/**
 * User object
 * Setters will return true if successful, false if failed.
 * Created by chateau86 on 14-Feb-18.
 */

public class User {
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MIN_PASSWORD_LENGTH = 3;

    private final String _Username;
    private String _Password;
    private UserTypeEnum _UserType;

    public User(String username, String password, UserTypeEnum type){
        this._Username = username;
        this._Password = password;
        this._UserType = type;
    }
    public String getUsername(){
        return this._Username;
    }
    public boolean checkPassword(String pass){
        //No string getter provided for obvious reason
        return this._Password.equals(pass);
    }
    public boolean changePassword(String oldPass, String newPass){
        if (this.checkPassword(oldPass)){
            this._Password = newPass;
            return true;
        } else {
            return false;
        }
    }
    public UserTypeEnum getUserType(){
        return this._UserType;
    }
    public boolean setUserType(UserTypeEnum newType){
        this._UserType = newType;
        return true;
    }

    public String toString(){
        return "User name: "+this._Username+"\nType: "+this._UserType;
    }

    @Override
    public int hashCode(){ //For HashSet use
        return this._Username.hashCode();
    }
}
