package dcube.com.salonseek;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import utils.ButtonAdapter;
import utils.ButtonAdapter1;
import utils.MyApplication;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

public class BookStep3 extends Activity {

    RadioGroup todayradiogroup;

    TextView confirm;
    ImageView cancel;

    WebServicesHandler ws;
    MyApplication global;

    GridView gv1;
    GridView gv2;

    Context context = BookStep3.this;
    //ArrayAdapter<String> adpater;

    ButtonAdapter adpater;
    ButtonAdapter1 adpater1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_step3);

        global = (MyApplication) getApplicationContext();

        confirm = (TextView) findViewById(R.id.confirm);
        cancel = (ImageView) findViewById(R.id.cancel);

        gv1 = (GridView) findViewById(R.id.gridView1);
        gv2 = (GridView) findViewById(R.id.gridView2);

        global.setBookingTime("");

        new AsyncTaskRunner().execute();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if (global.getBookingTime().matches(""))
                {
                    Toast.makeText(BookStep3.this, "Choose Appointment Time..", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (global.getBook_total_time() > 120)
                    {
                        startActivity(new Intent(BookStep3.this , PaymentMethods.class));
                    }
                    else {

                        startActivity(new Intent(BookStep3.this , InvoiceNotPaymentActivity.class));
//                        new BookingService().execute();
                    }

                }


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SalonActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    public class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(BookStep3.this, "Loading Timings.. ", "Please Wait..!");
        }

        @Override
        protected String doInBackground(String... params) {

            try{
                ws.GetSlotService(BookStep3.this ,
                        global.getBookingDetails().get(GlobalConstants.BOOK_SALON_ID),
                        global.getBookingDetails().get(GlobalConstants.BOOK_TREATMENT_ID),
                        global.getBookingDetails().get(GlobalConstants.BOOK_STYLIST_ID));
            }
            catch (Exception e){

            }

            return resp;
        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();

            //adpater = new ArrayAdapter<String>(BookStep3.this,android.R.layout.simple_list_item_single_choice,global.getTodayListing());

            try{

                if (global.getTodayListing().size() > 0)
                {
                    adpater = new ButtonAdapter(BookStep3.this , global.getTodayListing());
                    gv1.setAdapter(adpater);
                }
                if (global.getTomorrowListing().size() > 0)
                {
                    adpater1 = new ButtonAdapter1(BookStep3.this , global.getTomorrowListing());
                    gv2.setAdapter(adpater1);
                }

            }
            catch (Exception e){

            }

        }

    }



    public class BookingService extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(BookStep3.this, "Booking Appointment.. ", "Please Wait..!");
        }

        @Override
        protected String doInBackground(String... params) {

            try{
                ws.BookingService(BookStep3.this);
            }
            catch (Exception e){

            }

            return resp;
        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();

            if (global.getBooking_status() == 1)
            {
                startActivity(new Intent(BookStep3.this , PhoneActivity.class));
                finish();
            }
            else
            {
                Toast.makeText(context, "Some error occured \n Try Again..", Toast.LENGTH_SHORT).show();
            }
            //adpater = new ArrayAdapter<String>(BookStep3.this,android.R.layout.simple_list_item_single_choice,global.getTodayListing());


        }

    }






}