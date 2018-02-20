package edu.gatech.cs2340.eggos.UI_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.gatech.cs2340.eggos.Model.User.UserHolder;
import edu.gatech.cs2340.eggos.R;

public class DummyAppActivity extends AppCompatActivity {

    TextView usrInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_app);
        Button mLogoutButton = (Button) findViewById(R.id.dummy_button_logout);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fire an intent to go to login page
                UserHolder.getInstance().logout();
                Context context = view.getContext();
                Intent intent = new Intent(context, SplashScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                finish();
            }
        });
        this.usrInfoText = (TextView) findViewById(R.id.userInfoText);
        //this.usrInfoText.setText(UserHolder.getInstance().getUser().toString());
    }

    @Override
    protected void onStart(){
        super.onStart();
        this.usrInfoText.setText(UserHolder.getInstance().getUser().toString());
    }
}
