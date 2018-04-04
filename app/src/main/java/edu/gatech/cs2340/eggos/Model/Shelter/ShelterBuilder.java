package edu.gatech.cs2340.eggos.Model.Shelter;

public class ShelterBuilder {
    private int uid;
    private String name;
    private int capacity;
    //private String restrictions;
    private int gendersMask;
    private int ageMask;
    private String notes;
    private double lat;
    private double lon;
    private String addr;
    private String phone;

    public ShelterBuilder setUID(int uid) {
        this.uid = uid;
        return this;
    }

    public ShelterBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ShelterBuilder setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    /*public ShelterBuilder setRestrictions(String restrictions) {
        this.restrictions = restrictions;
        return this;
    }*/

    public ShelterBuilder setGendersMask(int gendersMask) {
        this.gendersMask = gendersMask;
        return this;
    }

    public ShelterBuilder setAgeMask(int ageMask) {
        this.ageMask = ageMask;
        return this;
    }

    public ShelterBuilder setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public ShelterBuilder setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public ShelterBuilder setLon(double lon) {
        this.lon = lon;
        return this;
    }

    public ShelterBuilder setCoord(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
        return this;
    }

    public ShelterBuilder setAddr(String addr) {
        this.addr = addr;
        return this;
    }

    public ShelterBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Shelter createShelter() {
        return new Shelter(uid, name, capacity, gendersMask, ageMask, notes, lat, lon, addr, phone);
    }
}