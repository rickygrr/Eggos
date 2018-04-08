package edu.gatech.cs2340.eggos.UI_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import edu.gatech.cs2340.eggos.Model.User.User;
import edu.gatech.cs2340.eggos.Model.User.UserDatabaseInterface;
import edu.gatech.cs2340.eggos.Model.User.UserDatabase_room;
import edu.gatech.cs2340.eggos.Model.User.UserTypeEnum;
import edu.gatech.cs2340.eggos.R;

public class RegisterUserActivity extends AppCompatActivity {

    UserDatabaseInterface UserDBInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        //UserDBInstance = UserDatabase_local.getInstance();
        UserDBInstance = UserDatabase_room.getInstance();
        Button mRegisterButton = findViewById(R.id.register_register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(attemptRegister()) {
                    //only go to login screen if success
                    Context context = view.getContext();
                    Intent intent = new Intent(context, LoginActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    finish();
                }
            }
        });
        Button mCancelButton = findViewById(R.id.register_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, SplashScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                finish();
            }
        });
        Spinner mUserType = findViewById(R.id.register_usertype_spinner);
        //now populate spinner
        ArrayAdapter<UserTypeEnum> type_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, UserTypeEnum.getRegisterableEnum());
        type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mUserType.setAdapter(type_adapter);
        mUserType.setSelection(UserTypeEnum.USER.ordinal());
    }

    private boolean attemptRegister(){
        EditText mUsername = findViewById(R.id.register_username);
        EditText mPassword = findViewById(R.id.register_password);
        EditText mPasswordConfirm = findViewById(R.id.register_password_confirm);
        Spinner mUserType = findViewById(R.id.register_usertype_spinner);
        Button mRegisterButton = findViewById(R.id.register_register_button);

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        UserTypeEnum usertype = (UserTypeEnum) mUserType.getSelectedItem();
        boolean valid_user = true;
        View focusView = null;

        if(username.length() < User.MIN_USERNAME_LENGTH) {
            mUsername.setError("Username too short.");
            valid_user = false;
            focusView = mUsername;
        }else if (UserDBInstance.userExists(username)){
            mUsername.setError("User already exists.");
            valid_user = false;
            focusView = mUsername;
        }else if (password.length() < User.MIN_PASSWORD_LENGTH){
            mPassword.setError("Password too short.");
            valid_user = false;
            focusView = mPassword;
        }else if (!password.equals(mPasswordConfirm.getText().toString())){
            mPasswordConfirm.setError("Password does not match.");
            valid_user = false;
            focusView = mPasswordConfirm;
        }
        //Now add user
        if (valid_user) {
            if (!UserDBInstance.addUser(new User(username, password, usertype.toString()))) {
                mRegisterButton.setError("User addition failed. Please try again.");
                focusView = mRegisterButton;
                valid_user = false;
            } else {

            }
        }
        if (!valid_user) {
            if (focusView != null) {
                focusView.requestFocus();
            }
        }
        return valid_user;
    }

}
