package edu.gatech.cs2340.eggos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DummyAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_app);
        Button mSplashButton = (Button) findViewById(R.id.dummy_button_splash);
        mSplashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fire an intent to go to login page
                Context context = view.getContext();
                Intent intent = new Intent(context, SplashScreenActivity.class);
                context.startActivity(intent);
            }
        });
        Button mLoginButton = (Button) findViewById(R.id.dummy_button_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fire an intent to go to login page
                Context context = view.getContext();
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        });

    }
}
