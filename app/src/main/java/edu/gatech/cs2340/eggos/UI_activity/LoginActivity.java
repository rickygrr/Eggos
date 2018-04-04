package edu.gatech.cs2340.eggos.UI_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.gatech.cs2340.eggos.Model.User.UserHolder;
import edu.gatech.cs2340.eggos.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    // UI references.
    private EditText mUserView;
    private EditText mPasswordView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUserView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if ((id == EditorInfo.IME_ACTION_DONE) || (id == EditorInfo.IME_NULL)) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mSignInButton = findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button mCancelButton = findViewById(R.id.cancel_button);
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
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            focusView = mPasswordView;
            cancel = true;
        }

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

        if (!TextUtils.isEmpty(user) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid username.
        if (TextUtils.isEmpty(user)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;

        }
        

        //now do login
        if (!UserHolder.getInstance().login(user,password)){
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null) {
                focusView.requestFocus();
            }
        } else {
            // go to main screen
            Context context = mLoginFormView.getContext();
            Intent intent = new Intent(context, DummyAppActivity.class);
            context.startActivity(intent);
            finish();
        }
    }

    private boolean isPasswordValid(String password) {
        return !password.isEmpty();
    }
}

