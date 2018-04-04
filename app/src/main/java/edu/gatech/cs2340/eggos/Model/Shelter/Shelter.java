package edu.gatech.cs2340.eggos.Model.Shelter;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

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
    public int _GenderMask;
    public int _AgeMask;
    public String _Notes;
    public double _lat;
    public double _lon;
    public String _Addr;
    public String _Phone;

    public Shelter(int _UID, final String _Name, int _Capacity_max, int _Capacity_current, int _GenderMask, int _AgeMask, String _Notes, double _lat, double _lon, String _Addr, String _Phone){
        this._UID = _UID;
        this._Name = _Name;
        this._Capacity_max = _Capacity_max;
        this._Capacity_current = _Capacity_current; //empty beds available
        this._GenderMask = _GenderMask;
        this._AgeMask = _AgeMask;
        this._Notes = _Notes;
        this._lat = _lat;
        this._lon = _lon;
        this._Addr = _Addr;
        this._Phone = _Phone;
    }
    public Shelter(int _UID, String _Name, int _Capacity_max, int _GendersMask, int _AgeMask, String _Notes, double _lat, double _lon, String _Addr, String _Phone){
        this(_UID, _Name, _Capacity_max, _Capacity_max, _GendersMask, _AgeMask, _Notes, _lat, _lon, _Addr, _Phone);
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
        if((cap >= 0) && this.haveRoomFor(cap)){
            this._Capacity_current -= cap;
            return true;
        } else {
            return false;
        }
    }

    public boolean freeRoom(int cap){
        if((cap >= 0) && (cap <= (this._Capacity_max - this._Capacity_current))){
            this._Capacity_current += cap;
            return true;
        } else {
            return false;
        }
    }

    public String getNotes(){
        return _Notes;
    }
    public boolean setNotes(String Notes){
        this._Notes = Notes;
        return true;
    }

    public double[] getCoord() {
        return new double[] {this._lat, this._lon};
    }
    public boolean setCoord(double[] coord){
        if (coord.length != 2) {
            return false;
        } else {
            this._lat = coord[0];
            this._lon = coord[1];
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
