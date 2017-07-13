package com.example.gk.potterwatch;

import android.content.Intent;
import android.hardware.camera2.params.Face;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
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

    private String facebookName,email,gender,profilePictureid;

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
        FacebookLoginButton.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logOut();


        FacebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken().getToken();


                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("MAIN", response.toString());

                                try {

                                    email = object.getString("email");
                                    gender = object.getString("gender");
                                    facebookName = object.getString("name");
                                    profilePictureid = object.getString("id");
                                    Log.e("CHECKKKK: ", email + " " + gender + " " + facebookName + " " + profilePictureid);
                                    new feedTask().execute();


                                }
                            catch (JSONException error){
                                error.printStackTrace();
                            }
                            }
                        });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();



            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "Error to LOGIN FACEBOOK ", Toast.LENGTH_LONG).show();
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


    protected void getUserDetails(LoginResult loginResult){
        GraphRequest dataRequest = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                    }
                }
        );
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
                postdata.put("id",profilePictureid);
                postdata.put("name",facebookName);
                postdata.put("email",email);
                postdata.put("gender",gender);

                String stringData = postdata.toString();

                Log.e("HELLLLLLOOOOOO:",stringData);
                RequestBody JSONdata = RequestBody.create(JSON, stringData);

                Request request = new Request.Builder().url("http:192.168.43.232:8080/fbconnect").post(JSONdata).build();
                Response response = client.newCall(request).execute();

                Log.e("TEST",request.toString());
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
