package edu.gatech.cs2340.eggos.Model.Shelter;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by chateau86 on 26-Feb-18.
 */
@SuppressWarnings("PublicField")
//Because otherwise Room requires all the getters/setters with *very specific* naming scheme
//that just breaks everything.
@Entity(tableName = "Shelters")
public class Shelter {
    @PrimaryKey
    private final int _UID;
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

    /**
     * Detailed constructor for shelter object
     * @param _UID ID of the shelter
     * @param _Name name of the shelter
     * @param _Capacity_max maximum capacity of the shelter
     * @param _Capacity_current current capacity of the shelter
     * @param _GenderMask gender restrictions of the shelter
     * @param _AgeMask age restrictions of the shelter
     * @param _Notes notes on the shelter
     * @param _lat latitude of the shelter
     * @param _lon longitude of the shelter
     * @param _Addr address of the shelter
     * @param _Phone phone of the shelter
     */
    public Shelter(int _UID,
                   final String _Name,
                   int _Capacity_max,
                   int _Capacity_current,
                   int _GenderMask,
                   int _AgeMask,
                   String _Notes,
                   double _lat,
                   double _lon,
                   String _Addr,
                   String _Phone){
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

    /**
     * Simple constructor for shelter object
     * @param _UID ID of the shelter
     * @param _Name name of the shelter
     * @param _Capacity_max maximum capacity of the shelter
     * @param _AgeMask age restrictions of the shelter
     * @param _Notes notes on the shelter
     * @param _lat latitude of the shelter
     * @param _lon longitude of the shelter
     * @param _Addr address of the shelter
     * @param _Phone phone of the shelter
     */
    public Shelter(int _UID,
                   String _Name,
                   int _Capacity_max,
                   int _GendersMask,
                   int _AgeMask,
                   String _Notes,
                   double _lat,
                   double _lon,
                   String _Addr,
                   String _Phone){
        this(_UID, _Name, _Capacity_max, _Capacity_max, _GendersMask,
                _AgeMask, _Notes, _lat, _lon, _Addr, _Phone);
    }

    /**
     * Gets the ID of the shelter
     * @return the ID of the shelter
     */
    public int getUID() {
        return this._UID;
    }

    /**
     * Gets the name of the shelter
     * @return the name of the shelter
     */
    public String getName(){
        return this._Name;
    }

    /**
     * Gets the maximum capacity of shelter
     * @return maximum capacity of shelter
     */
    public int getMaxCap(){
        return this._Capacity_max;
    }

    /**
     * Gets the available capacity of shelter
     * @return current capacity of shelter
     */
    public int getAvailCap(){
        return (this._Capacity_current);
    }

    /**
     * Determines if there is enough room for a certain capacity
     * @param cap capacity to check if there is enough room for
     * @return whether or not there is enough room for the given capacity
     */
    public boolean haveRoomFor(int cap){
        return (cap >= 0 && this._Capacity_current >= cap);
    }

    /**
     * Requests a room for the given shelter
     * @param cap the capacity which will occupy the room
     */
    public void requestRoom(int cap){
        if((cap >= 0) && this.haveRoomFor(cap)){
            this._Capacity_current -= cap;
        }
    }

    /**
     * Free's room in the shelter
     * @param cap the capacity of the room to be freed
     */
    public void freeRoom(int cap){
        if((cap >= 0) && (cap <= (this._Capacity_max - this._Capacity_current))){
            this._Capacity_current += cap;
        }
    }

    /**
     * Get notes of the shelter
     * @return the notes on the shelter
     */
    public String getNotes(){
        return _Notes;
    }

    /**
     * Get coordinates of the shelter
     * @return the coordinates of the shelter
     */
    public double[] getCoord() {
        return new double[] {this._lat, this._lon};
    }

    /**
     * Gets the address of the shelter
     * @return the address of the shelter
     */
    public String getAddr(){
        return this._Addr;
    }

    /**
     * Gets the phone of the shelter
     * @return the phone of the shelter
     */
    public String getPhone(){
        return this._Phone;
    }

    /**
     * Simple toString of the shelter
     * @return the name of the shelter
     */
    public String toString(){
        return this._Name;
    }

    /**
     * Detailed toString of the shelter
     * @return the name, capacity, gender restrictions, age restrictions, notes, coordinates,
     * address, phone number, total capacity and available capacity of the shelter
     */
    public String toDetailedString(){
        String coord = "";
        for(int i = 0; i < this.getCoord().length; i++) {
            coord += this.getCoord()[i] + ", ";
        }
        return "Name: " + this.toString()
                + "\n" + "Capacity: " + this.getMaxCap()
                + "\n" + "Gender Restrictions: " + GenderEnum.mask2Enums(this._GenderMask)
                + "\n" + "Age Restrictions: " + AgeEnum.mask2Enums(this._AgeMask)
                + "\n" + "Notes: " + this.getNotes()
                + "\n" + "Coordinates: " + coord
                + "\n" + "Address " + this.getAddr()
                + "\n" + "Phone Number " + this.getPhone()
                + "\n" + "Total Capacity:" + this._Capacity_max
                + "\n" + "Available Capacity:" + this._Capacity_current;
    }

    /**
     * Hash code of the shelter's ID
     * @return the shelter's ID
     */
    @Override
    public int hashCode() {
        return this._UID;
    }



}
