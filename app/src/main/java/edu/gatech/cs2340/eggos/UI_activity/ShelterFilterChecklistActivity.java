package edu.gatech.cs2340.eggos.UI_activity;

/**
 * Created by Vibhav on 3/13/2018.
 */

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import edu.gatech.cs2340.eggos.Model.Shelter.AgeEnum;
import edu.gatech.cs2340.eggos.Model.Shelter.GenderEnum;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabaseInterface;
//import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabase_local;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabase_room;
import edu.gatech.cs2340.eggos.R;


public class ShelterFilterChecklistActivity extends Activity {

    MyCustomAdapter genderAdapter = null;
    MyCustomAdapter ageAdapter = null;
    TextView textNameSearch = null;
    ShelterDatabaseInterface ShelterDBInstance;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist_filter_layout);
        //ShelterDBInstance = ShelterDatabase_local.getInstance();
        ShelterDBInstance = ShelterDatabase_room.getInstance();
        textNameSearch = findViewById(R.id.textNameSearch);
        //Generate list View from ArrayList
        displayListView();
        checkButtonClick();

    }

    private void displayListView() {
        //create an ArrayAdaptar from the String Array
        genderAdapter = new MyCustomAdapter(this,
                R.layout.shelter_info, str2list(GenderEnum.getGenderList()));
        ListView listViewGender = findViewById(R.id.listViewGenderRestrictions);
        // Assign adapter to ListView
        listViewGender.setAdapter(genderAdapter);
        listViewGender.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, log a message
                ListItem restriction = (ListItem) parent.getItemAtPosition(position);
                Log.d("call", "Clicked on " + restriction.toString());
            }
        });

        ageAdapter = new MyCustomAdapter(this,
                R.layout.shelter_info, str2list(AgeEnum.getAgeList()));
        ListView listViewAge = findViewById(R.id.listViewAgeRestrictions);
        // Assign adapter to ListView
        listViewAge.setAdapter(ageAdapter);
        listViewAge.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, log a message
                ListItem restriction = (ListItem) parent.getItemAtPosition(position);
                Log.d("call", "Clicked on " + restriction.toString());
            }
        });

    }

    private class ListItem {
        String str;
        boolean selected;
        ListItem(String str){
            this.str = str;
            this.selected = false;
        }
        public String toString(){
            return str;
        }
        public boolean isSelected(){
            return selected;
        }
        public void setSelected(boolean s){
            this.selected = s;
        }
    }

    private ArrayList<ListItem> str2list (Iterable<String> strArr){
        ArrayList<ListItem> out = new ArrayList<>();
        for (String s: strArr){
            out.add(new ListItem(s));
        }
        return out;
    }

    private class MyCustomAdapter extends ArrayAdapter<ListItem> {
        private List<ListItem> restrictionList;
        public MyCustomAdapter(Context context, int textViewResourceId,
                               List<ListItem> restrictionList) {
            super(context, textViewResourceId, restrictionList);
            this.restrictionList = new ArrayList<>();
            this.restrictionList.addAll(restrictionList);
        }

        private class ViewHolder {
            //TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View _convertView = convertView;
            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (_convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                _convertView = vi.inflate(R.layout.shelter_info, null);

                holder = new ViewHolder();
                //holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = _convertView.findViewById(R.id.checkBox1);
                _convertView.setTag(holder);
                holder.name.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        ListItem restriction = (ListItem) cb.getTag();
                        restriction.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) _convertView.getTag();
            }

            ListItem restriction = restrictionList.get(position);
            //holder.code.setText(" (" +  country.getCode() + ")");
            holder.name.setText(restriction.toString());
           // holder.name.setChecked(country.isSelected());
            holder.name.setTag(restriction);

            return _convertView;

        }

    }

    private void checkButtonClick() {
        Button myButton = findViewById(R.id.findSelected);
        myButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //StringBuilder responseText = new StringBuilder();
                //responseText.append("The following gender were selected...");

                List<ListItem> genderList = genderAdapter.restrictionList;
                ArrayList<String> selectedGender = new ArrayList<>();
                for(int i=0;i<genderList.size();i++){
                    ListItem g = genderList.get(i);
                    if(g.isSelected()){
                        //responseText.append("\n" + g.toString());
                        selectedGender.add(g.toString());
                    }
                }

                //responseText.append("\nThe following age were selected...");

                List<ListItem> ageList = ageAdapter.restrictionList;
                ArrayList<String> selectedAge = new ArrayList<>();
                for(int i=0;i<ageList.size();i++){
                    ListItem a = ageList.get(i);
                    if(a.isSelected()){
                        //responseText.append("\n" + a.toString());
                        selectedAge.add(a.toString());
                    }
                }
                //Log.d("call", "Clicked on " + responseText);

                Intent intent = new Intent();
                intent.putExtra("name", textNameSearch.getText().toString());
                intent.putExtra("gender", selectedGender);
                intent.putExtra("age", selectedAge);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

    }

}