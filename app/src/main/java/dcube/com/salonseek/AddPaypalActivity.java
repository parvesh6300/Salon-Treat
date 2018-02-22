package dcube.com.salonseek;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class AddPaypalActivity extends Activity {

    EditText ed_user_id, ed_pwd;
    TextView tv_add,tv_close;
    ImageView cancel;
    MyApplication global;
    Context context = this;

    WebServicesHandler ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add_paypal);

        ed_user_id = (EditText) findViewById(R.id.ed_user_id);
        ed_pwd = (EditText) findViewById(R.id.ed_pwd);

        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_close = (TextView) findViewById(R.id.tv_close);

        cancel = (ImageView) findViewById(R.id.cancel);

        global = (MyApplication) getApplicationContext();

        tv_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (ed_user_id.getText().toString().matches("")) {
                    Toast.makeText(context, "Enter Paypal Account", Toast.LENGTH_SHORT).show();
                } else if (ed_pwd.getText().toString().matches("")) {
                    Toast.makeText(context, "Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    global.setCard_holder(ed_user_id.getText().toString().trim());
                    global.setCard_no("");
                    global.setCard_expiry("");
                    global.setCard_cvv(ed_pwd.getText().toString().trim());
                    global.setCard_img_token(String.valueOf(AddPaymentMethod.card_type));
                    global.setUser_id(global.getLoginListing().get(0).get(GlobalConstants.USER_ID));
                    global.setCard_type("paypal");

                    Log.e("USer id ", "USer id " + global.getLoginListing().get(0).get(GlobalConstants.USER_ID));

                    //   CardDetails();
                    new AddCardAsync().execute();

                }
            }


        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  startActivity(new Intent(context, AddPaymentMethod.class));
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);  //pull_in_left
            }
        });


        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                startActivity(new Intent(context, PaymentMethods.class));
                finish();
             //   overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

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
