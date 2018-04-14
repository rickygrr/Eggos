package edu.gatech.cs2340.eggos.UI_activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.List;
import java.util.ArrayList;

import edu.gatech.cs2340.eggos.Model.Shelter.Shelter;
//import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabase_local;
//import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabaseFilter;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabaseInterface;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabase_room;
import edu.gatech.cs2340.eggos.Model.User.UserHolder;
import edu.gatech.cs2340.eggos.R;

public class DummyAppActivity extends AppCompatActivity {

    static final int SELECT_FILTER_REQUEST = 1;
    TextView usrInfoText;
    View recyclerView;
    //ShelterDatabaseInterface ShelterDBInstance = ShelterDatabase_local.getInstance();
    ShelterDatabaseInterface ShelterDBInstance = ShelterDatabase_room.getInstance();
    //ShelterDatabaseFilter filter = ShelterDatabase_local.SHOW_ALL_FILTER;
    String filterName = "";
    @Nullable
    List<String> filterGender = null;
    @Nullable
    List<String> filterAge = null;
    List<Shelter> shelterList = new ArrayList<>();
    UserHolder usrHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usrHolder = UserHolder.getInstance();
        //this.filter = ShelterDBInstance.SHOW_ALL_FILTER;
        setContentView(R.layout.activity_dummy_app);

        //Step 1.  Setup the recycler view by getting it from our layout in the main window
        this.recyclerView = findViewById(R.id.shelter_list_dummy);
        assert recyclerView != null;
        //Step 2.  Hook up the adapter to the view
        setupRecyclerView((RecyclerView) recyclerView);

        Button mLogoutButton = findViewById(R.id.dummy_button_logout);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fire an intent to go to login page
                usrHolder.logout();
                Context context = view.getContext();
                Intent intent = new Intent(context, SplashScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                finish();
            }
        });

        Button mMapButton = findViewById(R.id.dummy_button_map);
        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fire an intent to go to login page
                //UserHolder.getInstance().logout();
                Context context = view.getContext();
                Intent intent = new Intent(context, MapsActivity.class);
                Bundle b = new Bundle();
                b.putIntegerArrayList("ShelterUIDList",
                        new ArrayList<>(ShelterDBInstance.packShelterList(shelterList)));
                intent.putExtras(b);
                context.startActivity(intent);
                //finish();
            }
        });

        Button mFilterButton = findViewById(R.id.dummy_button_filter);
        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fire an intent to go to login page
                //UserHolder.getInstance().logout();
                Context context = view.getContext();
                Intent intent = new Intent(context, ShelterFilterChecklistActivity.class);
                startActivityForResult(intent, SELECT_FILTER_REQUEST);
                //finish();
            }
        });

        Button mRstFilterButton = findViewById(R.id.dummy_button_rst_filter);
        mRstFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //filter = ShelterDatabase_local.SHOW_ALL_FILTER;
            filterName = "";
            filterGender = null;
            filterAge = null;
            setupRecyclerView((RecyclerView) recyclerView);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == SELECT_FILTER_REQUEST) && (resultCode == RESULT_OK)) {
            final String name = data.getStringExtra("name");
            final ArrayList<String> gender = data.getStringArrayListExtra("gender");
            final ArrayList<String> age = data.getStringArrayListExtra("age");

            this.filterName = data.getStringExtra("name");
            this.filterGender = data.getStringArrayListExtra("gender");
            this.filterAge = data.getStringArrayListExtra("age");

        } else {
            //this.filter = ShelterDatabase_local.SHOW_ALL_FILTER;

            String filterName = "";
            List<String> filterGender = null;
            List<String> filterAge = null;
        }
        //regenerate recyclerview
        //setupRecyclerView((RecyclerView) recyclerView, filter);
        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecyclerView((RecyclerView) recyclerView);
    }



    /**
     * Set up an adapter and hook it to the provided view
     * @param recyclerView  the view that needs this adapter
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        //ShelterDatabase model = ShelterDatabase.getInstance();
        shelterList = ShelterDBInstance.getFilteredShelterList(this.filterName,this.filterGender,
                this.filterAge);
        recyclerView.setAdapter(new SimpleShelterRecyclerViewAdapter(shelterList));
    }

    /**
     * This inner class is our custom adapter.  It takes our basic model information and
     * converts it to the correct layout for this view.
     *
     * In this case, we are just mapping the toString of the Course object to a text field.
     */
    public class SimpleShelterRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleShelterRecyclerViewAdapter.ViewHolder>
            implements Filterable {

        /**
         * Collection of the items to be shown in this list.
         */
        private final List<Shelter> mShelters;
        private List<Shelter> mFiltered;

        /**
         * set the items to be used by the adapter
         *
         * @param items the list of items to be displayed in the recycler view
         */
        public SimpleShelterRecyclerViewAdapter(List<Shelter> items) {

            mShelters = items;
            mFiltered = items;
        }

        @SuppressWarnings("ChainedMethodCall")
        //LayoutInflater API required this.
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            /*

              This sets up the view for each individual item in the recycler display
              To edit the actual layout, we would look at: res/layout/course_list_content.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.shelter_list_content, parent, false);
            return new ViewHolder(view);
        }

        @SuppressWarnings("FeatureEnvy")
        //OnClickListener effectively causes Shelter operations to be double-counted.
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            /*
            This is where we have to bind each data element in list (given by position parameter)
            to an element in the view (which is one of our two TextView widgets
             */
            //start by getting the element at the correct position
            holder.mShelter = mShelters.get(position);
            /*
              Now we bind the data to the widgets.  In this case, pretty simple, put the id in one
              textview and the string rep of a course in the other.
             */
            int UID = holder.mShelter.getUID();
            String idViewString = Integer.toString(UID);
            if (usrHolder.getUser()._currentShelterID == UID){
                holder.mIdView.setText(idViewString+" *");
            } else {
                holder.mIdView.setText(idViewString);
            }

            holder.mContentView.setText(holder.mShelter.toString());

            /*
             * set up a listener to handle if the user clicks on this list item, what should happen?
             */
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                //on a phone, we need to change windows to the detail view
                Context context = v.getContext();
                //create our new intent with the new screen (activity)
                Intent intent = new Intent(context, ShelterDetailActivity.class);
                /*
                    pass along the id of the course so we can retrieve the correct data in
                    the next window
                 */

                Bundle b = new Bundle();
                b.putInt("uid", holder.mShelter.getUID());
                intent.putExtras(b);

                //model.setCurrentShelter(holder.mShelter);
                //now just display the new window
                context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mShelters.size();
        }

        @Override
        public Filter getFilter() {

            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {

                    String charString = charSequence.toString();

                    if (charString.isEmpty()) {
                        mFiltered = mShelters;
                    } else {

                        List<Shelter> filteredList = new ArrayList<>();

                        for (Shelter shelter : mShelters) {
                            String name = shelter.getName();
                            name = name.toLowerCase();
                            if (name.contains(charString)
                                    || name.contains(charString)) {

                                filteredList.add(shelter);
                            }
                        }

                        mFiltered = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence,
                                              FilterResults filterResults) {
                    mFiltered = (ArrayList<Shelter>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding between the model element (in this case a Course) and the widgets in
         * the list view (in this case the two TextView)
         */

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Shelter mShelter;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = view.findViewById(R.id.id);
                mContentView = view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        //this.usrInfoText.setText(UserHolder.getInstance().getUser().toString());
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
            navigateUpTo(new Intent(this, SplashScreenActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
