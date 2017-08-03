package com.mirka.app.studenttoolboxrevised;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mirka.app.studenttoolboxrevised.data.MoodleContract;
import com.mirka.app.studenttoolboxrevised.data.MoodleDbHelper;
import com.mirka.app.studenttoolboxrevised.utils.DatabaseUtils;
import com.mirka.app.studenttoolboxrevised.utils.ErrorUtils;
import com.mirka.app.studenttoolboxrevised.utils.MoodleUtils;
import com.mirka.app.studenttoolboxrevised.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class MoodleLoginActivity extends AppCompatActivity {


    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mLoginView;
    private EditText mPasswordView;
    private EditText mMoodleUrlView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView mErrorDisplay;

    private final static String TAG = MoodleLoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mLoginView = (EditText) findViewById(R.id.login);
        mErrorDisplay = (TextView) findViewById(R.id.tv_error_display);
        mMoodleUrlView = (EditText) findViewById(R.id.moodleURL);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mLoginView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String url = mMoodleUrlView.getText().toString();
        String email = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mLoginView.setError(getString(R.string.error_field_required));
            focusView = mLoginView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mLoginView.setError(getString(R.string.error_invalid_email));
            focusView = mLoginView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(this, url, email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return true;
    }

    private boolean isPasswordValid(String password) {
        return true;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, JSONObject> {

        private final String mURL;
        private final String mLogin;
        private final String mPassword;
        private String mToken;
        private Context mContext;


        UserLoginTask(Context context, String url, String login, String password) {
            mContext = context;
            mURL = url;
            mLogin = login;
            mPassword = password;

        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            int id;
            String name, surname;
            String url = "http://" + mURL + "/";
            String token;
            JSONObject tokenResponse = MoodleUtils.getToken(mURL, mLogin, mPassword);
            try {
                token = tokenResponse.getString("token");
            } catch (JSONException e) {
                return tokenResponse;
            }

            JSONObject userInfo = MoodleUtils.getUserInfo(url, token);

            try {
                // TODO create a class for this stuff
                id = userInfo.getInt("userid");
                name = userInfo.getString("firstname");
                surname = userInfo.getString("lastname");
            } catch (JSONException e) {
                e.printStackTrace();
                return userInfo;
            }

            MoodleDbHelper dbHelper = new MoodleDbHelper(mContext);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long res = db.insert(MoodleContract.UserEntry.TABLE_NAME, null, DatabaseUtils.getUserCV(id, mURL, mLogin, mPassword, token, name, surname));

            if (res == -1){
                return ErrorUtils.buildErrorMessage(DatabaseUtils.GENERIC_ERROR, "Error inserting user into the database");
            }

            return null;
        }

        @Override
        protected void onPostExecute(final JSONObject response) {
            mAuthTask = null;
            showProgress(false);

            if (response == null) {
                startActivity(new Intent(mContext, CoursesSectionActivity.class));
                finish();
            } else {
                int status = 0;
                try {
                    status = response.getInt(ErrorUtils.STATUS_CODE_KEY);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Cannot get a status code");
                }
                switch (status) {
                    case NetworkUtils.CONNECTION_ERROR:
                    case NetworkUtils.INVALID_NETWORK_RESPONSE:
                        mErrorDisplay.setText("Error in network connection");
                        break;
                    case MoodleUtils.GENERIC_ERROR:
                        mErrorDisplay.setText("Internal error");
                        break;
                    case MoodleUtils.INVALID_TOKEN:
                        mErrorDisplay.setText("Auth error");
                        break;
                    case MoodleUtils.INVALID_USERNAME:
                        mPasswordView.setError(getString(R.string.error_incorrect_password));
                        mPasswordView.requestFocus();
                        break;
                    case DatabaseUtils.GENERIC_ERROR:
                    default:
                        mErrorDisplay.setText("Ooops, something went wrong!");
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

