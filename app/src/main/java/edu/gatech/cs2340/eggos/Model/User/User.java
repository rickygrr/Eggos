package edu.gatech.cs2340.eggos.Model.User;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * User object
 * Setters will return true if successful, false if failed.
 * Created by chateau86 on 14-Feb-18.
 */
@SuppressWarnings("PublicField")
//Because otherwise Room requires all the getters/setters with *very specific* naming scheme
//that just breaks everything.
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
    public int _currentOccupancy;

    /**
     * Simple constructor for user object
     * @param username username of user
     * @param password password of user
     * @param type type of user
     */
    public User(String username, String password, String type){
        this(username, password, type, -1, 0);
    }

    /**
     * Detailed constructor for user object
     * @param _Username username of user
     * @param _Password password of user
     * @param _UserType type of user
     * @param _currentShelterID current shelter's ID
     * @param _currentOccupancy current occupancy of shelter
     */
    public User(String _Username,
                String _Password,
                String _UserType,
                int _currentShelterID,
                int _currentOccupancy){
        this._Username = _Username;
        this._Password = _Password;
        this._UserType = _UserType;
        this._currentShelterID = _currentShelterID;
        this._currentOccupancy = _currentOccupancy;
    }

    /**
     * Get username of user
     * @return username of user
     */
    public String getUsername(){
        return this._Username;
    }
// --Commented out by Inspection START (08-Apr-18 15:57):
//    public String get_Password(){
//        return this._Password;
//    }
// --Commented out by Inspection STOP (08-Apr-18 15:57)

    /**
     * Check password of user
     * @param pass password of user
     * @return whether or not the user's password is correct
     */
    public boolean checkPassword(String pass){
        //No string getter provided for obvious reason
        return this._Password.equals(pass);
    }
// --Commented out by Inspection START (08-Apr-18 15:57):
//    public boolean changePassword(String oldPass, String newPass){
//        if (this.checkPassword(oldPass)){
//            this._Password = newPass;
//            return true;
//        } else {
//            return false;
//        }
//    }
// --Commented out by Inspection STOP (08-Apr-18 15:57)
// --Commented out by Inspection START (08-Apr-18 15:57):
//    public String getUserType(){
//        return this._UserType;
//    }
// --Commented out by Inspection STOP (08-Apr-18 15:57)
// --Commented out by Inspection START (08-Apr-18 15:57):
//    public void setUserType(String newType){
//        this._UserType = newType;
//    }
// --Commented out by Inspection STOP (08-Apr-18 15:57)

    /**
     * Simple toString of user
     * @return the user's username and type
     */
    public String toString(){
        return "User name: "+this._Username+"\nType: "+this._UserType;
    }

    /**
     * Hash code of the user's username
     * @return hashcode username of user
     */
    @Override
    public int hashCode(){ //For HashSet use
        return this._Username.hashCode();
    }

// --Commented out by Inspection START (08-Apr-18 15:57):
//    public void setShelter(int s) {
//        this._currentShelterID = s;
//    }
// --Commented out by Inspection STOP (08-Apr-18 15:57)

   /* public int getShelterID(){
        return _currentShelterID;
    }
    public boolean setShelterID(int shelterID){
        if(this._currentOccupancy > 0){
            return false;
        } else {
            this._currentShelterID = shelterID;
            return true;
        }

    }*/
}
