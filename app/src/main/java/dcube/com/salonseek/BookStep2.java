package dcube.com.salonseek;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import utils.MyApplication;
import utils.StylistAdapter;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

public class BookStep2 extends Activity {

    ListView stylistlist;
    StylistAdapter adapter;
    TextView next;
    TextView name;
    TextView type;
    TextView offer;
    ImageView selected;
    ImageView stylist;

    WebServicesHandler ws;

    MyApplication global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_step2);

        next = (TextView) findViewById(R.id.confirm);
        stylistlist = (ListView) findViewById(R.id.liststylist);


        new StylistAsyncTask().execute();

        global = (MyApplication) getApplicationContext();

        global.setStylist_name("");
        global.setStylistID("");


        stylistlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                adapter.setSelectedIndex(position);
                adapter.notifyDataSetChanged();

                global.setStylistID(global.getStylistListing().get(position).get(GlobalConstants.STYLIST_ID));
                global.setStylist_name(global.getStylistListing().get(position).get(GlobalConstants.STYLIST_NAME));

//                if(position == 0)
//                {
//                    global.setStylistID(global.getStylistListing().get(1).get(GlobalConstants.STYLIST_ID));
//                    global.setStylist_name(global.getStylistListing().get(1).get(GlobalConstants.STYLIST_NAME));
//
//                }
//                else
//                {
//                    global.setStylistID(global.getStylistListing().get(position).get(GlobalConstants.STYLIST_ID));
//                    global.setStylist_name(global.getStylistListing().get(position).get(GlobalConstants.STYLIST_NAME));
//                }

            }
        });


        setListViewHeightBasedOnItems(stylistlist);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (global.getStylistID().matches(""))
                {
                    Toast.makeText(BookStep2.this, "Choose one stylist...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    global.getBookingDetails().put(GlobalConstants.BOOK_STYLIST_ID, global.getStylistID());

                    Log.e("Stylistid","Stylistid "+global.getStylistID());

                    startActivity(new Intent(BookStep2.this,BookStep3.class));

                }
            }
        });
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }


    private class StylistAsyncTask extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(BookStep2.this, "Getting Available Stylist.. ", "Please Wait..!");
        }

        @Override
        protected String doInBackground(String... params) {

            try
            {
                ws.AvailableStylistService(BookStep2.this);
            }
            catch (Exception e){

            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();

            if (global.getStylistListing().size() > 0)
            {
                adapter = new StylistAdapter(BookStep2.this);
                stylistlist.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
            else
            {
                Toast.makeText(global, "No Stylist is Available", Toast.LENGTH_SHORT).show();
            }

            //adpater = new ArrayAdapter<String>(BookStep3.this,android.R.layout.simple_list_item_single_choice,global.getTodayListing());
        }

    }

}
