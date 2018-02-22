package dcube.com.salonseek;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

import utils.CustomRatingBarBlue;
import utils.CustomSalonImages;
import utils.HorizontalListView;
import utils.MyApplication;
import utils.SalonImagePopUpAdapter;
import webservices.GlobalConstants;

public class AboutActivity extends Activity {

    MyApplication global;
    TextView rating;
    CustomRatingBarBlue ratingBarBlue;
    TextView about;

    int[] ar_rating = new int[5];

    LinearLayout linearChart,chartText;

    CustomSalonImages adapter;

    ViewPager mViewPager;

    HorizontalListView lv_salon_images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        global = (MyApplication) getApplicationContext();

        rating = (TextView) findViewById(R.id.rating_text);
        ratingBarBlue = (CustomRatingBarBlue) findViewById(R.id.ratingbarblue);
        about = (TextView) findViewById(R.id.about);

        linearChart = (LinearLayout) findViewById(R.id.linearChart);
        chartText = (LinearLayout) findViewById(R.id.ratingtext);

        lv_salon_images = (HorizontalListView) findViewById(R.id.lv_salon_images);

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
            for (int i = 0; i < 5; i++) {
                float h = (60 / max) * ar_rating[i];
                ar_rating[i] = Math.round(h);
            }
        } catch (Exception e) {

        }

        drawChart(5, ar_rating);
        textChart(5);

        ratingBarBlue.setScore(Float.parseFloat(global.getSalonDetailListing().get(0).get(GlobalConstants.OVERALL_RATING)));
        rating.setText(global.getSalonDetailListing().get(0).get(GlobalConstants.OVERALL_RATING));
        about.setText(global.getSalonDetailListing().get(0).get(GlobalConstants.SALON_ABOUT));

//        Gallery ga = (Gallery)findViewById(R.id.Gallery01);
//
//        ga.setAdapter(new ImageAdapter(this));

//        adapter = new CustomSalonImages(this);

//        mViewPager = (ViewPager) findViewById(R.id.pager);
//        mViewPager.setAdapter(adapter);

        lv_salon_images.setAdapter(new SalonImagePopUpAdapter(this));
    }

    public void drawChart(int count, int[] height) {

        for (int k = 1; k <= count; k++) {

            View view = new View(this);

            view.setBackgroundColor(getResources().getColor(R.color.colorblue));
            //view.setBackgroundResource(R.drawable.bar);
            view.setLayoutParams(new LinearLayout.LayoutParams(25, height[k - 1]));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.setMargins(100, 20, 0, 0); // substitute parameters for left, // top, right, bottom
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

}
