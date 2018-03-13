package edu.gatech.cs2340.eggos.UI_activity;

/**
 * Created by Vibhav on 3/13/2018.
 */

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import edu.gatech.cs2340.eggos.Model.Shelter.Shelter;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabase;
import edu.gatech.cs2340.eggos.Model.User.UserHolder;
import edu.gatech.cs2340.eggos.R;


public class ShelterFilterChecklistActivity extends Activity {

    MyCustomAdapter dataAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist_filter_layout);

        //Generate list View from ArrayList
        displayListView();

        checkButtonClick();

    }

    private void displayListView() {

        //Array list of countries
        ArrayList<String> restrictionList = new ArrayList<String>();
        restrictionList.add("Male");
        restrictionList.add("Female");
        restrictionList.add("Other");
        restrictionList.add("Family with Newborns");
        restrictionList.add("Children");
        restrictionList.add("Young Adults");
        restrictionList.add("Anyone");


        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.shelter_info, restrictionList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, log a message
                String restriction = (String) parent.getItemAtPosition(position);
                Log.d("call", "Clicked on " + restriction);
            }
        });

    }

    private class MyCustomAdapter extends ArrayAdapter<String> {

        private ArrayList<String> restrictionList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<String> restrictionList) {
            super(context, textViewResourceId, restrictionList);
            this.restrictionList = new ArrayList<String>();
            this.restrictionList.addAll(restrictionList);
        }

        private class ViewHolder {
            //TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.shelter_info, null);

                holder = new ViewHolder();
                //holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                /* holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        String restriction = (String) cb.getTag();
                        restriction.setSelected(cb.isChecked());
                    }
                }); */
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            String restriction = restrictionList.get(position);
            //holder.code.setText(" (" +  country.getCode() + ")");
            holder.name.setText(restriction);
           // holder.name.setChecked(country.isSelected());
            holder.name.setTag(restriction);

            return convertView;

        }

    }

    private void checkButtonClick() {


        /* Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Country> countryList = dataAdapter.countryList;
                for(int i=0;i<countryList.size();i++){
                    Country country = countryList.get(i);
                    if(country.isSelected()){
                        responseText.append("\n" + country.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        }); */

    }

}