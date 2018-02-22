package dcube.com.salonseek;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import webservices.WebServicesHandler;


public class PhoneActivity extends Activity {

    TextView done;
    Spinner spinner;
    EditText ed_phone_no;

    ArrayList<String> country = new ArrayList<>();

    WebServicesHandler ws;

    Context context = PhoneActivity.this;

    String[] planets_array;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_phone);

        done = (TextView) findViewById(R.id.done);
        spinner = (Spinner) findViewById(R.id.spinner);
        ed_phone_no = (EditText) findViewById(R.id.ed_phone_no);

        String[] locales = Locale.getISOCountries();

        for (String countryCode : locales) {

            Locale obj = new Locale("", countryCode);

            /*System.out.println("Country Code = " + obj.getCountry()
                    + ", Country Name = " + obj.getDisplayCountry());

            */
            country.add(obj.getDisplayCountry());

        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        //adapter.setDropDownViewResource(R.drawable.down);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Send_SMS().execute();

            }
        });

    }



    public class Send_SMS extends AsyncTask<String,String,String>
    {

        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Sending SMS...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            ws.SendSMS(PhoneActivity.this);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            startActivity(new Intent(PhoneActivity.this,CongratsActivity.class));
            finish();
        }

    }



}
