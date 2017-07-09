package com.example.gk.potterwatch;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    public static MediaType JSON;

    // To check whether is already existing or not. This is to ensure which page to open. Sorting Hat or Muggle? SignUp
    private boolean userExistence = true;
    // Variable for Alohomora
    private String enterEmail;
    private String enterPassword;
    private String accessToken;
    LoginButton FacebookLoginButton;
    CallbackManager callbackManager;

    public boolean getUserExistence() {
        return userExistence;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        //Creating both the buttons
        final Button buttonAlohomora = (Button) findViewById(R.id.alohomora);
        final Button buttonSignUp = (Button) findViewById(R.id.signUp);

        FacebookLoginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        FacebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                accessToken = loginResult.getAccessToken().getToken();
                Log.e(TAG, accessToken + '\n');
                Log.e(TAG, loginResult.getAccessToken().getUserId() + '\n');
                new feedTask().execute();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        //Setting a click listener on the Alohomora Button
        buttonAlohomora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting the entered email ID
                EditText eEmail = (EditText) findViewById(R.id.email);
                enterEmail = eEmail.getText().toString();

                //Getting the password.
                // TODO: Check the password with the data collected from the server.

                EditText ePassword = (EditText) findViewById(R.id.password);
                enterPassword = ePassword.getText().toString();


                /** Actual code. Final code depiction
                 if(getUserExistence()) {
                 Intent sortingHatIntent = new Intent(MainActivity.this, SortingHat.class);
                 startActivity(sortingHatIntent);
                 }**/

                Intent sortingHatIntent = new Intent(MainActivity.this, SortingHat.class);
                startActivity(sortingHatIntent);
            }
        });

        // Setting a click listener on the Muggle?SignUp Button
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignUp = new Intent(MainActivity.this, SignUp.class);
                startActivity(SignUp);

            }
        });
    }


    // This is the class to perform the post request to the server
    public class feedTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                JSON = MediaType.parse("application/json; charset=utf-8");

                OkHttpClient client = new OkHttpClient();

                JSONObject postdata = new JSONObject();
                postdata.put("auth_token", accessToken);

                RequestBody JSONdata = RequestBody.create(JSON, postdata.toString());

                Request request = new Request.Builder().url("http:192.168.43.232:8080/fbconnect").post(JSONdata).build();
                Response response = client.newCall(request).execute();

                String result = response.body().string();
                Log.e(TAG, "RESULT: " + result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (userExistence) {
                Intent i = new Intent(MainActivity.this, ColorPicker.class);
                startActivity(i);
            } else {
                // TODO: If the user is not existing and signing for the first time.
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


    }


}
