package dcube.com.salonseek;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import utils.MyApplication;
import utils.UserCardAdapter;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

public class AccountActivity extends Activity{

    RelativeLayout myInfo;
    TextView close;
    TextView save;
    Context context = AccountActivity.this;
    ListView lv_cards;
    UserCardAdapter adapter;
    WebServicesHandler ws;
    MyApplication global;
    RelativeLayout addnewcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        myInfo = (RelativeLayout) findViewById(R.id.myinfo);

        close = (TextView)findViewById(R.id.close);
        save =  (TextView)findViewById(R.id.save);
        lv_cards= (ListView) findViewById(R.id.lv_cards);
        addnewcard = (RelativeLayout)findViewById(R.id.addnewcard);

        global = (MyApplication) getApplicationContext();

        global.setUser_id(global.getLoginListing().get(0).get(GlobalConstants.USER_ID));

        new AsyncFetchCardDetails().execute();

        adapter = new UserCardAdapter(context);
        lv_cards.setAdapter(adapter);


        myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AccountActivity.this,MyInfoActivity.class));

            }
        });

        addnewcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this,AddPaymentMethod.class));

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


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

            adapter = new UserCardAdapter(context);
            adapter.notifyDataSetChanged();
            lv_cards.setAdapter(adapter);


            //  CardDetails();
        }


    }

}
