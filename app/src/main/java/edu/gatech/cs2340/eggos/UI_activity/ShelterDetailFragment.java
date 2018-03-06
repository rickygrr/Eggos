package edu.gatech.cs2340.eggos.UI_activity;

/**
 * Created by Vibhav on 2/28/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import junit.framework.Assert;

import java.util.List;

import edu.gatech.cs2340.eggos.Model.Shelter.Shelter;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabase;
import edu.gatech.cs2340.eggos.R;


/**
 * A fragment representing a single Course detail screen.
 *
 * Basically this displays a list of students that are in a particular course
 * that was selected from the main screen.
 *
 */
public class ShelterDetailFragment extends Fragment {
    /**
     * The fragment arguments representing the  ID's that this fragment
     * represents.  Used to pass keys into other activities through Bundle/Intent
     */
    public static final String SHELTER_UID = "shelter_uid";

    TextView shelterDetails;

    /**
     * The shelter that this detail view is for.
     */
    private Shelter mShelter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShelterDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("hooo", "WE HAVE REACHED THE FRAGMENT");
        Log.d("hooo", getArguments().toString());
        //Check if we got a valid shelter passed to us
        if (getArguments().containsKey(SHELTER_UID)) {
            // Get the id from the intent arguments (bundle) and
            //ask the model to give us the course object
            ShelterDatabase model = ShelterDatabase.getInstance();
            // mShelter = model.getShelterById(getArguments().getInt(ARG_COURSE_ID));
            mShelter = model.getCurrentShelter();

            this.shelterDetails.setText("Dummy Text");
            //this.shelterDetails.setText(mShelter.toString());
            Log.d("ShelterDetailFragment", "Passing over shelter: " + mShelter);

            Activity activity = this.getActivity();

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("hssooo", "WE HAVE REACHED THE onCreateView");
        View view = inflater.inflate(R.layout.shelter_detail_fragment, container, false);
        shelterDetails = (TextView) view.findViewById(R.id.shelterDetailsText);
        return view;
    }


}