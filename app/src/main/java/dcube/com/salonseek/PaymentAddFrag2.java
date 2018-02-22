package dcube.com.salonseek;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import utils.MyApplication;
import webservices.WebServicesHandler;

/**
 * Created by yadi on 29/07/16.
 */
public class PaymentAddFrag2 extends Fragment {

    public static final String PUBLISHABLE_KEY = "pk_test_2DwXZVhKXBStdAqej4ImVMhr";
    EditText ed_card_no, ed_card_expiry, ed_cvc, ed_card_holder_name;
    TextView tv_pay;
    Card card;
    Stripe stripe;
    MyApplication global;

    WebServicesHandler ws;

    public static PaymentAddFrag2 newInstance(String text) {

        PaymentAddFrag2 f = new PaymentAddFrag2();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.addpayment2, container, false);

        global = (MyApplication) getActivity().getApplicationContext();

        tv_pay = (TextView) v.findViewById(R.id.tv_pay);

        ed_card_no = (EditText) v.findViewById(R.id.ed_card_no);
        ed_card_expiry = (EditText) v.findViewById(R.id.ed_card_expiry);
        ed_cvc = (EditText) v.findViewById(R.id.ed_cvc);
        ed_card_holder_name = (EditText) v.findViewById(R.id.ed_card_holder_name);


        ed_card_expiry.addTextChangedListener(new TextWatcher() {
            int len = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                String str = ed_card_expiry.getText().toString();
                len = str.length();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                String str = ed_card_expiry.getText().toString();

                if (str.length() == 2 && len < str.length())    //len check for backspace
                {
                    ed_card_expiry.append("/");

                    //   ed_card_expiry.setText(ed_card_expiry.getText().insert(2, "/"));

                }
            }
        });


        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ed_card_holder_name.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), "Enter Card Holder name", Toast.LENGTH_SHORT).show();
                } else if (ed_card_no.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), "Enter Card No", Toast.LENGTH_SHORT).show();
                } else if (ed_card_expiry.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), "Enter Card Expiry Date", Toast.LENGTH_SHORT).show();
                } else if (ed_cvc.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), "Enter CVV no", Toast.LENGTH_SHORT).show();
                } else {
                    String expiry = ed_card_expiry.getText().toString();

                    String[] ary_expiry = expiry.split("/");

                    String month = ary_expiry[0];
                    String year = ary_expiry[1];

                    card = new Card(
                            ed_card_no.getText().toString().trim(),
                            Integer.parseInt(month),
                            Integer.parseInt(year),
                            ed_cvc.getText().toString().trim()
                    );


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

                            callStripe();




                    }

                }


            }
        });


        return v;


    }

    public void callStripe(){

        stripe = new Stripe();

        stripe.createToken(card, PUBLISHABLE_KEY, new TokenCallback() {
            @Override
            public void onError(Exception error) {

                Log.e("Error", "Error " + error.getLocalizedMessage());

                Toast.makeText(getActivity(), "Some error occured", Toast.LENGTH_LONG).show();
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


    public class AsyncTaskRunner extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;
        private String resp;

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(getActivity(), "Loading ... ", "Wait!");
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                ws.SendToken(getActivity());

                if (global.getPaymentStatus() == 1) {

                    ws.BookingService(getActivity());
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

                Intent i = new Intent(getActivity(), PhoneActivity.class);
                startActivity(i);
            } else {

                Toast.makeText(getContext(), "Some error occured...", Toast.LENGTH_SHORT).show();
            }


        }


    }


}




