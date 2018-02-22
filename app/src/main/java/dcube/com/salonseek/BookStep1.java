package dcube.com.salonseek;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import utils.MyApplication;
import utils.SpecialListAdapter;
import utils.TreatmentListAdapter;
import webservices.GlobalConstants;

public class BookStep1 extends Activity {

    ListView listtreatment;
    ListView listspecials;
    TreatmentListAdapter treatmentListAdapter;
    SpecialListAdapter specialListAdapter;
    TextView next,tv_special_label;
    ImageView cancel;

    MyApplication global;
    String selected_treatments;
    HashMap<String,String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_book_step1);

        global = (MyApplication) getApplicationContext();
        global.setReadonly(true);

        cancel = (ImageView) findViewById(R.id.cancel);

        global.setTreatmentID("");

        global.setBookingDetails(map);

        tv_special_label = (TextView) findViewById(R.id.tv_special_label);

        next = (TextView) findViewById(R.id.confirm);
        next.bringToFront();

        listtreatment = (ListView) findViewById(R.id.listtreatment);
        listspecials = (ListView) findViewById(R.id.listspecial);

        treatmentListAdapter = new TreatmentListAdapter(this);
        specialListAdapter = new SpecialListAdapter(this);

        listspecials.setAdapter(specialListAdapter);
        listtreatment.setAdapter(treatmentListAdapter);

        setListViewHeightBasedOnItems(listspecials);
        setListViewHeightBasedOnItems(listtreatment);

        global.setTreatmentSelected(false);
        global.setSpecialTreatmentSelected(false);

//        global.setSpecial_selected_price(null);
//        global.setSpecial_selected_id(null);
//        global.setSpecial_selected_name(null);

//        global.setSelected_treatment_id(null);
//        global.setSelected_treatment_price(null);
//        global.setSelected_treatment_name(null);

        next.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if ( global.isTreatmentSelected() )
               {
                  global.getSalonDetailListing().get(0).get(GlobalConstants.SAL_ID);

                   global.getBookingDetails().put(GlobalConstants.BOOK_SALON_ID, global.getSalonDetailListing().get(0).get(GlobalConstants.SAL_ID));
                   global.getBookingDetails().put(GlobalConstants.BOOK_TREATMENT_ID, global.getTreatmentID());

                   startActivity(new Intent(BookStep1.this,BookStep2.class));

               }
               else
               {
                   Toast.makeText(BookStep1.this, "Choose atleast one Treatment", Toast.LENGTH_SHORT).show();
               }


           }
       });

       cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            finish();
           }
        });


        if (global.getOfferTreatmentListing().size() == 0)
        {
            tv_special_label.setVisibility(View.GONE);
        }
        else
        {
            tv_special_label.setVisibility(View.VISIBLE);
        }

    }

    public static boolean setListViewHeightBasedOnItems(ListView listView)
    {
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter != null)
        {
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

        }
        else {
            return false;
        }
    }




}
