package edu.gatech.cs2340.eggos.UI_activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;

import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabaseInterface;
//import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabase_local;
import edu.gatech.cs2340.eggos.Model.Shelter.ShelterDatabase_room;
import edu.gatech.cs2340.eggos.Model.User.UserDatabaseInterface;
import edu.gatech.cs2340.eggos.Model.User.UserDatabase_room;
import edu.gatech.cs2340.eggos.R;

public class SplashScreenActivity extends AppCompatActivity {
    ShelterDatabaseInterface ShelterDBInstance;
    UserDatabaseInterface UserDBInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        UserDBInstance = UserDatabase_room.getFirstInstance(getApplicationContext());
        //ShelterDBInstance = ShelterDatabase_local.getInstance();
        ShelterDBInstance = ShelterDatabase_room.getFirstInstance(getApplicationContext());


        Button mSignInButton = (Button) findViewById(R.id.splash_login_button);
        Button mRegisterButton = (Button) findViewById(R.id.splash_register_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fire an intent to go to login page
                Context context = view.getContext();
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fire an intent to go to registration page
                Context context = view.getContext();
                Intent intent = new Intent(context, RegisterUserActivity.class);
                context.startActivity(intent);
            }
        });

        //Now run ShelterDatabase_local load trigger
        try {
            InputStream f_in = this.getApplicationContext().getResources().openRawResource(R.raw.shelter);
            ShelterDBInstance.initFromJSON(f_in);
        } catch (IOException e){
            ShelterDBInstance._initTestDatabase();
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Database load failed");
            alertDialog.setMessage("Debug data loaded instead.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
}
