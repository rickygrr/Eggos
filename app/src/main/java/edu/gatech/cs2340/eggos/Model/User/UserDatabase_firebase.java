package edu.gatech.cs2340.eggos.Model.User;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.cs2340.eggos.UI_activity.LoginActivity;

/**
 * Created by chateau86 on 23-Mar-18.
 */

public class UserDatabase_firebase implements UserDatabaseInterface{
    private static final UserDatabase_firebase ourInstance = new UserDatabase_firebase();

    public static UserDatabase_firebase getInstance() {
        return ourInstance;
    }

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private UserDatabase_firebase() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public boolean addUser(String username, String password, UserTypeEnum type) {
        return false;
    }

    @Override
    public void addUser(final String username, final String password, final UserTypeEnum usertype, final OnSuccessListener<Void> callback) {
        mAuth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
                                .addOnSuccessListener(callback)
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

                    //if (!success) {
                        //TODO: Add failure callback here
                        //Fuck you Java for being a kingdom of nouns: http://steve-yegge.blogspot.com/2006/03/execution-in-kingdom-of-nouns.html
                        //This shit could've been done in like 5 seconds in Python.
                        // If sign in fails, display a message to the user.
                        //Log.w("RegisterUserActivity", "createUserWithEmail:failure", task.getException());
                        //mRegisterButton.setError("User addition failed. Please try again.");
                        //focusView = mRegisterButton;
                        //valid_user = false;
                    //}
                }
            });
    }

    @Override
    public boolean userExists(String username) {
        Task<SignInMethodQueryResult> t = mAuth.fetchSignInMethodsForEmail(username);
        SignInMethodQueryResult result = t.getResult();
        return (result != null && result.getSignInMethods()!= null && result.getSignInMethods().size() > 0);
    }

    @Override
    public User getUser(String username, String password) {
        return null;
    }
}
