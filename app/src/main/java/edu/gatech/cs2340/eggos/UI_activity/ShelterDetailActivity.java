package edu.gatech.cs2340.eggos.UI_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import edu.gatech.cs2340.eggos.Model.Shelter.Shelter;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabaseInterface;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabase_local;
import edu.gatech.cs2340.eggos.R;

/**
 * Created by Sheetal on 3/4/2018.
 */

public class ShelterDetailActivity extends AppCompatActivity {
    //public static final String SHELTER_UID = "shelter_uid";
    private ShelterDatabaseInterface ShelterDBInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);

        ShelterDBInstance = ShelterDatabase_local.getInstance();
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
            Log.d("hi", "Reached Top of no savedInstance STate");
            Bundle b = getIntent().getExtras();
            int uid = b.getInt("uid");

            Shelter currShelter = ShelterDBInstance.getShelterByID(uid);

            TextView ShelterDetails = (TextView) findViewById(R.id.shelterDetailsTextView);
            String coord = "";
            for(int i = 0; i < currShelter.getCoord().length; i++) {
                coord += currShelter.getCoord()[i] + ", ";
            }
            String details = "Name: " + currShelter.toString()
                    + "\n" + "Capacity: " + currShelter.getMaxCap()
                    + "\n" + "Restrictions: " + currShelter.getRestrictions()
                    + "\n" + "Gender Restrictions: " + currShelter.getGender().toString()
                    + "\n" + "Age Restrictions: " + currShelter.getAge().toString()
                    + "\n" + "Notes: " + currShelter.getNotes().toString()
                    + "\n" + "Coordinates: " + coord
                    + "\n" + "Address " + currShelter.getAddr()
                    + "\n" + "Phone Number " + currShelter.getPhone();

            ShelterDetails.setText(details);

            /* arguments.putInt(ShelterDetailFragment.SHELTER_UID,
                    getIntent().getIntExtra(ShelterDetailFragment.SHELTER_UID, 0)); */

            /*ShelterDetailFragment fragment = new ShelterDetailFragment();
            fragment.setArguments(arguments);
            Log.d("hi", "Reached Bottom of no savedInstance STate");
            getSupportFragmentManager().beginTransaction()
                    .add(fragment, "blah")
                    .commit(); */
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
}
