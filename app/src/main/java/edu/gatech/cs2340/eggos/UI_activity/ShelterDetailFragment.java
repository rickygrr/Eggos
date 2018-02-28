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

import java.util.List;

import edu.gatech.cs2340.eggos.Model.Shelter.Shelter;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabase;


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
    //public static final String ARG_STUDENT_ID = "student_id";

    TextView shelterDetails;

    /**
     * The course that this detail view is for.
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

        //Check if we got a valid shelter passed to us
        if (getArguments().containsKey(SHELTER_UID)) {
            // Get the id from the intent arguments (bundle) and
            //ask the model to give us the course object
            ShelterDatabase model = ShelterDatabase.getInstance();
            // mCourse = model.getCourseById(getArguments().getInt(ARG_COURSE_ID));
            mShelter = model.getCurrentShelter();

            this.shelterDetails.setText(mShelter.toString());
            //Log.d("CourseDetailFragment", "Passing over course: " + mShelter);
            //Log.d("CourseDetailFragment", "Got students: " + mShelter.getStudents().size());

            Activity activity = this.getActivity();

            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mShelter.toString());
            }
        }

    }
}