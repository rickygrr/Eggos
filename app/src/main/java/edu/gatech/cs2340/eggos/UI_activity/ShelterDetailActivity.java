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
    @SuppressWarnings({"FeatureEnvy", "ChainedMethodCall"})
    //ChainedMethodCall: Intent API is just pure stupidity all the way down.
    //FeatureEnvy: @SuppressWarnings on inner class/object does not count for parent.
    //Maybe they shouldn't have dropped the linter on its head when it was born after all.
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

                @SuppressWarnings("FeatureEnvy")
                //It's a user*Holder* FFS. We will of course be getting the User from it
                // (1 operation down). Somehow that consumes our whole object access quota.
                @Override
                public void onClick(View v) {
                    //Set User with this shelter and remove beds from shelter
                    int numBeds = Integer.parseInt(numBedsEditText.getText().toString());
                    //Log.e("ShelterDetailActivity",
                    //   "Bed update clicked with shelter: "+shelterID+" Bedcount: "+numBeds);
                    Shelter currShelter = ShelterDBInstance.getShelterByID(shelterID);
                    UserHolder UsrHolder = UserHolder.getInstance();
                    User u = UsrHolder.getUser();
                    boolean avail = false;
                    if(u._currentShelterID == shelterID){
                        //same shelter
                        avail = currShelter.haveRoomFor(numBeds - u._currentOccupancy);
                    } else {
                        avail = currShelter.haveRoomFor(numBeds);
                    }
                    if(!avail){
                        //Log.e("ShelterDetailActivity",
                        //        "Not enough room: Want :"+numBeds
                        //                +" have: "+currShelter._Capacity_current);
                        //Because fixing suspected FeatureEnvy > Actually have good debugging info.
                        numBedsEditText.setError("Insufficient bed availability");
                    } else {
                        //Have the room
                        UsrHolder.setCurrentOccupancy(shelterID, numBeds);
                    }
                    updateShelterNumber();

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
        UserHolder usrHolder = UserHolder.getInstance();
        User usr = usrHolder.getUser();
        Shelter currShelter = ShelterDBInstance.getShelterByID(shelterID);

        String details = currShelter.toDetailedString();
        //And now the UI-related stringification code for Shelter is in Shelter class instead of
        // directly-related UI class.
        // Linter gods shall be nourished, regardless of actual maintainability.

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
