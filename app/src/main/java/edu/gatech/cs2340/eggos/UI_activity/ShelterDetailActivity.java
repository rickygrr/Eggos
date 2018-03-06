package edu.gatech.cs2340.eggos.UI_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import edu.gatech.cs2340.eggos.Model.Shelter.Shelter;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabase;
import edu.gatech.cs2340.eggos.Model.User.UserHolder;
import edu.gatech.cs2340.eggos.R;

/**
 * Created by Sheetal on 3/4/2018.
 */

public class ShelterDetailActivity extends AppCompatActivity {
    //public static final String SHELTER_UID = "shelter_uid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("hi", "Reached AAAShelterDetailActivity");
        setContentView(R.layout.activity_shelter_detail);
        Log.d("hi", "Reached ShelterDetailActivity");


        //Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        //setSupportActionBar(toolbar);

        /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Creating a new Student", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getBaseContext(), EditStudentActivity.class);
                startActivity(intent);
            }
        }); */

        // Show the Up button in the action bar.
        /* ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } */

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activityz
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        Log.d("hi", "Reached Before of no savedInstance STate");
        assert savedInstanceState == null;
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            Log.d("hi", "Reached Top of no savedInstance STate");
            Bundle arguments = new Bundle();
            arguments.putInt(ShelterDetailFragment.SHELTER_UID,
                    getIntent().getIntExtra(ShelterDetailFragment.SHELTER_UID, 0));

            ShelterDetailFragment fragment = new ShelterDetailFragment();
            fragment.setArguments(arguments);
            Log.d("hi", "Reached Bottom of no savedInstance STate");
            getSupportFragmentManager().beginTransaction()
                    .add(fragment, "blah")
                    .commit();
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
