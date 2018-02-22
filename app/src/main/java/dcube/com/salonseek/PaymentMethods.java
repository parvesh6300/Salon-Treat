package dcube.com.salonseek;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stripe.android.Stripe;
import com.stripe.android.model.Card;

import utils.MyApplication;
import utils.PaymentAdapter;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

public class PaymentMethods extends Activity {

    public static final String PUBLISHABLE_KEY = "pk_test_2DwXZVhKXBStdAqej4ImVMhr";
    public Context context = PaymentMethods.this;
    ListView listView;
    PaymentAdapter adapter;
    RelativeLayout addpayment,rel_discount;
    MyApplication global;


    TextView cost;
    float price;
    static int card_select_pos;

    Double cost_value;

    ImageView cancel;
    TextView tv_close;
    WebServicesHandler ws;
    Card card;
    Stripe stripe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_payment_methods);

        listView = (ListView) findViewById(R.id.list);

        cost = (TextView) findViewById(R.id.cost);
        tv_close = (TextView) findViewById(R.id.tv_close);
        cancel = (ImageView) findViewById(R.id.cancel);

        global = (MyApplication) getApplicationContext();

        //code to set adapter to populate list
        View footerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.cardlistfooter, null, false);
        listView.addFooterView(footerView);

        addpayment = (RelativeLayout) findViewById(R.id.addpayment);
        rel_discount = (RelativeLayout) findViewById(R.id.rel_discount);
//
//        View footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.discount_footer, null, false);
//        ListView.addFooterView(footerView);


        try {

            for (String costdetail : global.getSelected_treatment_price()) {

                Log.e("Cost", "Cost " + costdetail);

                price += Float.parseFloat(costdetail);

                Log.e("float", "Cost " + price);


            }

            for (String cost : global.getSpecial_selected_price()) {
                Log.e("Cost", "Cost " + cost);

                price += Float.parseFloat(cost);

                Log.e("float", "Cost " + price);
            }

        } catch (Exception e) {
            Log.e("Exception", "Exception in adding cost");
        }

        price = (price*20)/100;

        cost.setText("$ " + String.valueOf(price) + "");

        global.setTotal_treatment_cost(String.valueOf(price));



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                card_select_pos = pos;

                startActivity(new Intent(PaymentMethods.this,InvoiceActivity.class));
                overridePendingTransition(R.anim.pull_in_right, R.anim.pull_in_right);
                finish();
                //CardDetails();

            }
        });



        addpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(PaymentMethods.this, AddPaymentMethod.class));
                overridePendingTransition(R.anim.pull_in_right, R.anim.pull_in_right);
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
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

            }
        });


        rel_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(PaymentMethods.this,DiscountCodeActivity.class));
                overridePendingTransition(R.anim.pull_in_right, R.anim.pull_in_right);

            }
        });

        try {

            global.setUser_id(global.getLoginListing().get(0).get(GlobalConstants.USER_ID));

        } catch (Exception e) {

        }

        new AsyncFetchCardDetails().execute();






    }



    public class AsyncFetchCardDetails extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading Card Details...");
            progressDialog.show();

        }


        @Override
        protected String doInBackground(String... params) {

            try {
                ws.FetchCardDetails(context);

            } catch (Exception e) {

            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {

            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }

            adapter = new PaymentAdapter(PaymentMethods.this);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);

            //  CardDetails();
        }

    }


}
