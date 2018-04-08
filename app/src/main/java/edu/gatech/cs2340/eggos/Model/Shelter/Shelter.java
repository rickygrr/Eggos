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
    public void requestRoom(int cap){
        if((cap >= 0) && this.haveRoomFor(cap)){
            this._Capacity_current -= cap;
        } else {
        }
    }

    public void freeRoom(int cap){
        if((cap >= 0) && (cap <= (this._Capacity_max - this._Capacity_current))){
            this._Capacity_current += cap;
        } else {
        }
    }

    public String getNotes(){
        return _Notes;
    }
// --Commented out by Inspection START (08-Apr-18 15:57):
//    public void setNotes(String Notes){
//        this._Notes = Notes;
//    }
// --Commented out by Inspection STOP (08-Apr-18 15:57)

    public double[] getCoord() {
        return new double[] {this._lat, this._lon};
    }
// --Commented out by Inspection START (08-Apr-18 15:57):
//    public boolean setCoord(double[] coord){
//        if (coord.length != 2) {
//            return false;
//        } else {
//            this._lat = coord[0];
//            this._lon = coord[1];
//            return true;
//        }
//    }
// --Commented out by Inspection STOP (08-Apr-18 15:57)

    public String getAddr(){
        return this._Addr;
    }
// --Commented out by Inspection START (08-Apr-18 15:57):
//    public void setAddr(String addr){
//        this._Addr = addr;
//    }
// --Commented out by Inspection STOP (08-Apr-18 15:57)

    public String getPhone(){
        return this._Phone;
    }
// --Commented out by Inspection START (08-Apr-18 15:57):
//    public void setPhone(String phone){
//        this._Phone = phone;
//    }
// --Commented out by Inspection STOP (08-Apr-18 15:57)

    public String toString(){
        return this._Name;
    }


    @Override
    public int hashCode() {
        return this._UID;
    }

}
