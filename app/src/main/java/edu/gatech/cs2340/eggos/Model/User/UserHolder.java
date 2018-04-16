package edu.gatech.cs2340.eggos.Model.User;

import android.support.annotation.Nullable;

import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabaseInterface;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabase_room;

/**
 * UserHolder singleton to store the app's currently logged-in user
 * Created by chateau86 on 14-Feb-18.
 */

public final class UserHolder {
    private static final UserHolder ourInstance = new UserHolder();
    public static UserHolder getInstance() {
        return ourInstance;
    }
    private final UserDatabaseInterface _UserDBInstance;
    private final ShelterDatabaseInterface _ShelterDBInstance;

    @Nullable
    private User _currentUser;
    public UserHolder() {
        this._currentUser = null; //Might default to guest when we implement guest.
        //this._UserDBInstance = UserDatabase_local.getInstance();
        this._UserDBInstance = UserDatabase_room.getInstance();
        this._ShelterDBInstance = ShelterDatabase_room.getInstance();
    }

// --Commented out by Inspection START (08-Apr-18 15:57):
//    private void _setUser(User usr){
//        this._currentUser = usr;
//    }
// --Commented out by Inspection STOP (08-Apr-18 15:57)

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

    public void logout(){
        this._currentUser = null;
    }

    public void setCurrentOccupancy(int newShelterID, int newOccupancy){
        if (_currentUser == null) {
            return;
        }
        if(newOccupancy >= 0){
            int oldShelterID = _currentUser._currentShelterID;
            int oldOccupancy = _currentUser._currentOccupancy;

            _currentUser._currentOccupancy = newOccupancy;
            _currentUser._currentShelterID = newShelterID;
            if (newOccupancy == 0) {
                _currentUser._currentShelterID = -1;
            }
            _UserDBInstance.updateUser(_currentUser);

            _ShelterDBInstance.transferReservation(oldOccupancy, oldShelterID,
                                                   newOccupancy, newShelterID);
        }

    }

}
