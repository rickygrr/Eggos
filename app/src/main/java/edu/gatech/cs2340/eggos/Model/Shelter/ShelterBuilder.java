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

    /**
     * Sets the ID of the shelter
     * @param uid ID of the shelter
     * @return shelter
     */
    public ShelterBuilder setUID(int uid) {
        this.uid = uid;
        return this;
    }

    /**
     * Sets the name of the shelter
     * @param name name of the shelter
     * @return shelter
     */
    public ShelterBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the capacity of the shelter
     * @param capacity capacity of the shelter
     * @return shelter
     */
    public ShelterBuilder setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    /*public ShelterBuilder setRestrictions(String restrictions) {
        this.restrictions = restrictions;
        return this;
    }*/

    /**
     * Set gender restriction of the shelter
     * @param gendersMask gender restriction of the shelter
     * @return shelter
     */
    public ShelterBuilder setGendersMask(int gendersMask) {
        this.gendersMask = gendersMask;
        return this;
    }

    /**
     * Set age restriction of the shelter
     * @param ageMask age restriction of the shelter
     * @return shelter
     */
    public ShelterBuilder setAgeMask(int ageMask) {
        this.ageMask = ageMask;
        return this;
    }

    /**
     * Set notes of the shelter
     * @param notes notes of the shelter
     * @return shelter
     */
    public ShelterBuilder setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    /**
     * Set latitude of the shelter
     * @param lat latitude of the shelter
     * @return shelter
     */
    public ShelterBuilder setLat(double lat) {
        this.lat = lat;
        return this;
    }

    /**
     * Set longitude of the shelter
     * @param lon longitude of the shelter
     * @return shelter
     */
    public ShelterBuilder setLon(double lon) {
        this.lon = lon;
        return this;
    }

    /**
     * Set coordinates of the shelter
     * @param lat latitude of the shelter
     * @param lon longitude of the shelter
     * @return shelter
     */
    public ShelterBuilder setCoord(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
        return this;
    }

    /**
     * Set address of the shelter
     * @param addr address of the shelter
     * @return shelter
     */
    public ShelterBuilder setAddr(String addr) {
        this.addr = addr;
        return this;
    }

    /**
     * Set phone of the shelter
     * @param phone phone of the shelter
     * @return shelter
     */
    public ShelterBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * Creates a new shelter
     * @return new shelter
     */
    public Shelter createShelter() {
        return new Shelter(uid, name, capacity, gendersMask, ageMask, notes, lat, lon, addr, phone);
    }
}