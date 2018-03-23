package edu.gatech.cs2340.eggos.UI_activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.gatech.cs2340.eggos.Model.User.UserHolder;
import edu.gatech.cs2340.eggos.R;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    // UI references.
    private EditText mUserView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        mUserView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button mCancelButton = (Button) findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, SplashScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //Make back button great again.
                context.startActivity(intent);
                finish();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String user = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        /*if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            focusView = mPasswordView;
            cancel = true;
        }*/

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(user)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        }

        if (TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;

        }
        

        if (!TextUtils.isEmpty(user) && TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(user)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;

        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null) {
                focusView.requestFocus();
            }
        } else {
            mAuth.signInWithEmailAndPassword(user, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LoginActivity", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Context context = mLoginFormView.getContext();
                            Intent intent = new Intent(context, DummyAppActivity.class);
                            context.startActivity(intent);
                            finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("LoginActivity", "signInWithEmail:failure", task.getException());
                            //updateUI(null);
                            mPasswordView.setError(getString(R.string.error_incorrect_password));
                        }
                    }
                 });
        }
    }

    /*private boolean isPasswordValid(String password) {
        return password.length() > 0;
    }*/
}

