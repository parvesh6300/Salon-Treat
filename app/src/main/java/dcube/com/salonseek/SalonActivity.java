package dcube.com.salonseek;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import utils.CustomRatingBarBlue;
import utils.CustomRatingBarWhite;
import utils.Deal;
import utils.DealsAdapter;
import utils.HorizontalListView;
import utils.MyApplication;
import utils.SalonImagePopUpAdapter;
import utils.Utilities;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

public class SalonActivity extends Activity {

    RelativeLayout treatment;
    RelativeLayout about;
    RelativeLayout reviews;

    TextView booknow,tv_products;
    CustomRatingBarBlue customRatingBarBlue;

    LinearLayout linearChart;
    LinearLayout chartText;

    CircleImageView salonPic;
    TextView salonName;
    TextView address;
    TextView contact;

    TextView tv_special_deals;
    WebServicesHandler ws;
    MyApplication global;
    RelativeLayout salonbg;

    TextView mon;
    TextView tue;
    TextView wed;
    TextView thu;
    TextView fri;
    TextView sat;
    TextView sun;

    ImageView iv_paypal;
    ImageView iv_visa;
    ImageView iv_master;
    ImageView iv_american;
    ImageView iv_apple_pay;
    ImageView like;
    ImageView iv_get_dir;

    int[] ar_rating = new int[5];
    TextView rating;
    Gallery ga;

    CustomRatingBarWhite ratingBarWhite;
    CustomRatingBarBlue ratingBarBlue;
    Context context = SalonActivity.this;
    Utilities utils;
    private List<Deal> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DealsAdapter mAdapter;

    HorizontalListView lv_salon_images;

    public static Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_salon);

        global = (MyApplication) getApplicationContext();

        new AsyncTaskRunner1().execute();

        iv_paypal = (ImageView) findViewById(R.id.iv_paypal);
        iv_visa = (ImageView) findViewById(R.id.iv_visa);
        iv_master = (ImageView) findViewById(R.id.iv_master);
        iv_american = (ImageView) findViewById(R.id.iv_american);
     //   iv_apple_pay = (ImageView) findViewById(R.id.iv_apple_pay);
        like = (ImageView) findViewById(R.id.like);
        iv_get_dir = (ImageView) findViewById(R.id.iv_get_dir);

        lv_salon_images = (HorizontalListView) findViewById(R.id.lv_salon_images);

        iv_paypal.setVisibility(View.GONE);
        iv_visa.setVisibility(View.GONE);
        iv_master.setVisibility(View.GONE);
        iv_american.setVisibility(View.GONE);
   //     iv_apple_pay.setVisibility(View.GONE);

        tv_products = (TextView) findViewById(R.id.tv_products);
        salonName = (TextView) findViewById(R.id.salonname);
        treatment = (RelativeLayout) findViewById(R.id.treatment);
        about = (RelativeLayout) findViewById(R.id.about);
        reviews = (RelativeLayout) findViewById(R.id.review);

        booknow = (TextView) findViewById(R.id.booknow);
        tv_special_deals = (TextView) findViewById(R.id.tv_special_deals);
        contact = (TextView) findViewById(R.id.contact);
        rating = (TextView) findViewById(R.id.rating_text);
        address = (TextView) findViewById(R.id.location);

        ratingBarBlue = (CustomRatingBarBlue) findViewById(R.id.ratingbarblue);
        ratingBarWhite = (CustomRatingBarWhite) findViewById(R.id.ratingbarwhite);
        customRatingBarBlue = (CustomRatingBarBlue) findViewById(R.id.ratingbarblue);
        salonPic = (CircleImageView) findViewById(R.id.salonimage);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        salonbg = (RelativeLayout) findViewById(R.id.salonbg);
   //     ga = (Gallery) findViewById(R.id.Gallery01);
        linearChart = (LinearLayout) findViewById(R.id.linearChart);
        chartText = (LinearLayout) findViewById(R.id.ratingtext);

        mon = (TextView) findViewById(R.id.mon);
        tue = (TextView) findViewById(R.id.tue);
        wed = (TextView) findViewById(R.id.wed);
        thu = (TextView) findViewById(R.id.thu);
        fri = (TextView) findViewById(R.id.fri);
        sat = (TextView) findViewById(R.id.sat);
        sun = (TextView) findViewById(R.id.sun);

   /*   bar_rating5=(LinearLayout)findViewById(R.id.bar_rating5);
        bar_rating4=(LinearLayout)findViewById(R.id.bar_rating4);
        bar_rating3=(LinearLayout)findViewById(R.id.bar_rating3);
        bar_rating2=(LinearLayout)findViewById(R.id.bar_rating2);
        bar_rating1=(LinearLayout)findViewById(R.id.bar_rating1);
    */

        tv_special_deals.setVisibility(View.VISIBLE);


        h = new Handler()
        {

            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch(msg.what) {

                    case 0:
                        finish();
                        break;

                }
            }

        };




        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                global.setUser_id(global.getLoginListing().get(0).get(GlobalConstants.USER_ID));

                global.setMake_salon_fav_id(global.getSalonDetailListing().get(0).get(GlobalConstants.SAL_ID));

                Log.e("Userid", "" + global.getUser_id());
                Log.e("Salonid", "" + global.getMake_salon_fav_id());

                if (global.getIs_salon_fav() == 0) {
                    like.setBackgroundResource(R.drawable.heartselected);
                } else {
                    like.setBackgroundResource(R.drawable.heart);
                }

                new MakeSalonFav().execute();

            }
        });

        iv_get_dir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String salon_lat = global.getSalonDetailListing().get(0).get(GlobalConstants.LATITUDE);
                String salon_long = global.getSalonDetailListing().get(0).get(GlobalConstants.LONGITUDE);

                startActivity(new Intent(SalonActivity.this, WebDirectActivity.class));

                // GetDirections   WebDirectActivity

            }
        });


        utils = new Utilities();

        String salon_image_url = GlobalConstants.SALON_IMAGES_THUMBNAIL_URL + global.getSalonListing().get(global.getItem_selected()).get(GlobalConstants.SALON_PROFILE_IMAGE);

        Log.v("salon image", salon_image_url);

        Picasso.with(SalonActivity.this) //Context
                .load(salon_image_url) //URL/FILE
                .into(salonPic);

        Drawable dr = new BitmapDrawable(utils.getBlurBitmap_fromURL(salon_image_url, SalonActivity.this));
        salonbg.setBackgroundDrawable(dr);


        treatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SalonActivity.this, TreatmentActivity.class));
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SalonActivity.this, AboutActivity.class));
            }
        });

        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SalonActivity.this, ReviewsActivity.class));
            }
        });

        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SalonActivity.this, BookStep1.class));
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });


        tv_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SalonActivity.this, ProductActivity.class));
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

            }
        });


        mAdapter = new DealsAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Deal deal = movieList.get(position);
                Toast.makeText(getApplicationContext(), deal.getDeal() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void prepareMovieData() {

        if (global.getOfferTreatmentListing() == null) {
            Deal deal = new Deal(" ", "$ " + " ");
            movieList.add(deal);
        } else {
            for (HashMap<String, String> hashMap : global.getOfferTreatmentListing()) {
                Deal deal = new Deal(hashMap.get(GlobalConstants.TITLE), "$ " + hashMap.get(GlobalConstants.PRICE));
                movieList.add(deal);
            }
        }

        mAdapter.notifyDataSetChanged();
    }

    public void drawChart(int count, int[] height) {

        for (int k = 1; k <= count; k++) {

            View view = new View(this);

            view.setBackgroundColor(getResources().getColor(R.color.colorblue));
            //view.setBackgroundResource(R.drawable.bar);
            view.setLayoutParams(new LinearLayout.LayoutParams(25, height[k - 1]));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.setMargins(100, 100, 0, 0); // substitute parameters for left, top, right, bottom
       //     params.gravity = Gravity.CENTER_HORIZONTAL;
            view.setLayoutParams(params);
            linearChart.addView(view);
        }
    }

    public void textChart(int count) {

        for (int k = 1; k <= count; k++) {

            TextView view = new TextView(this);
            view.setLayoutParams(new LinearLayout.LayoutParams(25, 60));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.setMargins(100, 0, 0, 0); // substitute parameters for left, // top, right, bottom
            view.setLayoutParams(params);
            view.setText(String.valueOf(k));
            chartText.addView(view);
        }
    }

    public void showSalonCards() {

        for (int i = 0; i < global.getAl_salon_card().size(); i++) {

            if (global.getAl_salon_card().size() > 0) {

                if (global.getAl_salon_card().get(i).get(GlobalConstants.SALON_CARD_TYPE).contains("paypal")) {
                    iv_paypal.setVisibility(View.VISIBLE);
                }
                if (global.getAl_salon_card().get(i).get(GlobalConstants.SALON_CARD_TYPE).contains("amex")) {
                    iv_american.setVisibility(View.VISIBLE);
                }
                if (global.getAl_salon_card().get(i).get(GlobalConstants.SALON_CARD_TYPE).contains("mastercard")) {
                    iv_master.setVisibility(View.VISIBLE);
                }
                if (global.getAl_salon_card().get(i).get(GlobalConstants.SALON_CARD_TYPE).contains("visa")) {
                    iv_visa.setVisibility(View.VISIBLE);
                }
//                if (global.getAl_salon_card().get(i).get(GlobalConstants.SALON_CARD_TYPE).contains("paypal")) {
//                    iv_apple_pay.setVisibility(View.VISIBLE);
//                }

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private SalonActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final SalonActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    public class AsyncTaskRunner1 extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;
        private String resp;

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(SalonActivity.this, "Loading Salon...","Wait!");
        }

        @Override
        protected String doInBackground(String... params) {

            Log.v("selected_salon", global.getSelectedSalonID());

            ws.SalonDetailsWebService(SalonActivity.this, global.getSelectedSalonID());
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();

            salonName.setText(global.getSalonDetailListing().get(0).get(GlobalConstants.SALON_NAME));
            address.setText(global.getSalonDetailListing().get(0).get(GlobalConstants.SALON_ADDRESS).trim());


            ratingBarWhite.setScore(Float.parseFloat(global.getSalonDetailListing().get(0).get(GlobalConstants.OVERALL_RATING)));
            ratingBarBlue.setScore(Float.parseFloat(global.getSalonDetailListing().get(0).get(GlobalConstants.OVERALL_RATING)));

            prepareMovieData();

            rating.setText(global.getSalonDetailListing().get(0).get(GlobalConstants.OVERALL_RATING));

            setTradinghours(mon, "MON", global.getSalonDetailListing().get(0).get(GlobalConstants.MON_FROM), global.getSalonDetailListing().get(0).get(GlobalConstants.MON_TO));
            setTradinghours(tue, "TUE", global.getSalonDetailListing().get(0).get(GlobalConstants.TUE_FROM), global.getSalonDetailListing().get(0).get(GlobalConstants.TUE_TO));
            setTradinghours(wed, "WED", global.getSalonDetailListing().get(0).get(GlobalConstants.WED_FROM), global.getSalonDetailListing().get(0).get(GlobalConstants.WED_TO));
            setTradinghours(thu, "THU", global.getSalonDetailListing().get(0).get(GlobalConstants.THU_FROM), global.getSalonDetailListing().get(0).get(GlobalConstants.THU_TO));
            setTradinghours(fri, "FRI", global.getSalonDetailListing().get(0).get(GlobalConstants.FRI_FROM), global.getSalonDetailListing().get(0).get(GlobalConstants.FRI_TO));
            setTradinghours(sat, "SAT", global.getSalonDetailListing().get(0).get(GlobalConstants.SAT_FROM), global.getSalonDetailListing().get(0).get(GlobalConstants.SAT_TO));
            setTradinghours(sun, "SUN", global.getSalonDetailListing().get(0).get(GlobalConstants.SUN_FROM), global.getSalonDetailListing().get(0).get(GlobalConstants.SUN_TO));

            ar_rating[0] = Integer.parseInt(global.getSalonDetailListing().get(0).get(GlobalConstants.RATING1));
            ar_rating[1] = Integer.parseInt(global.getSalonDetailListing().get(0).get(GlobalConstants.RATING2));
            ar_rating[2] = Integer.parseInt(global.getSalonDetailListing().get(0).get(GlobalConstants.RATING3));
            ar_rating[3] = Integer.parseInt(global.getSalonDetailListing().get(0).get(GlobalConstants.RATING4));
            ar_rating[4] = Integer.parseInt(global.getSalonDetailListing().get(0).get(GlobalConstants.RATING5));

            int a[] = ar_rating;
            Arrays.sort(a);

            int max = a[a.length - 1];
            System.out.println(max);

            try {
                for (int i = 0; i < 5; i++)
                {
                    float h = (60 / max) * ar_rating[i];
                    ar_rating[i] = Math.round(h);
                }
            } catch (Exception e) {

            }

            drawChart(5, ar_rating);
            textChart(5);

            contact.setText(global.getSalonDetailListing().get(0).get(GlobalConstants.SALON_CONTACT));

 //           ga.setAdapter(new ImageAdapter(SalonActivity.this));

            lv_salon_images.setAdapter(new SalonImagePopUpAdapter(SalonActivity.this));

            showSalonCards();

            if (global.getIs_salon_fav() == 1) {
                like.setBackgroundResource(R.drawable.heartselected);
            }

            if (global.getOfferTreatmentListing().size() == 0 || global.getOfferTreatmentListing() == null) {
                tv_special_deals.setVisibility(View.GONE);
            }

        }

        public void setTradinghours(TextView tv, String Day, String from, String to) {

            if (from.equals("Closed") || to.equals("Closed"))
                tv.setText(Day + " -   Closed");
            else
                tv.setText(Day + " -   " + from + " - " + to);
        }
    }


    public class MakeSalonFav extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;
        private String resp;

        @Override
        protected void onPreExecute() {



//            progressDialog = ProgressDialog.show(SalonActivity.this, "Loading... ", "Wait!");
        }

        @Override
        protected String doInBackground(String... params) {

            Log.v("selected_salon", global.getSelectedSalonID());

            if (global.getIs_salon_fav() == 0) {
                ws.MakeSalonFav(context);
            } else {
                ws.MakeSalonUnFav(context);
            }

            return resp;
        }

        @Override
        protected void onPostExecute(String result) {

//            if (progressDialog.isShowing()) {
//                progressDialog.cancel();
//            }



        }

    }


}