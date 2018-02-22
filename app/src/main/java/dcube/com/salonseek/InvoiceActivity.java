package dcube.com.salonseek;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.exception.AuthenticationException;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.json.JSONException;

import java.math.BigDecimal;

import utils.MyApplication;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

public class InvoiceActivity extends Activity {

    public static final String PUBLISHABLE_KEY = "pk_test_2DwXZVhKXBStdAqej4ImVMhr";
    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;
    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);

    TextView tv_cost, tv_card_type, tv_card_detail, tv_treatment, tv_stylist, tv_time, tv_close, tv_book_aptmt;
    ImageView iv_card_type, cancel;
    MyApplication global;
    String treatment_name;
    String special_treatment;
    int[] imageId = {R.drawable.paypalicon, R.drawable.visaicon, R.drawable.mastercard, R.drawable.americanexpress};
    Card card;
    Stripe stripe;
    Context context = InvoiceActivity.this;
    WebServicesHandler ws;
    //Payment Amount
    private String paymentAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_invoice);

        tv_cost = (TextView) findViewById(R.id.tv_cost);
        tv_card_type = (TextView) findViewById(R.id.tv_card_type);
        tv_card_detail = (TextView) findViewById(R.id.tv_card_detail);
        tv_treatment = (TextView) findViewById(R.id.tv_treatment);
        tv_stylist = (TextView) findViewById(R.id.tv_stylist);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_close = (TextView) findViewById(R.id.tv_close);
        tv_book_aptmt = (TextView) findViewById(R.id.tv_book_aptmt);

        iv_card_type = (ImageView) findViewById(R.id.iv_card_type);
        cancel = (ImageView) findViewById(R.id.cancel);

        global = (MyApplication) getApplicationContext();

        tv_cost.setText("$ " + global.getTotal_treatment_cost());

        tv_stylist.setText(global.getStylist_name());

        tv_time.setText(global.getBookingTime());

        tv_card_type.setText(global.getAl_card_details().get(PaymentMethods.card_select_pos).get(GlobalConstants.CARD_HOLDER));

        tv_card_detail.setText(global.getAl_card_details().get(PaymentMethods.card_select_pos).get(GlobalConstants.Card_Number));

        final String str_card_type = global.getAl_card_details().get(PaymentMethods.card_select_pos).get(GlobalConstants.Card_type);

        Log.e("CardType", "CardType " + str_card_type);

        switch (str_card_type) {

            case "paypal":
                iv_card_type.setImageResource(imageId[0]);
                break;

            case "visa":
                iv_card_type.setImageResource(imageId[1]);
                break;

            case "mastercard":
                iv_card_type.setImageResource(imageId[2]);
                break;

            case "amex":
                iv_card_type.setImageResource(imageId[3]);
                break;

            default:
                iv_card_type.setImageResource(imageId[1]);
                break;

        }


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


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(InvoiceActivity.this, PaymentMethods.class));
                overridePendingTransition(R.anim.pull_in_left, R.anim.pull_in_left);
                finish();
            }
        });


        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        tv_book_aptmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (str_card_type.equalsIgnoreCase("paypal")) {
                    //  startActivity(new Intent(InvoiceActivity.this,PaymentScreen.class));
                    getPayment();
                } else {
                    CardDetails();
                }
            }
        });


        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);


    }


    private void getPayment() {

        //Getting the amount
        paymentAmount = global.getTotal_treatment_cost();

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


    public void CardDetails() {

        try {
            String expiry = global.getAl_card_details().get(PaymentMethods.card_select_pos).get(GlobalConstants.CARD_Expiry);

            String[] ary_expiry = expiry.split("/");

            String month = ary_expiry[0];
            String year = ary_expiry[1];

            card = new Card(
                    global.getAl_card_details().get(PaymentMethods.card_select_pos).get(GlobalConstants.Card_Number),
                    Integer.parseInt(month),
                    Integer.parseInt(year),
                    global.getAl_card_details().get(PaymentMethods.card_select_pos).get(GlobalConstants.CARD_CVV)
            );

        } catch (Exception e) {
            Log.e("Error", "Card Call error");
        }


//                card= new Card(
//                        "5610591081018250",
//                        12,
//                        16,
//                        "122"
//                );


        card.validateNumber();
        card.validateCVC();


        if (!card.validateCard()) {

            Log.e("Error", "Check Card Details ");

            // Show errors
        } else {
            try {
                callStripe();

            } catch (AuthenticationException e) {
                e.printStackTrace();
            }
        }

    }

    public void callStripe() throws AuthenticationException {

        stripe = new Stripe();

        stripe.createToken(card, PUBLISHABLE_KEY, new TokenCallback() {
            @Override
            public void onError(Exception error) {

                Log.e("Error", "Error " + error.getLocalizedMessage());

                Toast.makeText(context, "Some error occured", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(Token token) {

                Log.e("TOken", "TOken " + token);

                global.setToken_id(token.getId());

                Log.e("Tokenid", "Tokenid " + global.getToken_id());

                new AsyncTaskRunner().execute();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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
                        startActivity(new Intent(this, PhoneActivity.class)  //ConfirmationActivity
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

    public class AsyncTaskRunner extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;
        private String resp;

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(context, "Loading ... ", "Wait!");
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                ws.SendToken(context);

                if (global.getPaymentStatus() == 1) {

                    ws.BookingService(context);
                } else {
                    Toast.makeText(context, "Payment Failed...\n Try Again", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {

            }

            return resp;
        }

        @Override
        protected void onPostExecute(String result) {

            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }

            if (global.getBooking_status() == 1) {

                Intent i = new Intent(context, PhoneActivity.class);
                startActivity(i);
                finish();

            } else {

                Toast.makeText(context, "Some error occured...", Toast.LENGTH_SHORT).show();
            }


        }


    }


    @Override
    protected void onDestroy() {

        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
