package dcube.com.salonseek;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import utils.MyApplication;
import utils.PaymentFinalAdapter;
import webservices.WebServicesHandler;

public class PaymentScreen extends Activity {

    ListView listView;
    PaymentFinalAdapter adapter;

    TextView bottom,cost;

    WebServicesHandler ws;

    MyApplication global;
    Double cost_value;
    int price;

    ImageView cancel;
    TextView tv_close;

    //Payment Amount
    private String paymentAmount;

    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;


    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_payment_screen);


        global=(MyApplication)getApplicationContext();

        listView = (ListView) findViewById(R.id.list);
        cost=(TextView)findViewById(R.id.cost);
        bottom = (TextView) findViewById(R.id.botom);
        tv_close = (TextView)findViewById(R.id.tv_close);
        cancel = (ImageView) findViewById(R.id.cancel);

        adapter = new PaymentFinalAdapter(this);
        listView.setAdapter(adapter);


        cost.setText("$ "+ global.getTotal_treatment_cost());


//        Intent intent = new Intent(PaymentScreen.this,PayPalService.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//        startService(intent);

        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);


        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPayment();

              //   new AsyncTaskRunner1().execute();
            }
        });



        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentScreen.this,PaymentMethods.class));
                overridePendingTransition(R.anim.pull_in_left,R.anim.pull_in_left);
                finish();
            }
        });


    }

    private void getPayment() {

        //Getting the amount
        paymentAmount =  global.getTotal_treatment_cost();

        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "USD", "Salon Booking Fee",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);


                   //     new AsyncTaskRunner1().execute();

                        //Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(this, ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount));

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }

    }



    public class AsyncTaskRunner1 extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {

            ws.BookingService(PaymentScreen.this);
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {

            //progressDialog.dismiss();
            //  getActivity().startActivity(new Intent(getActivity(),PhoneActivity.class));
        }

        @Override
        protected void onPreExecute() {

            //progressDialog = ProgressDialog.show(getActivity(),"Booking Salon... ","Wait!");
        }
    }


    @Override
    protected void onDestroy() {

        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }




}
