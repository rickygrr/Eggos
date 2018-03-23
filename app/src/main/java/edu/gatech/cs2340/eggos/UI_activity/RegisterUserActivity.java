package edu.gatech.cs2340.eggos.UI_activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.gatech.cs2340.eggos.Model.User.User;
import edu.gatech.cs2340.eggos.Model.User.UserTypeEnum;
import edu.gatech.cs2340.eggos.R;

public class RegisterUserActivity extends AppCompatActivity {

    //UserDatabaseInterface UserDBInstance;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Button mRegisterButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        //UserDBInstance = UserDatabase_local.getInstance();
        Button mRegisterButton = (Button) findViewById(R.id.register_register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
        Button mCancelButton = (Button) findViewById(R.id.register_cancel_button);
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
        Spinner mUserType = (Spinner) findViewById(R.id.register_usertype_spinner);
        //now populate spinner
        ArrayAdapter<UserTypeEnum> type_adapter = new ArrayAdapter<UserTypeEnum>(this, android.R.layout.simple_spinner_item, UserTypeEnum.getRegisterableEnum());
        type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mUserType.setAdapter(type_adapter);
        mUserType.setSelection(UserTypeEnum.USER.ordinal());
    }

    private boolean attemptRegister(){
        EditText mUsername = (EditText) findViewById(R.id.register_username);
        EditText mPassword = (EditText) findViewById(R.id.register_password);
        EditText mPasswordConfirm = (EditText) findViewById(R.id.register_password_confirm);
        Spinner mUserType = (Spinner) findViewById(R.id.register_usertype_spinner);
        mRegisterButton = (Button) findViewById(R.id.register_register_button);

        final String username = mUsername.getText().toString();
        final String password = mPassword.getText().toString();
        final UserTypeEnum usertype = (UserTypeEnum) mUserType.getSelectedItem();
        boolean valid_user = true;
        View focusView = null;

        if(username.length() < User.MIN_USERNAME_LENGTH) {
            mUsername.setError("Username too short.");
            valid_user = false;
            focusView = mUsername;
        /*}else if (UserDBInstance.userExists(username)){
            mUsername.setError("User already exists.");
            valid_user = false;
            focusView = mUsername;*/
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
            /*if (!UserDBInstance.addUser(new User(username, password, usertype))) {
                mRegisterButton.setError("User addition failed. Please try again.");
                focusView = mRegisterButton;
                valid_user = false;
            } else {

            }*/
            mAuth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            boolean success = true;
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("RegisterUserActivity", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                try{
                                    //TODO Add user to permission database
                                    Map<String, Object> userProperty = new HashMap<>();
                                    userProperty.put("Type", usertype.toString());
                                    userProperty.put("CurrentShelter", null);
                                    Log.d("RegisterUserActivity", "User UID = "+user.getUid());
                                    DocumentReference userDoc = db.collection("user").document(user.getUid());
                                    userDoc.set(userProperty)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("RegisterUserActivity", "User DB write ok");
                                                    //Put screen change *all the way in* to force write to get done first.
                                                    Context context = mRegisterButton.getContext();
                                                    Intent intent = new Intent(context, LoginActivity.class);
                                                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    context.startActivity(intent);
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("RegisterUserActivity", "Error writing database", e);
                                                    throw new RuntimeException("User DB write failed",e);
                                                }
                                            });
                                }catch(Exception e){
                                    Log.e("RegisterUserActivity", "Failed after add user: "+e.toString());
                                    success = false;
                                    //now undo user add
                                    if(user != null) {
                                        user.reauthenticate(EmailAuthProvider.getCredential(username, password));
                                        user.delete(); //Monika approves!
                                        Log.e("RegisterUserActivity", "User un-added");
                                    }
                                }
                            } else {
                                success = false;
                            }

                            if (!success) {
                                // If sign in fails, display a message to the user.
                                Log.w("RegisterUserActivity", "createUserWithEmail:failure", task.getException());
                                //updateUI(null);
                                mRegisterButton.setError("User addition failed. Please try again.");
                                //focusView = mRegisterButton;
                                //valid_user = false;
                            }
                        }
                    });

        }
        if (focusView != null) {
            focusView.requestFocus();
        }
        return valid_user;
    }

}
