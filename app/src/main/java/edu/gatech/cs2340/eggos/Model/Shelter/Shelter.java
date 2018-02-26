package edu.gatech.cs2340.eggos.Model.Shelter;

import java.util.HashSet;
import java.util.Set;

import edu.gatech.cs2340.eggos.Model.User.User;

/**
 * Created by chateau86 on 26-Feb-18.
 */

public class Shelter {
    private int _UID;
    private String _Name;
    private int _Capacity_max;
    private Set<User> _Staying_user;
    private int _Staying_nonuser; //_Staying_User+_Staying_Nonuser = occupancy
    private Set<String> _Restrictions; //TODO: Maybe make restrictions their own class?
    private Set<String> _Notes; //TODO: Maybe merge this with restriction?
    private double _lat;
    private double _lon;
    private String _Addr;
    private String _Phone;

    public Shelter(int UID, String name, int capacity, Set<String> restrictions, Set<String> notes, double lat, double lon, String addr, String phone){
        //TODO
        this._UID = UID;
        this._Name = name;
        this._Capacity_max = capacity;
        this._Restrictions = restrictions;
        this._Notes = notes;
        this._lat = lat;
        this._lon = lon;
        this._Addr = addr;
        this._Phone = phone;
        this._Staying_nonuser = 0;
        this._Staying_user = new HashSet<User>();
    }

    public int getUID() {
        return this._UID;
    }
    public String getName(){
        return this._Name;
    }
    public int getMaxCapacity(){
        return this._Capacity_max;
    }
    public int getAvailability(){
        return (this._Capacity_max - (this._Staying_nonuser + this._Staying_user.size()));
    }
    public boolean addUser(User usr){
        //TODO
        return false;
    }
}
