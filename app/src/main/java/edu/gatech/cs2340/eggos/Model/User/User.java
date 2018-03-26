package edu.gatech.cs2340.eggos.Model.User;

import edu.gatech.cs2340.eggos.Model.Shelter.Shelter;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

/**
 * User object
 * Setters will return true if successful, false if failed.
 * Created by chateau86 on 14-Feb-18.
 */
@Entity(tableName = "Users")
public class User {
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MIN_PASSWORD_LENGTH = 3;

    @PrimaryKey
    @NonNull
    public final String _Username; //All public due to Room requirements
    public String _Password;
    public String _UserType;
    public int _currentShelterID;

    public User(String username, String password, String type){
        this(username, password, type, -1);
    }
    public User(String _Username, String _Password, String _UserType, int _currentShelterID){
        this._Username = _Username;
        this._Password = _Password;
        this._UserType = _UserType;
        this._currentShelterID = _currentShelterID;
    }

    public String getUsername(){
        return this._Username;
    }
    public String get_Password(){
        return this._Password;
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
    public String getUserType(){
        return this._UserType;
    }
    public boolean setUserType(String newType){
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

    public boolean setShelter(int s) {
        this._currentShelterID = s;
        return true;
    }

    public int getShelterID(){
        return _currentShelterID;
    }

}
