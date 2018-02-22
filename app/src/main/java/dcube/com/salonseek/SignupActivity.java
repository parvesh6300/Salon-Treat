package dcube.com.salonseek;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import utils.MyApplication;
import webservices.WebServicesHandler;

public class SignupActivity extends Activity {

    private EditText username;
    private EditText password;
    private EditText confirmpassword;
    private EditText email;
    private EditText firstname;
    private EditText lastname;


    private Button backButton;
    private Button finishButton;

    public String Error;

    RelativeLayout checkbox;
    CheckBox check;
    WebServicesHandler ws;
    Context ctx;

    public MyApplication global;
    Intent i;

    String username_text;
    String password_text;
    String confirmpassword_text;
    String email_text;
    String firstname_text;
    String lastname_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ctx = this;

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        email = (EditText) findViewById(R.id.email);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);

        check = (CheckBox) findViewById(R.id.check);

        checkbox = (RelativeLayout) findViewById(R.id.checkbox);

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check.isChecked())
                    check.setChecked(false);
                else
                    check.setChecked(true);
            }
        });

        //password.setText("password");
        final Handler toastHandler = new Handler();
        final Runnable toastRunnable = new Runnable() {public void run()
        {

            Toast.makeText(SignupActivity.this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
        }
        };

        i = new Intent(SignupActivity.this, LoginActivity.class);

        backButton = (Button) findViewById(R.id.backbutton);
        finishButton = (Button) findViewById(R.id.finishbutton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {

                    /*new Thread() {
                        @Override
                        public void run() {

                            ws.SignUpWebService(ctx, username.getText().toString(), password.getText().toString(),email.getText().toString());

                        }
                    }.start();*/

                    new AsyncTaskRunner().execute(username.getText().toString(),password.getText().toString(),email.getText().toString(),(firstname_text+" "+lastname_text));

                } else {

                    Toast.makeText(SignupActivity.this, Error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean validate() {

        username_text = username.getText().toString();
        password_text = password.getText().toString();
        confirmpassword_text = confirmpassword.getText().toString();
        email_text = email.getText().toString();
        firstname_text = firstname.getText().toString();
        lastname_text = lastname.getText().toString();

        if (username_text.isEmpty()) {
            setError("User name is empty");
            return false;

        } else {
            setError(null);
        }

       if (password_text.isEmpty()) {
            setError("Password is empty");
            return false;

        } else {
            setError(null);
        }

        if (!confirmpassword_text.equals(password_text)) {
            setError("Password and confirm password do not match");
            return false;

        } else {
            setError(null);
        }

        if (firstname_text.isEmpty()) {
            setError("Enter First Name");
            return false;

        } else {
            setError(null);
        }

        if (lastname_text.isEmpty()) {
            setError("Enter Last Name");
            return false;

        } else {
            setError(null);
        }

        if (email_text.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email_text).matches()) {
            setError("enter a valid email address");
            return false;

        } else {
            setError(null);
        }

        return true;
    }

    public void setError(String error)
    {
        this.Error = error;
    }

    public class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;
        String success;

        @Override
        protected String doInBackground(String... params) {

               success =  ws.SignUpWebService(ctx,params[0],params[1],params[2],params[3]);

            Log.v("result" , success);

            return success;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming

            if (result == "true") {

                Toast.makeText(SignupActivity.this, "Signup Successfull", Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                Toast.makeText(SignupActivity.this, "Email Already exists", Toast.LENGTH_SHORT).show();
            }

            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(SignupActivity.this, "Signing up", "Wait!");
        }
    }
}

