package dcube.com.salonseek;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import utils.MyApplication;
import utils.PrefUtils;
import utils.User;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

public class LoginActivity extends Activity  {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private ImageView fbLogin;
    private ImageView signinButton;
    private ProgressDialog progressDialog;

    private EditText username;
    private EditText password;

    private ImageView signup;

    String username_text;
    String password_text;

    public String Error;

    User user;
    ImageView cross;
    WebServicesHandler ws;
    Context ctx;
    MyApplication global;

    String login_pref = "Login_pref";
    String is_logged_in_pref = "Logged_in_pref";
    String user_name_pref = "user_name";
    String password_pref = "password";

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    ProgressBar progressBarFooter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ctx = this;
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        cross = (ImageView) findViewById(R.id.cross);
        signup = (ImageView) findViewById(R.id.signup);
        signinButton = (ImageView) findViewById(R.id.signin_button);

        global = (MyApplication) getApplicationContext();

        progressBarFooter = (ProgressBar) findViewById(R.id.pbFooterLoading);

        //password.setText("password");

        final Handler toastHandler = new Handler();
        final Runnable toastRunnable = new Runnable() {

            public void run() {

            Toast.makeText(LoginActivity.this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
        }

        };

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate())
                {

                    username_text = username.getText().toString();
                    password_text = password.getText().toString();

                    new AsyncTaskRunner().execute();

                }
                else
                {
                    Toast.makeText(LoginActivity.this,Error,Toast.LENGTH_SHORT).show();
                }
            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startActivity(new Intent(LoginActivity.this , OnBoardingActivity.class));
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this , SignupActivity.class));
            }
        });
     }

    @Override
    protected void onResume() {

        super.onResume();

        callbackManager=CallbackManager.Factory.create();

        loginButton= (LoginButton)findViewById(R.id.facebook_button);

        loginButton.setReadPermissions("public_profile", "email","user_friends");

        fbLogin = (ImageView) findViewById(R.id.facebook_login_button);

        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(PrefUtils.getCurrentUser(LoginActivity.this) != null){

                    global.setFblogin(true);

                    ArrayList loginList =  new ArrayList<HashMap<String,String>>();

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(GlobalConstants.USER_ID ,PrefUtils.getCurrentUser(LoginActivity.this).userID);

                    loginList.add(map);

                    global.setLoginListing(loginList);

                    setSharedPreferences();

                    Intent homeIntent = new Intent(LoginActivity.this, HomeTestActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
                else {

                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();

                    loginButton.performClick();
                    loginButton.setPressed(true);
                    loginButton.invalidate();
                    loginButton.registerCallback(callbackManager, mCallBack);
                    loginButton.setPressed(false);
                    loginButton.invalidate();
                }
            }
        });
    }

    public boolean validate() {

        username_text = username.getText().toString().trim();
        password_text = password.getText().toString().trim();

        if (username_text.isEmpty()) {
            setError("User name is empty");
            return false;
        }

        if (password_text.isEmpty()) {

            setError("Password is empty");
            return false;
        }
        else
        {
            return true;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            progressDialog.dismiss();

            // App codes
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),

                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {

                            Log.e("response: ", response + "");
                            try {

                                user = new User();
                                user.facebookID = object.getString("id").toString();
                                user.email = object.getString("email").toString();
                                user.name = object.getString("name").toString();
                                user.gender = object.getString("gender").toString();
                                user.Image  = "https://graph.facebook.com/"+ user.facebookID +"/picture";

                                //user.Image  = object.getJSONObject("picture").getJSONObject("data").getString("url");

                                new Thread() {
                                    @Override
                                    public void run() {

                                        if (Looper.myLooper() == null)
                                        {
                                            Looper.prepare();
                                        }

                                        if((ws.FacebookLoginWebService(LoginActivity.this,user.email,user.name,user.facebookID)) == "true")
                                        {
                                            Toast.makeText(LoginActivity.this,"welcome "+user.name,Toast.LENGTH_LONG).show();

                                            global.setFblogin(true);

                                            user.userID = global.getLoginListing().get(0).get(GlobalConstants.USER_ID);

                                            PrefUtils.setCurrentUser(user,LoginActivity.this);

                                            Intent intent = new Intent(LoginActivity.this,HomeTestActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                    }
                                }.start();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            progressDialog.dismiss();
        }

        @Override
        public void onError(FacebookException e) {
            progressDialog.dismiss();
        }
    };

    public void setError(String error)
    {
        this.Error = error;
    }


    public void setSharedPreferences()
    {
        pref = getSharedPreferences(login_pref,MODE_PRIVATE);

        editor = pref.edit();

        editor.putBoolean(is_logged_in_pref,true);

        editor.putString(user_name_pref,username.getText().toString().trim());
        editor.putString(password_pref, password.getText().toString().trim());

        editor.apply();
    }


    public class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        // ProgressBar pbFooterLoading =(ProgressBar)findViewById(R.id.pbFooterLoading);;

        @Override
        protected void onPreExecute() {
            //  progressDialog = ProgressDialog.show(SplashActivity.this, "Loading..", "Wait!");

            progressBarFooter.setVisibility(View.VISIBLE);
            Log.e("Login","Button Clicked");
        }

        @Override
        protected String doInBackground(String... params) {

//            ws.LoginWebService(SplashActivity.this, str_username, str_password);



            if(ws.LoginWebService(ctx, username_text, password_text).equalsIgnoreCase("true") )
            {
                global.setFblogin(false);

                setSharedPreferences();

                Intent i = new Intent(LoginActivity.this, HomeTestActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ctx,"Wrong UserName or Password",Toast.LENGTH_SHORT).show();
                    }
                });

            }

            return resp;

        }

        @Override
        protected void onPostExecute(String result) {

//            if (progressDialog.isShowing())
//            {
//                progressDialog.dismiss();
//            }

            progressBarFooter.setVisibility(View.GONE);

//            Intent i = new Intent(LoginActivity.this,HomeTestActivity.class);
//            startActivity(i);
//            finish();

        }

    }


}