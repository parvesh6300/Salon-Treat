package dcube.com.salonseek;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import utils.MyApplication;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

public class CardDetails extends Activity {

    TextView tv_close;
    ImageView cancel,card_type;
    Context context = CardDetails.this;
    EditText ed_card_no, ed_card_expiry, ed_cvc, ed_card_holder_name;
    TextView tv_pay,tv_card_type;

    MyApplication global;

    WebServicesHandler ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_card_details);

        tv_close = (TextView) findViewById(R.id.tv_close);
        cancel = (ImageView) findViewById(R.id.cancel);
        card_type = (ImageView) findViewById(R.id.card_type);

        global = (MyApplication) getApplicationContext();

        tv_pay = (TextView) findViewById(R.id.tv_pay);
        tv_card_type = (TextView) findViewById(R.id.tv_card_type);

        ed_card_no = (EditText) findViewById(R.id.ed_card_no);
        ed_card_expiry = (EditText) findViewById(R.id.ed_card_expiry);
        ed_cvc = (EditText) findViewById(R.id.ed_cvc);
        ed_card_holder_name = (EditText) findViewById(R.id.ed_card_holder_name);

        switch (AddPaymentMethod.card_type)
        {
            case 0:
                card_type.setImageResource(R.drawable.paypal);
                tv_card_type.setText("Paypal");
                global.setCard_type("paypal");
                break;

            case 1:
                card_type.setImageResource(R.drawable.visa);
                tv_card_type.setText("Visa");
                global.setCard_type("visa");
                break;

            case 2:
                card_type.setImageResource(R.drawable.mastercard);
                tv_card_type.setText("MasterCard");
                global.setCard_type("mastercard");
                break;

            case 3:
                card_type.setImageResource(R.drawable.americanexpress);
                tv_card_type.setText("American Express");
                global.setCard_type("amex");
                break;

        }


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  startActivity(new Intent(CardDetails.this, AddPaymentMethod.class));
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);  //pull_in_left

            }
        });


        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  startActivity(new Intent(CardDetails.this, PaymentMethods.class));
                overridePendingTransition(R.anim.pull_in_left, R.anim.pull_in_left);
                finish();
            }
        });


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

                }
            }
        });


        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ed_card_holder_name.getText().toString().matches("")) {
                    Toast.makeText(context, "Enter Card Holder name", Toast.LENGTH_SHORT).show();
                } else if (ed_card_no.getText().toString().matches("")) {
                    Toast.makeText(context, "Enter Card No", Toast.LENGTH_SHORT).show();
                } else if (ed_card_expiry.getText().toString().matches("")) {
                    Toast.makeText(context, "Enter Card Expiry Date", Toast.LENGTH_SHORT).show();
                } else if (ed_cvc.getText().toString().matches("")) {
                    Toast.makeText(context, "Enter CVV no", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    global.setCard_holder(ed_card_holder_name.getText().toString().trim());
                    global.setCard_no(ed_card_no.getText().toString().trim());
                    global.setCard_expiry(ed_card_expiry.getText().toString().trim());
                    global.setCard_cvv(ed_cvc.getText().toString().trim());
                    global.setCard_img_token(String.valueOf(AddPaymentMethod.card_type));
                    global.setUser_id(global.getLoginListing().get(0).get(GlobalConstants.USER_ID));

                    Log.e("USer id ","USer id "+ global.getLoginListing().get(0).get(GlobalConstants.USER_ID));

                 //   CardDetails();

                    new AddCardAsync().execute();

                }


            }
        });


    }



    public class AddCardAsync extends AsyncTask<String,String,String>
    {
        String result;

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Adding Card....");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                result =  ws.AddCard(context);

            }
            catch (Exception e)
            {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }

            Toast.makeText(context, ""+ result, Toast.LENGTH_SHORT).show();

            startActivity(new Intent(context,PaymentMethods.class));
            finish();


        }

    }



}
