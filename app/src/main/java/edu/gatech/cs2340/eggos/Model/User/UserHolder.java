package edu.gatech.cs2340.eggos.Model.User;

import edu.gatech.cs2340.eggos.Model.Shelter.Shelter;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabaseInterface;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabase_room;

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
    private ShelterDatabaseInterface _ShelterDBInstance;

    private User _currentUser;
    private UserHolder() {
        this._currentUser = null; //Might default to guest when we implement guest.
        //this._UserDBInstance = UserDatabase_local.getInstance();
        this._UserDBInstance = UserDatabase_room.getInstance();
        this._ShelterDBInstance = ShelterDatabase_room.getInstance();
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

    public boolean setCurrentOccupancy(int newShelterID, int newOccupancy){
        if (_currentUser == null) {
            return false;
        }
        if(newOccupancy < 0){
            return false;
        } else {
            //TODO notify shelter
            if(_currentUser._currentShelterID != -1) {
                //return beds
                Shelter s = _ShelterDBInstance.getShelterByID(_currentUser._currentShelterID);
                s.freeRoom(_currentUser._currentOccupancy);
                _ShelterDBInstance.updateShelter(s);
            }

            _currentUser._currentOccupancy = newOccupancy;
            _currentUser._currentShelterID = newShelterID;

            if (newOccupancy == 0) {
                _currentUser._currentShelterID = -1;
            } else {
                Shelter s = _ShelterDBInstance.getShelterByID(_currentUser._currentShelterID);
                s.requestRoom(_currentUser._currentOccupancy);
                _ShelterDBInstance.updateShelter(s);
            }
            //notify db update
            _UserDBInstance.updateUser(_currentUser);


            return true;
        }

    }

}
