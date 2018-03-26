package edu.gatech.cs2340.eggos.Model.Shelter;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.HashSet;
import java.util.Set;

import edu.gatech.cs2340.eggos.Model.User.User;

/**
 * Created by chateau86 on 26-Feb-18.
 */

@Entity(tableName = "Shelters")
public class Shelter {
    @PrimaryKey
    private int _UID;
    public final String _Name;
    public int _Capacity_max;
    public int _Capacity_current;
    //private Set<User> _Staying_user;
    //private int _Staying_nonuser; //_Staying_User+_Staying_Nonuser = occupancy
    public String _Restrictions; //TODO: Maybe make restrictions their own class?
    public String _Genders;
    public String _Age;
    public String _Notes; //TODO: Maybe merge this with restriction?
    public double _lat;
    public double _lon;
    public String _Addr;
    public String _Phone;

    public Shelter(int UID, String name, int capacity, String restrictions, String genders, String age, String notes, double lat, double lon, String addr, String phone){
        this._UID = UID;
        this._Name = name;
        this._Capacity_max = capacity;
        this._Capacity_current = capacity;
        //this._Staying_nonuser = 0;
        //this._Staying_user = new HashSet<User>();
        this._Restrictions = restrictions;
        this._Genders = genders;
        this._Age = age;
        this._Notes = notes;
        this._lat = lat;
        this._lon = lon;
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
        return (this._Capacity_current);
    }
    public boolean haveRoomFor(int cap){
        return (this._Capacity_current >= cap);
    }
    public boolean requestRoom(int cap){
        if(cap >= 0 && this.haveRoomFor(cap)){
            this._Capacity_current -= cap;
            return true;
        } else {
            return false;
        }
    }

    public boolean freeRoom(int cap){
        if(cap >= 0 && cap <= (this._Capacity_max-this._Capacity_current)){
            this._Capacity_current += cap;
            return true;
        } else {
            return false;
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

    public Set<String> getGender(){
        return _Genders;
    }
    public boolean addGender(String res){
        return this._Genders.add(res);
    }
    public boolean removeGender(String res){
        return this._Genders.remove(res);
    }

    public Set<String> getAge(){
        return _Age;
    }
    public boolean addAge(String res){
        return this._Age.add(res);
    }
    public boolean removeAge(String res){
        return this._Age.remove(res);
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
