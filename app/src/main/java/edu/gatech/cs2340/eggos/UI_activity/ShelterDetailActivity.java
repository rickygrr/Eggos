package edu.gatech.cs2340.eggos.UI_activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

import edu.gatech.cs2340.eggos.Model.Shelter.AgeEnum;
import edu.gatech.cs2340.eggos.Model.Shelter.GenderEnum;
import edu.gatech.cs2340.eggos.Model.Shelter.Shelter;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabaseInterface;
//import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabase_local;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabase_room;
import edu.gatech.cs2340.eggos.Model.User.User;
import edu.gatech.cs2340.eggos.Model.User.UserHolder;
import edu.gatech.cs2340.eggos.R;

/**
 * Created by Sheetal on 3/4/2018.
 */

public class ShelterDetailActivity extends AppCompatActivity {
    //public static final String SHELTER_UID = "shelter_uid";
    private ShelterDatabaseInterface ShelterDBInstance;
    private int shelterID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);

        //ShelterDBInstance = ShelterDatabase_local.getInstance();
        ShelterDBInstance = ShelterDatabase_room.getInstance();
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activityz
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            Log.d("hi", "Reached Top of no savedInstance State");
            Bundle b = getIntent().getExtras();
            //int uid = b.getInt("uid");
            shelterID = b.getInt("uid");
            final Button reserveButton = findViewById(R.id.reserveButton);
            final Button applyButton = findViewById(R.id.applyButton);
            final EditText numBedsEditText = findViewById(R.id.numBedsEditText);
            final TextView numBedsText = findViewById(R.id.numBedsText);

            updateShelterNumber();

            reserveButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    updateShelterNumber();
                    numBedsEditText.setVisibility(View.VISIBLE);
                    numBedsText.setVisibility(View.VISIBLE);
                    applyButton.setVisibility(View.VISIBLE);
                    reserveButton.setVisibility(View.INVISIBLE);
                }
            });

            applyButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    //Set User with this shelter and remove beds from shelter
                    int numBeds = Integer.parseInt(numBedsEditText.getText().toString());
                    Log.e("ShelterDetailActivity",
                        "Bed update clicked with shelter: "+shelterID+" Bedcount: "+numBeds);
                    Shelter currShelter = ShelterDBInstance.getShelterByID(shelterID);
                    User u = UserHolder.getInstance().getUser();
                    boolean avail = false;
                    if(u._currentShelterID == shelterID){
                        //same shelter
                        avail = currShelter.haveRoomFor(numBeds - u._currentOccupancy);
                    } else {
                        avail = currShelter.haveRoomFor(numBeds);
                    }
                    if(!avail){
                        Log.e("ShelterDetailActivity",
                                "Not enough room: Want :"+numBeds
                                        +" have: "+currShelter._Capacity_current);
                        numBedsEditText.setError("Insufficient bed availability");
                        //Error bubble or something
                    } else {
                        //Have the room
                        UserHolder.getInstance().setCurrentOccupancy(shelterID, numBeds);
                    }
                    updateShelterNumber();
                    //User.setShelter(currShelter)
                    //currShelter.removeBeds(numBeds)

                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, DummyAppActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    private void updateShelterNumber(){
        EditText numBedsEditText = findViewById(R.id.numBedsEditText);
        TextView ShelterDetails = findViewById(R.id.shelterDetailsTextView);
        User usr = UserHolder.getInstance().getUser();
        Shelter currShelter = ShelterDBInstance.getShelterByID(shelterID);
        String coord = "";
        for(int i = 0; i < currShelter.getCoord().length; i++) {
            coord += currShelter.getCoord()[i] + ", ";
        }
        String details = "Name: " + currShelter.toString()
                + "\n" + "Capacity: " + currShelter.getMaxCap()
                + "\n" + "Gender Restrictions: " + GenderEnum.mask2Enums(currShelter._GenderMask)
                + "\n" + "Age Restrictions: " + AgeEnum.mask2Enums(currShelter._AgeMask)
                + "\n" + "Notes: " + currShelter.getNotes()
                + "\n" + "Coordinates: " + coord
                + "\n" + "Address " + currShelter.getAddr()
                + "\n" + "Phone Number " + currShelter.getPhone()
                + "\n" + "Total Capacity:" + currShelter._Capacity_max
                + "\n" + "Available Capacity:" + currShelter._Capacity_current;
        ShelterDetails.setText(details);
        if(usr._currentShelterID == shelterID) {
            numBedsEditText.setText(Integer.toString(usr._currentOccupancy));
        } else {
            numBedsEditText.setText("0");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateShelterNumber();
    }
}
