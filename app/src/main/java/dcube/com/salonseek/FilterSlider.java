package dcube.com.salonseek;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;

import utils.PostVisitAdapter;

public class FilterSlider extends AppCompatActivity {

    RangeSeekBar<Integer> custom_seek;
    PostVisitAdapter postVisitAdapter;

    ArrayList<String> al_salon_name;
    ArrayList<String> al_salon_distance;

    ListView lv_post_visit;

    LinearLayout lin_rating,lin_popular,lin_available,lin_post_visits,lin_price,lin_distance;
    RelativeLayout rel_post_visit,rel_dis_option,rel_price_option;

    TextView tv_not_visit_label;

    boolean isPriceSelected = true;
    boolean isDistanceSelected = true;
    boolean isPostVisitSelected = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.filterslider);

        lv_post_visit = (ListView) findViewById(R.id.lv_post_visit);

        lin_rating = (LinearLayout) findViewById(R.id.lin_rating);
        lin_popular = (LinearLayout) findViewById(R.id.lin_popular);
        lin_available = (LinearLayout) findViewById(R.id.lin_available);
        lin_post_visits = (LinearLayout) findViewById(R.id.lin_post_visits);
        lin_price = (LinearLayout) findViewById(R.id.lin_price);
        lin_distance = (LinearLayout) findViewById(R.id.lin_distance);

        rel_post_visit = (RelativeLayout) findViewById(R.id.rel_post_visit);
        rel_dis_option = (RelativeLayout) findViewById(R.id.rel_dis_option);
        rel_price_option = (RelativeLayout) findViewById(R.id.rel_price_option);

        tv_not_visit_label = (TextView) findViewById(R.id.tv_not_visit_label);

        lv_post_visit.setVisibility(View.GONE);
        rel_post_visit.setVisibility(View.GONE);
        rel_price_option.setVisibility(View.GONE);
        rel_dis_option.setVisibility(View.GONE);

        al_salon_distance = new ArrayList<>();
        al_salon_name = new ArrayList<>();

        custom_seek = (RangeSeekBar<Integer>)findViewById(R.id.custom_seek_dis);

        custom_seek.setRangeValues(1, 60);
        custom_seek.setSelectedMinValue(1);
        custom_seek.setSelectedMaxValue(60);
        custom_seek.setTextAboveThumbsColor(Color.parseColor("#000000"));


        custom_seek.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                Log.e("Seek","Min "+minValue +" Max "+maxValue);
            }
        });

        custom_seek.setNotifyWhileDragging(true);

        postVisitAdapter = new PostVisitAdapter(this,al_salon_name,al_salon_distance);
        lv_post_visit.setAdapter(postVisitAdapter);


        lin_post_visits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPostVisitSelected)
                {
                    rel_post_visit.setVisibility(View.VISIBLE);
                    isPostVisitSelected = false;

                    if (al_salon_name.size() == 0)
                    {
                        lv_post_visit.setVisibility(View.GONE);
                        tv_not_visit_label.setVisibility(View.VISIBLE);

                    }
                    else
                    {
                        lv_post_visit.setVisibility(View.VISIBLE);
                        tv_not_visit_label.setVisibility(View.GONE);
                    }


                }
                else
                {
                    rel_post_visit.setVisibility(View.GONE);
                    isPostVisitSelected = true;
                }



            }
        });


        lin_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPriceSelected)
                {
                    rel_price_option.setVisibility(View.VISIBLE);
                    isPriceSelected = false;
                }
                else
                {
                    rel_price_option.setVisibility(View.GONE);
                    isPriceSelected = true;
                }

            }
        });


        lin_distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isDistanceSelected)
                {
                    rel_dis_option.setVisibility(View.VISIBLE);
                    isDistanceSelected = false;
                }
                else
                {
                    rel_dis_option.setVisibility(View.GONE);
                    isDistanceSelected = true;
                }

            }
        });


    }


}
