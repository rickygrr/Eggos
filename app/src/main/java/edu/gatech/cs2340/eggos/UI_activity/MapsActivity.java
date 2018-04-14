package edu.gatech.cs2340.eggos.UI_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import edu.gatech.cs2340.eggos.Model.Shelter.Shelter;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabaseInterface;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabase_room;
import edu.gatech.cs2340.eggos.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Shelter> shelterList;
    private Iterable<Integer> shelterUIDList;
    private final Deque<Marker> markerList = new LinkedList<>();
    ShelterDatabaseInterface ShelterDBInstance = ShelterDatabase_room.getInstance();

    @SuppressWarnings("ChainedMethodCall")
    //ChainedMethodCall: Android Activity Class being a mess? Who'd have thought.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Bundle b = getIntent().getExtras();
        shelterUIDList = b.getIntegerArrayList("ShelterUIDList");
        shelterList = ShelterDBInstance.unpackShelterList(shelterUIDList);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Shelter s = (Shelter)marker.getTag();
                Log.e("MapActivity","Marker clicked at: "+s);
                Context context = getApplicationContext();
                Intent intent = new Intent(context, ShelterDetailActivity.class);
                Bundle b = new Bundle();
                b.putInt("uid", s.getUID());
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
        /*// Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions()
                                .position(sydney)
                                .title("Marker in Sydney")
                                .snippet("Bleugh lelel."));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
        regenMarker();

    }

    @SuppressWarnings({"FeatureEnvy", "ChainedMethodCall"})
    //FeatureEnvy: Shelter is an information holder that happens to hold 5 things,
    // more than the 3 access limit for this check.
    //ChainedMethodCall: "Builder pattern considered harmful."-Linter (probably)
    private void regenMarker(){
        final double YELLOW_FRACTION_LIMIT = 0.75;
        final int YELLOW_BED_LIMIT = 10;
        final double RED_FRACTION_LIMIT = 0.99;
        final int RED_BED_LIMIT = 5;
        final int ZOOM_LEVEL = 12;
        if (mMap == null){
            return;
        }
        //Cleanup old markers
        while(!markerList.isEmpty()) {
            markerList.pop().remove();
        }
        //add new markers
        double avgLat = 0;
        double avgLon = 0;

        for(Shelter s: shelterList){
            LatLng loc = new LatLng(s._lat, s._lon);
            avgLat += loc.latitude;
            avgLon += loc.longitude;
            int availCap = s.getAvailCap();
            int capMax = s._Capacity_max;
            double fracUsed = 1 - (availCap/(double)capMax);
            BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_GREEN);

            if((fracUsed >= RED_FRACTION_LIMIT) || (s.getAvailCap() < RED_BED_LIMIT)){
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            } else if ((fracUsed >= YELLOW_FRACTION_LIMIT)
                    || (s.getAvailCap() < YELLOW_BED_LIMIT)) {
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
            }
            Marker m = mMap.addMarker(new MarkerOptions()
                    .position(loc)
                    .title(s.getName())
                    .snippet(availCap+"/"+capMax)
                    .icon(icon)
            );
            m.setTag(s);
            markerList.add(m);
        }
        avgLat /= shelterList.size();
        avgLon /= shelterList.size();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(avgLat, avgLon), ZOOM_LEVEL));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //update shelter objects to current value in db.
        shelterList = ShelterDBInstance.unpackShelterList(shelterUIDList);
        regenMarker();
    }

}
