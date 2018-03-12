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
    private String _Restrictions; //TODO: Maybe make restrictions their own class?
    private Set<String> _Notes; //TODO: Maybe merge this with restriction?
    private double[] _coord;
    private String _Addr;
    private String _Phone;

    public Shelter(int UID, String name, int capacity, String restrictions, Set<String> notes, double[] coord, String addr, String phone){
        //TODO
        this._UID = UID;
        this._Name = name;
        this._Capacity_max = capacity;
        this._Staying_nonuser = 0;
        this._Staying_user = new HashSet<User>();
        this._Restrictions = restrictions;
        this._Notes = notes;
        this._coord = coord;
        this._Addr = addr;
        this._Phone = phone;
    }

    public int getUID() {
        return this._UID;
    }
    public String getName(){
        return this._Name;
    }
    public int getMaxCap(){
        return this._Capacity_max;
    }
    public int getAvailCap(){
        return (this._Capacity_max - (this._Staying_nonuser + this._Staying_user.size()));
    }
    public boolean addUser(User usr){
        return this._Staying_user.add(usr);
    }
    public boolean removeUser(User usr){
        return this._Staying_user.remove(usr);
    }
    public boolean adjustOccupancy(int newOccupancy){
        //Adjust _staying_nonuser to match availability
        int adjusted_nonuser = newOccupancy - this._Staying_user.size();
        if (adjusted_nonuser < 0){
            return false;
        } else {
            this._Staying_nonuser = adjusted_nonuser;
            return true;
        }
    }

    public String getRestrictions(){
        return _Restrictions;
    }
    public boolean setRestrictions(String restrictions){
        this._Restrictions = restrictions;
        return true;
    }

    public Set<String> getNotes(){
        return _Notes;
    }
    public boolean setNotes(Set<String> Notes){
        this._Notes = Notes;
        return true;
    }
    public boolean addNote(String res){
        return this._Notes.add(res);
    }
    public boolean removeNote(String res){
        return this._Notes.remove(res);
    }

    public double[] getCoord() {
        return this._coord;
    }
    public boolean setCoord(double[] coord){
        if (coord.length != 2) {
            return false;
        } else {
            this._coord = coord;
            return true;
        }
    }

    public String getAddr(){
        return this._Addr;
    }
    public boolean setAddr(String addr){
        this._Addr = addr;
        return true;
    }

    public String getPhone(){
        return this._Phone;
    }
    public boolean setPhone(String phone){
        this._Phone = phone;
        return true;
    }

    public String toString(){
        return this._Name;
    }


    @Override
    public int hashCode() {
        return this._UID;
    }

}
