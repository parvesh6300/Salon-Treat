package dcube.com.salonseek;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import utils.DialogAdapter;
import utils.MyApplication;
import webservices.WebServicesHandler;

public class FilterScreen extends Activity implements View.OnClickListener{


    TextView tv_char,tv_rating,tv_distance,tv_nearest,tv_20_km,tv_40_km,tv_prev_visit,tv_fav,tv_done,tv_popularity,tv_reset;

    RelativeLayout rel_price,rel_salon_type;

    LinearLayout lin_fav,lin_prev_visit;

    MyApplication global;

    WebServicesHandler ws;

    ArrayList<String> mAnimals = new ArrayList<String>();
    ArrayList<String> mPRice = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_filter_screen);

     //   getSupportActionBar().hide();

        global=(MyApplication) getApplicationContext();

        tv_char=(TextView)findViewById(R.id.tv_char);
        tv_rating=(TextView)findViewById(R.id.tv_rating);
        tv_distance=(TextView)findViewById(R.id.tv_distance);
        tv_nearest=(TextView)findViewById(R.id.tv_nearest);
        tv_20_km=(TextView)findViewById(R.id.tv_20_km);
        tv_40_km=(TextView)findViewById(R.id.tv_40_km);
        tv_prev_visit=(TextView)findViewById(R.id.tv_prev_visit);
        tv_fav=(TextView)findViewById(R.id.tv_fav);
        tv_done=(TextView)findViewById(R.id.tv_done);
        tv_popularity=(TextView)findViewById(R.id.tv_popularity);
        tv_reset=(TextView)findViewById(R.id.tv_reset);

        rel_salon_type=(RelativeLayout)findViewById(R.id.rel_salon_type);
        rel_price=(RelativeLayout)findViewById(R.id.rel_price);

        lin_fav = (LinearLayout)findViewById(R.id.lin_fav);
        lin_prev_visit = (LinearLayout) findViewById(R.id.lin_prev_visit);

        tv_char.setOnClickListener(this);
        tv_rating.setOnClickListener(this);
        tv_distance.setOnClickListener(this);
        tv_nearest.setOnClickListener(this);
        tv_20_km.setOnClickListener(this);
        tv_40_km.setOnClickListener(this);
        tv_prev_visit.setOnClickListener(this);
        tv_fav.setOnClickListener(this);
        tv_done.setOnClickListener(this);
        tv_popularity.setOnClickListener(this);

        rel_salon_type.setOnClickListener(this);
        rel_price.setOnClickListener(this);

        lin_fav.setOnClickListener(this);
        lin_prev_visit.setOnClickListener(this);

        resetFilterValues();

        global.setSortBy("all");
        global.setDistance("10");


        mAnimals.add("Cat");
        mAnimals.add("Dog");
        mAnimals.add("Horse");
        mAnimals.add("Elephant");
        mAnimals.add("Rat");
        mAnimals.add("Lion");

        mPRice.add("$");
        mPRice.add("$$");
        mPRice.add("$$$");
        mPRice.add("$$$$");


    }

    @Override
    public void onClick(View v) {

        if (v==tv_done)
        {
            global.setFilter(true);
            finish();
            overridePendingTransition(R.anim.slide_out_up,R.anim.slide_out_up);

            //new AsyncTaskRunner().execute();
        }


        if (v==tv_reset)
        {
            resetFilterValues();
        }

        if (v==tv_char)
        {
            tv_char.setTextColor(Color.parseColor("#ffffff"));
            tv_char.setBackgroundColor(Color.parseColor("#438bc5"));

            tv_rating.setTextColor(Color.parseColor("#000000"));
            tv_rating.setBackgroundColor(Color.parseColor("#ffffff"));

            tv_distance.setTextColor(Color.parseColor("#000000"));
            tv_distance.setBackgroundColor(Color.parseColor("#ffffff"));

            tv_popularity.setTextColor(Color.parseColor("#000000"));
            tv_popularity.setBackgroundColor(Color.parseColor("#ffffff"));


            global.setSortBy("all");

            // Reset Remaining filters
            global.setFilterRating("");
            global.setFilterLat("");
            global.setFilterlong("");

            Log.e("A-Z","A-Z "+global.getSortBy());

        }


        if (v==tv_rating)
        {
            tv_rating.setTextColor(Color.parseColor("#ffffff"));
            tv_rating.setBackgroundColor(Color.parseColor("#438bc5"));

            tv_char.setTextColor(Color.parseColor("#000000"));
            tv_char.setBackgroundColor(Color.parseColor("#ffffff"));

            tv_distance.setTextColor(Color.parseColor("#000000"));
            tv_distance.setBackgroundColor(Color.parseColor("#ffffff"));

            tv_popularity.setTextColor(Color.parseColor("#000000"));
            tv_popularity.setBackgroundColor(Color.parseColor("#ffffff"));

            global.setFilterRating("1");

            // Reset Remaining filters
            global.setSortBy("");
            global.setFilterLat("");
            global.setFilterlong("");

            Log.e("Rating","Rating "+global.getFilterRating());

        }

        if (v==tv_distance)
        {
            tv_distance.setTextColor(Color.parseColor("#ffffff"));
            tv_distance.setBackgroundColor(Color.parseColor("#438bc5"));

            tv_char.setTextColor(Color.parseColor("#000000"));
            tv_char.setBackgroundColor(Color.parseColor("#ffffff"));

            tv_rating.setTextColor(Color.parseColor("#000000"));
            tv_rating.setBackgroundColor(Color.parseColor("#ffffff"));

            tv_popularity.setTextColor(Color.parseColor("#000000"));
            tv_popularity.setBackgroundColor(Color.parseColor("#ffffff"));


            global.setFilterLat(global.getLat());

            global.setFilterlong(global.getFilterlong());

            Log.e("Distance","Lat "+global.getFilterlat());
            Log.e("Distance","Long "+global.getFilterlong());

            // Reset Remaining filters
            global.setSortBy("");
            global.setFilterRating("");


        }


        if (v==tv_popularity)
        {
            tv_popularity.setTextColor(Color.parseColor("#ffffff"));
            tv_popularity.setBackgroundColor(Color.parseColor("#438bc5"));

            tv_char.setTextColor(Color.parseColor("#000000"));
            tv_char.setBackgroundColor(Color.parseColor("#ffffff"));

            tv_rating.setTextColor(Color.parseColor("#000000"));
            tv_rating.setBackgroundColor(Color.parseColor("#ffffff"));

            tv_distance.setTextColor(Color.parseColor("#000000"));
            tv_distance.setBackgroundColor(Color.parseColor("#ffffff"));


            global.setSortBy("popular");

            Log.e("Popularity","Popularity "+global.getSortBy());


            // Reset Remaining filters
            global.setFilterRating("");
            global.setFilterLat("");
            global.setFilterlong("");

        }



        if (v==tv_nearest)
        {
            tv_nearest.setTextColor(Color.parseColor("#ffffff"));
            tv_nearest.setBackgroundColor(Color.parseColor("#438bc5"));

            tv_20_km.setTextColor(Color.parseColor("#000000"));
            tv_20_km.setBackgroundColor(Color.parseColor("#ffffff"));

            tv_40_km.setTextColor(Color.parseColor("#000000"));
            tv_40_km.setBackgroundColor(Color.parseColor("#ffffff"));

            global.setDistance("10");

            Log.e("Distance","Distance "+global.getDistance());


            // Reset Remaining filters


        }


        if (v==tv_20_km)
        {
            tv_20_km.setTextColor(Color.parseColor("#ffffff"));
            tv_20_km.setBackgroundColor(Color.parseColor("#438bc5"));

            tv_nearest.setTextColor(Color.parseColor("#000000"));
            tv_nearest.setBackgroundColor(Color.parseColor("#ffffff"));

            tv_40_km.setTextColor(Color.parseColor("#000000"));
            tv_40_km.setBackgroundColor(Color.parseColor("#ffffff"));


            global.setDistance("20");

            Log.e("Distance","Long "+global.getDistance());


            // Reset Remaining filters

        }

        if (v==tv_40_km)
        {
            tv_40_km.setTextColor(Color.parseColor("#ffffff"));
            tv_40_km.setBackgroundColor(Color.parseColor("#438bc5"));

            tv_nearest.setTextColor(Color.parseColor("#000000"));
            tv_nearest.setBackgroundColor(Color.parseColor("#ffffff"));

            tv_20_km.setTextColor(Color.parseColor("#000000"));
            tv_20_km.setBackgroundColor(Color.parseColor("#ffffff"));

            global.setDistance("40");

            Log.e("Distance","Long "+global.getDistance());


            // Reset Remaining filters


        }


        if (v==rel_salon_type) {

            final Dialog dialog= new Dialog(this);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.setContentView(R.layout.customdialogbox);

            TextView title= (TextView)dialog.findViewById(R.id.title);
            TextView tv_apply_filter=(TextView) dialog.findViewById(R.id.tv_apply_filter);
            TextView tv_cancel=(TextView) dialog.findViewById(R.id.tv_cancel);

            final ListView list= (ListView) dialog.findViewById(R.id.list);

            final DialogAdapter dialogAdapter= new DialogAdapter(this,mAnimals);

            title.setText("Select Salon Type");

            dialog.setCancelable(true);

            list.setAdapter(dialogAdapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    dialogAdapter.setSelectedIndex(position);
                    dialogAdapter.notifyDataSetChanged();
                }
            });


            tv_apply_filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.cancel();
                }
            });

            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });


           // dialog.create();
            dialog.show();

        }


        if (v==rel_price)
        {
            final Dialog dialog= new Dialog(this);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.setContentView(R.layout.customdialogbox);

            TextView title= (TextView)dialog.findViewById(R.id.title);
            TextView tv_apply_filter=(TextView) dialog.findViewById(R.id.tv_apply_filter);
            TextView tv_cancel=(TextView) dialog.findViewById(R.id.tv_cancel);

            final ListView list= (ListView) dialog.findViewById(R.id.list);

            final DialogAdapter dialogAdapter= new DialogAdapter(this,mPRice);

            title.setText("Select Price");

            dialog.setCancelable(true);

            list.setAdapter(dialogAdapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    dialogAdapter.setSelectedIndex(position);
                    dialogAdapter.notifyDataSetChanged();
                }
            });


            tv_apply_filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.cancel();
                }
            });

            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

        //    dialog.create();
            dialog.show();

        }


        if (v == lin_fav)
        {
            global.setFavorite_filter(true);
            global.setFilter(false);
            finish();
            overridePendingTransition(R.anim.slide_out_up,R.anim.slide_out_up);
        }

        if (v == lin_prev_visit)
        {
            global.setPrev_visit_filter(true);
            finish();
            overridePendingTransition(R.anim.slide_out_up,R.anim.slide_out_up);
        }


    }


    public void resetFilterValues()
    {
        global.setSortBy("");
        global.setFilterRating("");
        global.setDistance("");
        global.setFilterLat("");
        global.setFilterlong("");
        global.setHighLow("");

        tv_char.setTextColor(Color.parseColor("#000000"));  //ffffff
        tv_char.setBackgroundColor(Color.parseColor("#ffffff"));  // 438bc5

        tv_rating.setTextColor(Color.parseColor("#000000"));
        tv_rating.setBackgroundColor(Color.parseColor("#ffffff"));

        tv_distance.setTextColor(Color.parseColor("#000000"));
        tv_distance.setBackgroundColor(Color.parseColor("#ffffff"));

        tv_popularity.setTextColor(Color.parseColor("#000000"));
        tv_popularity.setBackgroundColor(Color.parseColor("#ffffff"));

        tv_nearest.setTextColor(Color.parseColor("#000000"));    //ffffff
        tv_nearest.setBackgroundColor(Color.parseColor("#ffffff"));    // 438bc5

        tv_20_km.setTextColor(Color.parseColor("#000000"));
        tv_20_km.setBackgroundColor(Color.parseColor("#ffffff"));

        tv_40_km.setTextColor(Color.parseColor("#000000"));
        tv_40_km.setBackgroundColor(Color.parseColor("#ffffff"));

    }


}
