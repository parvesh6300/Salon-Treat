package dcube.com.salonseek;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by yadi on 27/07/16.
 */
public class PaymentFrag2 extends Fragment{

    ListView listView;
    PaymentFinalAdapter adapter;

    TextView bottom,cost;

    WebServicesHandler ws;

    MyApplication global;
    Double cost_value;

    Context context;
    //Payment Amount
    private String paymentAmount;

    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;


    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.payment2, container, false);

        adapter = new PaymentFinalAdapter(getActivity());
        listView = (ListView) v.findViewById(R.id.list);

        bottom = (TextView) v.findViewById(R.id.botom);
        listView.setAdapter(adapter);


        context  = getActivity();

        try {
            for (String costdetail : global.getSelected_treatment_price()) {

                cost_value += Double.parseDouble(costdetail);

            }
        } catch (Exception e) {}


        cost=(TextView)v.findViewById(R.id.cost);

        Intent intent = new Intent(context, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        context.startService(intent);

        global=(MyApplication)getActivity().getApplicationContext();

        cost.setText( String.valueOf(cost_value) + ".00");

        Log.e("Cost","Cost "+ cost.getText().toString());


        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 getPayment();

               new AsyncTaskRunner1().execute();
            }
        });

        return v;
    }

    public static PaymentFrag2 newInstance(String text) {

        PaymentFrag2 f = new PaymentFrag2();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }



    private void getPayment() {

        // global.getBookedListing().get(1).get(GlobalConstants.BOOKED_TREATMENT_TOTAL);

        //Getting the amount from editText
        //paymentAmount =  global.getTotal_treatment_cost()+".00";


        paymentAmount="100";

       // paymentAmount = String.valueOf(cost_value);

        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "USD", "Simplified Coding Fee",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(getActivity(), PayPalService.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

//


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

                            //Starting a new activity for the payment details and also putting the payment details with intent
                            startActivity(new Intent(getActivity(), ConfirmationActivity.class)
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

    @Override
    public void onDestroyView() {
        getActivity().stopService(new Intent(getActivity(), PayPalService.class));
        super.onDestroyView();
    }

    public class AsyncTaskRunner1 extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {

            ws.BookingService(getActivity());
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

}
