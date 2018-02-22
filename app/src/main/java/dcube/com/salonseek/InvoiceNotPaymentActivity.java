package dcube.com.salonseek;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import utils.MyApplication;
import webservices.WebServicesHandler;

public class InvoiceNotPaymentActivity extends Activity {

    RelativeLayout rel_discount;

    MyApplication global;

    String treatment_name,special_treatment;

    TextView tv_cost, tv_treatment, tv_stylist, tv_time, tv_close, tv_book_aptmt;

    WebServicesHandler ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_invoice_not_payment);

        global = (MyApplication) getApplicationContext();

        rel_discount = (RelativeLayout) findViewById(R.id.rel_discount);
        tv_book_aptmt = (TextView) findViewById(R.id.tv_book_aptmt);

        tv_cost = (TextView) findViewById(R.id.tv_cost);
        tv_treatment = (TextView) findViewById(R.id.tv_treatment);
        tv_stylist = (TextView) findViewById(R.id.tv_stylist);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_close = (TextView) findViewById(R.id.tv_close);
        tv_book_aptmt = (TextView) findViewById(R.id.tv_book_aptmt);

        tv_stylist.setText(global.getStylist_name());

        tv_time.setText(global.getBookingTime());

        tv_book_aptmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new BookingService().execute();

            }
        });

        if (global.getSelected_treatment_name().size() > 0) {
            treatment_name = TextUtils.join(",", global.getSelected_treatment_name());

            tv_treatment.setText(treatment_name);
        }

        try {
            if (global.getSpecial_selected_name().size() > 0) {
                special_treatment = TextUtils.join(",", global.getSpecial_selected_name());

                tv_treatment.setText(special_treatment + " , " + treatment_name);
            }

        } catch (Exception e) {

        }




        rel_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(InvoiceNotPaymentActivity.this,DiscountCodeActivity.class));
                overridePendingTransition(R.anim.pull_in_right, R.anim.pull_in_right);
            }
        });


    }


    public class BookingService extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(InvoiceNotPaymentActivity.this, "Booking Appointment.. ", "Please Wait..!");
        }

        @Override
        protected String doInBackground(String... params) {

            try{
                ws.BookingService(InvoiceNotPaymentActivity.this);
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
                startActivity(new Intent(InvoiceNotPaymentActivity.this,PhoneActivity.class));
                finish();
            }
            else
            {
                Toast.makeText(InvoiceNotPaymentActivity.this, "Some error occured \n Try Again..", Toast.LENGTH_SHORT).show();
            }
            //adpater = new ArrayAdapter<String>(BookStep3.this,android.R.layout.simple_list_item_single_choice,global.getTodayListing());


        }

    }



}
