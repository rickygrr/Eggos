package edu.gatech.cs2340.eggos.Model.Shelter;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.SparseArray;
import android.content.res.Resources;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.ArrayList;

import edu.gatech.cs2340.eggos.R;

/**
 * Created by chateau86 on 26-Feb-18.
 */

public class ShelterDatabase {
    private static final ShelterDatabase ourInstance = new ShelterDatabase();

    private SparseArray<Shelter> _ShelterList;
    private Shelter _currentShelter;
    private boolean _jsonReadDone;

    public static ShelterDatabase getInstance() {
        return ourInstance;
    }

    private ShelterDatabase() {
        this._ShelterList = new SparseArray<Shelter>();
        //this._initTestDatabase();
        this._jsonReadDone = false;
    }

    /**
     *
     * @return  the currently selected course
     */
    public Shelter getCurrentShelter() { return _currentShelter;}

    public void setCurrentShelter(Shelter shelter) { _currentShelter = shelter; }

    public boolean addShelter(Shelter s){
        if(_ShelterList.get(s.getUID()) != null){
            return false; //duplicate
        }
        _ShelterList.append(s.getUID(), s);
        return true;
    }

    public int getNextShelterID(){
        //For shelter addition without manual UID
        return _ShelterList.keyAt(_ShelterList.size()-1) + 1;
    }

    public ArrayList<Shelter> getShelterList(){ //Copy the content of this function for filtering implementations.
        return this.getFilteredShelterList(new ShelterDatabaseFilter() {
            @Override
            public boolean keepShelter(Shelter s) {
                return true;
            }
        });
    }


    public ArrayList<Shelter> getFilteredShelterList(ShelterDatabaseFilter filt){
        /*
        "Wow, talk about a completely unloved class, conforms to ZERO collection interfaces..."
             -user166390, https://stackoverflow.com/questions/7999211/how-to-iterate-through-sparsearray
         */
        //SparseArray<> is now officially my spirit animal. :(
        //Pls get me in the screenshot for the obligatory /r/me_irl post - W.K.
        ArrayList<Shelter> outList = new ArrayList<Shelter>();
        for(int i = 0; i < _ShelterList.size(); i++){
            if(filt.keepShelter(_ShelterList.valueAt(i))) {
                outList.add(_ShelterList.valueAt(i));
            }
        }
        return outList;
    }

    private static ArrayList<Shelter> SparseArrToArrayList(SparseArray<Shelter> sp){
        if(sp==null){ return null;}
        ArrayList<Shelter> ar = new ArrayList<>(sp.size());
        for(int i = 0; i < sp.size(); i++){
            ar.add(sp.get(sp.keyAt(i)));
        }
        return ar;
    }

    public void _initTestDatabase(){
        this.addShelter(new Shelter(0,"Test Shelter", 20, "blah", new HashSet<String>(){}, new double[]{420.0,69.0}, "123 Fake St.\n 42069", "867-5309" ));
        this.addShelter(new Shelter(1,"Test Shelter 2", 420, "blah", new HashSet<String>(){}, new double[]{123.0,45.0}, "124 Fake St.\n 42069", "531-8008" ));
    }

    public void initFromJSON(Context cont)throws IOException {
        if (_jsonReadDone){
            return;
        }
        _jsonReadDone = true;
        InputStream f_in = cont.getResources().openRawResource(R.raw.shelter);
        JsonReader reader = new JsonReader(new InputStreamReader(f_in, "UTF-8"));
        reader.beginArray();
        while(reader.hasNext()){
            _readShelter(reader);
        }
        reader.endArray();
    }

    private void _readShelter(JsonReader reader) throws IOException{
        reader.beginObject();
        String addr = "", name = "", phone = "", restriction = "";
        int uid = 0, cap = 0;
        double lat = 0, lon = 0;
        HashSet<String> notes = new HashSet<>();

        while(reader.hasNext()){
            String token_name = reader.nextName();
            switch(token_name){
                case "Name":
                    name = reader.nextString();
                    break;
                case "Address":
                    addr = reader.nextString();
                    break;
                case "Phone":
                    phone = reader.nextString();
                    break;
                case "Restriction":
                    restriction = reader.nextString();
                    break;
                case "UID":
                    uid = reader.nextInt();
                    break;
                case "Capacity":
                    cap = reader.nextInt();
                    break;
                case "Lat":
                    lat = reader.nextDouble();
                    break;
                case "Lon":
                    lon = reader.nextDouble();
                    break;
                case "Notes":
                    reader.beginArray();
                    while(reader.hasNext()){
                        notes.add(reader.nextString());
                    }
                    reader.endArray();
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        this.addShelter(new Shelter(uid, name, cap, restriction, notes, new double[]{lat, lon}, addr, phone));
    }
    //TODO: Read CSV/JSON
}