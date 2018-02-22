package dcube.com.salonseek;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import utils.MyApplication;
import webservices.WebServicesHandler;

public class ConfirmationActivity extends AppCompatActivity {

    WebServicesHandler ws;

    MyApplication global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        global = (MyApplication) getApplicationContext();

        //Getting Intent
        Intent intent = getIntent();


        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));

            //Displaying payment details
            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showDetails(JSONObject jsonDetails, String paymentAmount) throws JSONException {
        //Views
        TextView textViewId = (TextView) findViewById(R.id.paymentId);
        TextView textViewStatus= (TextView) findViewById(R.id.paymentStatus);
        TextView textViewAmount = (TextView) findViewById(R.id.paymentAmount);

        try {

            //Showing the details from json object
            textViewId.setText(jsonDetails.getString("id"));
            textViewStatus.setText(jsonDetails.getString("state"));
            textViewAmount.setText(paymentAmount+" USD");

        }catch (NullPointerException e){

        }


    }

    @Override
    public void onBackPressed() {

        new AsyncTaskRunner1().execute();
    }



    public class AsyncTaskRunner1 extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {

            ws.BookingService(ConfirmationActivity.this);
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {

            //progressDialog.dismiss();
            //  getActivity().startActivity(new Intent(getActivity(),PhoneActivity.class));


            if (global.getBooking_status() == 1)
            {
                Intent i = new Intent(ConfirmationActivity.this,PhoneActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(ConfirmationActivity.this, "Some error occured..", Toast.LENGTH_SHORT).show();
            }



        }

        @Override
        protected void onPreExecute() {

            //progressDialog = ProgressDialog.show(getActivity(),"Booking Salon... ","Wait!");
        }
    }






}
