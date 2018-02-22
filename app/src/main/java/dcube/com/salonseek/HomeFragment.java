package dcube.com.salonseek;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import utils.CustomAdapter;
import utils.CustomDate;
import utils.CustomRatingBar;
import utils.HorizontalListView;
import utils.MyApplication;
import utils.PostVisitAdapter;
import utils.SearchItemsAdapter;
import utils.date.DateAdapter;
import utils.date.TimesAdapter;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

public class HomeFragment extends Fragment {

    public static final int TIME_PICKER_INTERVAL = 15;
    private static HomeFragment mInstance = null;
    public AsyncTaskRunner runner = new AsyncTaskRunner();
    public ArrayList<String> al_search;
    ListView lv;
    RelativeLayout search;
    RelativeLayout filter;
    SlidingDrawer searchslider;
    SlidingDrawer filterslider;
    TextView seektext,tv_label_km;
    ImageView bottomslide,toplogo;
    ImageView all;
    ImageView popular;

    HorizontalListView lvTest;
    HorizontalListView lvTestdate;
    HorizontalListView lv_search_items;

    Context ctx;
    TextView viewcal;
    Button showSalon;
    SeekBar seekBar;
    CustomAdapter adapter;
    LinearLayout bottom;

    RelativeLayout location;
    RelativeLayout treatment;
    RelativeLayout salon;

    TextView hightolow;
    TextView lowtohigh;
    TextView tv_treatment;
    TextView tv_salon_name;
    TextView cancel;

    View view_fade;

    boolean istimeselected,isdateselected;

    boolean isSearchSelected;

    CustomRatingBar ratingBar;
    //BookNowDateListAdapter dateAdapter;
    ViewPager pager;
    WebServicesHandler ws;
    MyApplication global;
    DateAdapter dateAdapter;
    TimesAdapter timeAdapter;
    SearchItemsAdapter searchItemsAdapter;
    RadioGroup radio_choice;
    RadioButton radio_morning, radio_midday, radio_evening;
    Animation slideUp, slidedown;
    ArrayList<String> dating = new ArrayList<>();
    TextView tv_current_location;
    private ArrayList<CustomDate> dates;
    private ArrayList<String> time;

    // ******************************** Filter Variables **********************************************

    RangeSeekBar<Integer> custom_seek_dis;
    RangeSeekBar<Integer> custom_seek_price;
    ListView  lv_post_visit ;
    PostVisitAdapter postVisitAdapter;

    ImageView iv_visits,iv_price,iv_distance,iv_rating,iv_popular,iv_available;

    ArrayList<String> al_salon_name= new ArrayList<>();
    ArrayList<String> al_salon_distance = new ArrayList<>();

    TextView tv_limit_km,tv_distance_label,tv_start_km;
    TextView tv_start_price,tv_limit_price;

    TextView tv_distance,tv_price,tv_visits,tv_available,tv_popular,tv_rating;

    LinearLayout lin_rating,lin_popular,lin_available,lin_post_visits,lin_price,lin_distance;
    RelativeLayout rel_post_visit,rel_dis_option,rel_price_option;

    TextView tv_not_visit_label,tv_reset,tv_apply;

    boolean isPriceSelected = true;
    boolean isDistanceSelected = true;
    boolean isPostVisitSelected = true;
    boolean isRatingSelected = true;
    boolean isPopularSelected = true;
    boolean isAvailableSelected = true;


    // ******************************** Filter Variables End here  **********************************************

    public HomeFragment() {

        // Required empty public constructor
        // ctx = getActivity();
    }

    public static HomeFragment newInstance() {

        mInstance = new HomeFragment();
        return mInstance;
    }

    public static HomeFragment getInstance() {

        if (mInstance == null) {
            mInstance = new HomeFragment();
        }

        return mInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        slideUp = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_up);
        slidedown = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_up);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        //ctx = getActivity();
        final Activity activity = getActivity();

        lvTest = (HorizontalListView) view.findViewById(R.id.lvItems);

        timeAdapter = new TimesAdapter(getActivity(), R.layout.layout_time_item, getTime());
        lvTest.setAdapter(timeAdapter);

        lvTestdate = (HorizontalListView) view.findViewById(R.id.lvItemsdate);

        dateAdapter = new DateAdapter(getActivity(), R.layout.layout_date_item, getDates());
        lvTestdate.setAdapter(dateAdapter);

//        yourListView.setSelection(0);
//        yourListView.getSelectedView().setSelected(true);

        timeAdapter.setSelectedIndex(0);
        timeAdapter.notifyDataSetChanged();

        dateAdapter.setSelectedIndex(0);
        dateAdapter.notifyDataSetChanged();

        lvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                timeAdapter.setSelectedIndex(position);
                timeAdapter.notifyDataSetChanged();

                istimeselected = true;

                String time = lvTest.getItemAtPosition(position).toString();

                //     String timearray=time.substring(0,5);

                //      global.setTime(timearray.trim());

//              global.setTime(time);
//
//              Log.e("Time","Time "+global.getTime().trim());

            }
        });

        lvTestdate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                isdateselected = true;

                if (position == 0) {

                    dateAdapter.setSelectedIndex(position);
                    dateAdapter.notifyDataSetChanged();

                    Calendar calendar = Calendar.getInstance();

                    Log.e("gettime", "gettime " + calendar.getTime());

                    timeAdapter = new TimesAdapter(getActivity(), R.layout.layout_time_item, getTime());
                    timeAdapter.setSelectedIndex(0);
                    lvTest.setAdapter(timeAdapter);
                    timeAdapter.notifyDataSetChanged();

                } else {

                    dateAdapter.setSelectedIndex(position);
                    dateAdapter.notifyDataSetChanged();

                    Calendar calendar = Calendar.getInstance();
                    Log.e("gettime", "gettime " + calendar.getTime());
                    timeAdapter = new TimesAdapter(getActivity(), R.layout.layout_time_item, getMorningTime());
                    timeAdapter.setSelectedIndex(0);
                    lvTest.setAdapter(timeAdapter);
                    timeAdapter.notifyDataSetChanged();

                }
            }
        });

        searchslider = (SlidingDrawer) view.findViewById(R.id.searchDrawer);
        filterslider = (SlidingDrawer) view.findViewById(R.id.filterdrawer);
        showSalon = (Button) view.findViewById(R.id.showsalon);
        lv = (ListView) view.findViewById(R.id.list);
        bottomslide = (ImageView) view.findViewById(R.id.bottomslide);
        toplogo = (ImageView) view.findViewById(R.id.toplogo);
        viewcal = (TextView) view.findViewById(R.id.viewcal);
        cancel = (TextView) view.findViewById(R.id.cancel);
        location = (RelativeLayout) view.findViewById(R.id.location_layout);
        treatment = (RelativeLayout) view.findViewById(R.id.treatmentselect);
        salon = (RelativeLayout) view.findViewById(R.id.salon);

       view_fade = (View) view.findViewById(R.id.view_fade);


     //   all = (ImageView) view.findViewById(R.id.all);
    //    popular = (ImageView) view.findViewById(R.id.popular);
        bottom = (LinearLayout) getActivity().findViewById(R.id.bottom);
        seekBar = (SeekBar) view.findViewById(R.id.seekbar);

        seektext = (TextView) view.findViewById(R.id.seekvalue);
  //      hightolow = (TextView) view.findViewById(R.id.hightolow);
  //      lowtohigh = (TextView) view.findViewById(R.id.lowtohigh);
        ratingBar = (CustomRatingBar) view.findViewById(R.id.ratingbar);
        tv_treatment = (TextView) view.findViewById(R.id.tv_treatment);
        tv_salon_name = (TextView) view.findViewById(R.id.tv_salon_name);
        tv_current_location = (TextView) view.findViewById(R.id.tv_current_location);


        radio_choice = (RadioGroup) view.findViewById(R.id.radio_choice);
        radio_morning = (RadioButton) view.findViewById(R.id.radio_morning);
        radio_midday = (RadioButton) view.findViewById(R.id.radio_midday);
        radio_evening = (RadioButton) view.findViewById(R.id.radio_evening);

//        al_search = new ArrayList<>();
//        searchItemsAdapter = new SearchItemsAdapter(getActivity(), global.al_search_items);

        lv.bringToFront();

        try {

            global.al_treatment_name.clear();

            radio_evening.setChecked(false);
            radio_midday.setChecked(false);
            radio_morning.setChecked(false);

        } catch (Exception e) {

        }

        View addNew = inflater.inflate(R.layout.searchandfilter, lv, false);
        lv.addHeaderView(addNew, null, false);

        global = (MyApplication) getActivity().getApplicationContext();
        global.setFilter(false);


        if (global.isRun()) {
            runner.execute();
        } else {
            adapter = new CustomAdapter(getActivity());
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }


        tv_current_location.setText(global.getPlace_name());

        search = (RelativeLayout) addNew.findViewById(R.id.searchbar);
        filter = (RelativeLayout) addNew.findViewById(R.id.filterbar);
        lv_search_items = (HorizontalListView) addNew.findViewById(R.id.lv_search_items);


        view_fade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterslider.animateClose();
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewCompat.setElevation(filterslider,5);

                bottom.setVisibility(View.GONE);
                filterslider.animateOpen();
                filterslider.bringToFront();
                filterslider.setClickable(true);
                lv.setAlpha(0.3f);
                toplogo.setAlpha(0.3f);

                ViewCompat.setElevation(view_fade,5);

                view_fade.setVisibility(View.VISIBLE);

                new PreviousVisitAsync().execute();

//                Intent i = new Intent(getActivity(), FilterScreen.class);
//                startActivity(i);
//                getActivity().overridePendingTransition(R.anim.push_down_in, R.anim.push_down_in);

            }
        });

        filterslider.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {

                bottom.setVisibility(View.VISIBLE);
                global.setFilter(true);

                lv.setAlpha(1.0f);
                toplogo.setAlpha(1.0f);

                ViewCompat.setElevation(filterslider,2);

                view_fade.setVisibility(View.GONE);

            }
        });


        bottomslide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterslider.animateClose();
                filterslider.setClickable(false);
                filterslider.setAlpha(1.0f);
                ViewCompat.setElevation(filterslider,5);
                ViewCompat.setElevation(lv,3);

            }
        });


        //*********************************    Filter Methods    ******************************************

        lv_post_visit = (ListView) view.findViewById(R.id.lv_post_visit);

        lin_rating = (LinearLayout) view.findViewById(R.id.lin_rating);
        lin_popular = (LinearLayout) view.findViewById(R.id.lin_popular);
        lin_available = (LinearLayout) view.findViewById(R.id.lin_available);
        lin_post_visits = (LinearLayout) view.findViewById(R.id.lin_post_visits);
        lin_price = (LinearLayout) view.findViewById(R.id.lin_price);
        lin_distance = (LinearLayout) view.findViewById(R.id.lin_distance);

        rel_post_visit = (RelativeLayout) view.findViewById(R.id.rel_post_visit);
        rel_dis_option = (RelativeLayout) view.findViewById(R.id.rel_dis_option);
        rel_price_option = (RelativeLayout) view.findViewById(R.id.rel_price_option);

        tv_not_visit_label = (TextView) view.findViewById(R.id.tv_not_visit_label);
        tv_distance_label = (TextView) view.findViewById(R.id.tv_distance_label);
        tv_limit_km = (TextView) view.findViewById(R.id.tv_limit_km);
        tv_start_km = (TextView) view.findViewById(R.id.tv_start_km);
        tv_label_km = (TextView) view.findViewById(R.id.tv_label_km);
        tv_start_price = (TextView) view.findViewById(R.id.tv_start_price);
        tv_limit_price = (TextView) view.findViewById(R.id.tv_limit_price);

        tv_distance = (TextView) view.findViewById(R.id.tv_distance);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_visits = (TextView) view.findViewById(R.id.tv_visits);
        tv_available = (TextView) view.findViewById(R.id.tv_available);
        tv_popular = (TextView) view.findViewById(R.id.tv_popular);
        tv_rating = (TextView) view.findViewById(R.id.tv_rating);

        tv_apply = (TextView) view.findViewById(R.id.tv_apply);
        tv_reset = (TextView) view.findViewById(R.id.tv_reset);

        iv_visits = (ImageView) view.findViewById(R.id.iv_visits);
        iv_price = (ImageView) view.findViewById(R.id.iv_price);
        iv_distance = (ImageView) view.findViewById(R.id.iv_distance);
        iv_rating = (ImageView) view.findViewById(R.id.iv_rating);
        iv_popular = (ImageView) view.findViewById(R.id.iv_popular);
        iv_available = (ImageView) view.findViewById(R.id.iv_available);

        lv_post_visit.setVisibility(View.GONE);
        rel_post_visit.setVisibility(View.GONE);
        rel_price_option.setVisibility(View.GONE);
        rel_dis_option.setVisibility(View.GONE);

        al_salon_distance = new ArrayList<>();
        al_salon_name = new ArrayList<>();

        custom_seek_dis = (RangeSeekBar<Integer>) view.findViewById(R.id.custom_seek_dis);
        custom_seek_price = (RangeSeekBar<Integer>) view.findViewById(R.id.custom_seek_price);

        custom_seek_dis.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                tv_start_km.setText(String.valueOf(minValue+"km - "));
                tv_limit_km.setText(String.valueOf(maxValue+"km +"));
                global.setDistance_from(String.valueOf(minValue));
                global.setDistance_to(String.valueOf(maxValue));

            }
        });

        custom_seek_price.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {

                tv_start_price.setText(String.valueOf("$"+minValue+" -"));
                tv_limit_price.setText(String.valueOf("$"+maxValue+"+"));
                global.setPrice_from(String.valueOf(minValue));
                global.setPrice_to(String.valueOf(maxValue));
            }
        });

        custom_seek_dis.setNotifyWhileDragging(true);
        custom_seek_price.setNotifyWhileDragging(true);

        tv_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                global.setFilter(true);
                filterslider.animateClose();
                new AsyncTaskRunner().execute();

            }
        });

        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                global.setFilter(false);
                resetFilterValues();
                filterslider.animateClose();
                new AsyncTaskRunner().execute();
            }
        });

        lin_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isRatingSelected)
                {
                    iv_rating.setImageResource(R.drawable.ratingblue);
                    tv_rating.setTextColor(Color.parseColor("#438bc5"));  // Color blue
                    isRatingSelected = false;
                    global.setFilterRating("0");

                }
                else
                {
                    iv_rating.setImageResource(R.drawable.rating_black);
                    tv_rating.setTextColor(Color.parseColor("#484848"));  // Color text black
                    isRatingSelected = true;
                    global.setFilterRating("");
                }
            }
        });

        lin_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPopularSelected)
                {
                    global.setSortBy("popular");
                    iv_popular.setImageResource(R.drawable.popularblue);
                    tv_popular.setTextColor(Color.parseColor("#438bc5"));  // Color blue
                    isPopularSelected = false;
                }
                else
                {
                    iv_popular.setImageResource(R.drawable.popular_black);
                    tv_popular.setTextColor(Color.parseColor("#484848"));  // Color text black
                    isPopularSelected = true;
                    global.setSortBy("");
                }

            }
        });


        lin_available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isAvailableSelected)
                {
                    iv_available.setImageResource(R.drawable.available_blue);
                    tv_available.setTextColor(Color.parseColor("#438bc5"));  // Color blue
                    isAvailableSelected = false;
                    global.setAvailable("available");
                }
                else
                {
                    iv_available.setImageResource(R.drawable.available_black);
                    tv_available.setTextColor(Color.parseColor("#484848"));  // Color text black
                    isAvailableSelected = true;
                    global.setAvailable("");
                }

            }
        });



        lin_post_visits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPostVisitSelected)
                {
                    iv_visits.setImageResource(R.drawable.post_visit_blue);
                    tv_visits.setTextColor(Color.parseColor("#438bc5"));  // Color blue
                    rel_post_visit.setVisibility(View.VISIBLE);
                    rel_dis_option.setVisibility(View.GONE);
                    rel_price_option.setVisibility(View.GONE);

                    isPostVisitSelected = false;

                    if (global.getAl_prev_visit().size() == 0) // al_salon_name
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
                    iv_visits.setImageResource(R.drawable.post_visit_black);
                    tv_visits.setTextColor(Color.parseColor("#484848"));  // Color text black
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
                    iv_price.setImageResource(R.drawable.price_blue);
                    tv_price.setTextColor(Color.parseColor("#438bc5"));  // Color blue

                    rel_price_option.setVisibility(View.VISIBLE);
                    rel_dis_option.setVisibility(View.GONE);
                    rel_post_visit.setVisibility(View.GONE);

                    isPriceSelected = false;

                    custom_seek_price.setSelectedMinValue(1);
                    custom_seek_price.setSelectedMaxValue(1000);

                    tv_start_price.setText(String.valueOf("$"+1+" -"));
                    tv_limit_price.setText(String.valueOf("$"+1000+"+"));
                }
                else
                {
                    iv_price.setImageResource(R.drawable.price_black);
                    tv_price.setTextColor(Color.parseColor("#484848"));  // Color text black
                    rel_price_option.setVisibility(View.GONE);
                    isPriceSelected = true;
                    global.setPrice_from("");
                    global.setPrice_to("");
                }


            }
        });


        lin_distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isDistanceSelected)
                {
                    custom_seek_dis.setSelectedMinValue(1);
                    custom_seek_dis.setSelectedMaxValue(60);
                    tv_start_km.setText(String.valueOf(1+"km - "));
                    tv_limit_km.setText(String.valueOf(60+"km +"));

                    iv_distance.setImageResource(R.drawable.distance_blue);
                    tv_distance.setTextColor(Color.parseColor("#438bc5"));  // Color blue
                    rel_dis_option.setVisibility(View.VISIBLE);
                    rel_price_option.setVisibility(View.GONE);
                    rel_post_visit.setVisibility(View.GONE);
                    isDistanceSelected = false;
                }
                else
                {
                    iv_distance.setImageResource(R.drawable.distance_black);
                    tv_distance.setTextColor(Color.parseColor("#484848"));  // Color text black
                    rel_dis_option.setVisibility(View.GONE);
                    isDistanceSelected = true;
                    global.setDistance_from("");
                    global.setDistance_to("");
                    custom_seek_dis.setSelectedMinValue(1);
                    custom_seek_dis.setSelectedMaxValue(60);
                }

            }
        });



        // *******************************  Filter end here *********************************



        // ******************************* Search Slider methods  *********************************


        radio_choice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (radio_morning.isChecked()) {

                    if (al_search.contains("Midday"))
                    {
                        al_search.remove("Midday");
                    }
                    else if (al_search.contains("Evening"))
                    {
                        al_search.remove("Evening");
                    }
                    else
                    {
                        al_search.add("Morning");
                    }

                    timeAdapter = new TimesAdapter(getActivity(), R.layout.layout_time_item, getTimeSchedule(9, 12));
                    lvTest.setAdapter(timeAdapter);
                }

                if (radio_midday.isChecked()) {

                    if (al_search.contains("Morning"))
                    {
                        al_search.remove("Morning");
                    }
                    else if (al_search.contains("Evening"))
                    {
                        al_search.remove("Evening");
                    }
                    else
                    {
                        al_search.add("Midday");
                    }

                    timeAdapter = new TimesAdapter(getActivity(), R.layout.layout_time_item, getTimeSchedule(12, 18));
                    lvTest.setAdapter(timeAdapter);
                }

                if (radio_evening.isChecked()) {

                    if (al_search.contains("Morning"))
                    {
                        al_search.remove("Morning");
                    }
                    else if (al_search.contains("Midday"))
                    {
                        al_search.remove("Midday");
                    }
                    else
                    {
                        al_search.add("Evening");
                    }

                    timeAdapter = new TimesAdapter(getActivity(), R.layout.layout_time_item, getTimeSchedule(18, 22));
                    lvTest.setAdapter(timeAdapter);
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                global.setDate("");
                global.setTime("");
                global.setSrc_date_time("");
                global.setSrc_salon_id("");
                global.setPlace_name("");
                tv_current_location.setText("");

                isdateselected =false;
                istimeselected = false;
                radio_evening.setChecked(false);
                radio_midday.setChecked(false);
                radio_morning.setChecked(false);

                global.al_search_items.clear();

                al_search = new ArrayList<String>();

//                if (al_search.size() != 0) {
//                    al_search.clear();
//                }

                tv_salon_name.setText("All Salons");
                tv_treatment.setText("");


//                if (global.al_search_salon.size() != 0) {
//                    global.al_search_salon.clear();
//                    tv_salon_name.setText("All Salons");
//                }

                if (global.al_treatment_id.size() != 0) {
                    global.al_treatment_id.clear();

                }


                if (global.al_search_salon_id.size() != 0)
                {
                    global.al_search_salon_id.clear();
                }

                timeAdapter.setSelectedIndex(0);
                timeAdapter.notifyDataSetChanged();

                dateAdapter.setSelectedIndex(0);
                dateAdapter.notifyDataSetChanged();

                searchslider.animateOpen();
                searchslider.setClickable(true);
                bottom.setVisibility(View.GONE);

                //ViewCompat.setElevation(searchslider,3);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        ws.AllTreatmentService(getActivity());

                    }
                });

                thread.start();
            }
        });



        showSalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchslider.animateClose();
                searchslider.setClickable(false);
                bottom.setVisibility(View.VISIBLE);
                lv_search_items.setVisibility(View.VISIBLE);

                // global.setSrc_date_time(global.getDate() + " " + global.getTime());

//                adapter = new CustomAdapter(getActivity());
//                lv.setAdapter(adapter);
//                adapter.notifyDataSetChanged();

                if (global.al_treatment_name.size() > 0)
                {
                    for (int i = 0; i < global.al_treatment_name.size(); i++)
                    {
                        al_search.add(global.al_treatment_name.get(i));
                    }
                }

                if (global.al_search_salon.size() > 0) {

                    for (int i = 0; i < global.al_search_salon.size(); i++)
                    {
//                        al_search.add(global.al_search_salon.get(i));
                        global.al_search_items.put(GlobalConstants.Search_SALON_ID,global.getSrc_salon_id());
                    }
                }

                if (isdateselected && istimeselected)
                {
                    global.setSrc_date_time(global.getDate() + " " + global.getTime());

//                    al_search.add(global.getDate());
//                    al_search.add(global.getTime());

                    global.al_search_items.put(GlobalConstants.Search_DATE_TIME, global.getSrc_date_time());

                }

                try{

                 //   global.al_search_items.put(GlobalConstants.Search_DATE_TIME,global.getSrc_date_time());

                    if ( !global.getPlace_name().matches("") || global.getPlace_name().equalsIgnoreCase(null))
                    {
                        al_search.add(global.getPlace_name());

                        global.al_search_items.put(GlobalConstants.Search_Place_Name,global.getPlace_name());

                    }
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }

                global.setAl_search_items(global.al_search_items);

                searchItemsAdapter = new SearchItemsAdapter(getActivity(),HomeFragment.this);
                lv_search_items.setAdapter(searchItemsAdapter);
                searchItemsAdapter.notifyDataSetChanged();

                isSearchSelected = true;

                new AsyncTaskRunner().execute();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchslider.animateClose();
                searchslider.setClickable(false);
                bottom.setVisibility(View.VISIBLE);
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().startActivity(new Intent(getActivity(), LocationSelectActivity.class));

            }
        });

        treatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                global.al_treatment_name.clear();
                global.al_treatment_id.clear();
                getActivity().startActivity(new Intent(getActivity(), TreatmentSelectActivity.class));
            }
        });

        salon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().startActivity(new Intent(getActivity(), SalonSelectActivity.class));

            }
        });

        viewcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), CalendarActivity.class));
            }
        });




        return view;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private ArrayList<CustomDate> getDates() {

        dates = new ArrayList<CustomDate>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        for (int index = 1; index < 30; index++) {

            CustomDate date = new CustomDate();
            date.setDate("" + calendar.get(Calendar.DATE));
            date.setDay(getDay(calendar.get(Calendar.DAY_OF_WEEK)));
            date.setYear("" + calendar.get(Calendar.YEAR));
            date.setMonth("" + getMonth(calendar.get(Calendar.MONTH)));

            date.setFormattedDate(calendar.get(Calendar.YEAR) + "-"
                    + (calendar.get(Calendar.MONTH) + 1) + "-"
                    + calendar.get(Calendar.DATE));

            dates.add(date);

            //  Log.e("date","date "+calendar.get(Calendar.DATE));
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }


    public ArrayList<String> getTime() {

        time = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int hour = cal.get(cal.HOUR_OF_DAY) + 1;
        int min = cal.get(Calendar.MINUTE);
        String add;

        for (int j = hour; j < 24; j++) {
            if (hour > 12)
                add = " pm";
            else
                add = " am";

            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {

                time.add(String.valueOf(j) + ":" + String.format("%02d", i));

            }
        }

        return time;
    }


    public ArrayList<String> getMorningTime() {
        time = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int hour = 9;
        String add;

        for (int j = hour; j < 24; j++) {
            if (hour >= 12) {
                add = " pm";
            } else {
                add = " am";
            }

            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {

                if (j < 10) {

                    time.add("0" + String.valueOf(j) + ":" + String.format("%02d", i));
                } else {
                    time.add(String.valueOf(j) + ":" + String.format("%02d", i));
                }
            }
        }

        return time;
    }


    public ArrayList<String> getTimeSchedule(int start_hour, int end_hour) {
        time = new ArrayList<>();

        for (int j = start_hour; j < end_hour; j++) {
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                time.add(String.valueOf(j) + ":" + String.format("%02d", i));
            }
        }

        return time;
    }

    private String getDay(int index) {
        switch (index) {
            case Calendar.SUNDAY:
                return "SUN";
            case Calendar.MONDAY:
                return "MON";
            case Calendar.TUESDAY:
                return "TUE";
            case Calendar.WEDNESDAY:
                return "WED";
            case Calendar.THURSDAY:
                return "THU";
            case Calendar.FRIDAY:
                return "FRI";
            case Calendar.SATURDAY:
                return "SAT";
        }
        return "";
    }

    private String getMonth(int index) {
        switch (index) {
            case Calendar.JANUARY:
                return "JANUARY";
            case Calendar.FEBRUARY:
                return "FEBRUARY";
            case Calendar.MARCH:
                return "MARCH";
            case Calendar.APRIL:
                return "APRIL";
            case Calendar.MAY:
                return "MAY";
            case Calendar.JUNE:
                return "JUNE";
            case Calendar.JULY:
                return "JULY";
            case Calendar.AUGUST:
                return "AUGUST";
            case Calendar.SEPTEMBER:
                return "SEPTEMBER";
            case Calendar.OCTOBER:
                return "OCTOBER";
            case Calendar.NOVEMBER:
                return "NOVEMBER";
            case Calendar.DECEMBER:
                return "DECEMBER";
        }
        return "";
    }

    @Override
    public void onResume() {

        super.onResume();

     //   new AsyncTaskRunner().execute();

        try
        {
            if (global.al_treatment_name.size() > 0) {

                String selected_treatment = TextUtils.join(",", global.al_treatment_name);

                Log.e("Id", "Treatment " + selected_treatment);

                tv_treatment.setText(selected_treatment);
            }

            if (global.al_search_items.size() > 0) {

                tv_salon_name.setText(global.al_search_items.get(GlobalConstants.Search_Salon_Name));

            }

            tv_current_location.setText(global.getPlace_name());
        }
        catch (Exception e) {
            tv_salon_name.setText("All Salons");
        }



    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String>
    {

        ProgressDialog progressDialog;
        private String resp;

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(getActivity(), "Getting Salons around you", "Wait!");
        }

        @Override
        protected String doInBackground(String... params) {

            if (global.isFilter())
            {
                ws.FilterService(getActivity());
            }
            else if (isSearchSelected)
            {
                ws.SearchSlider(getActivity(),false);    // SearchService
            }
            else if (global.isFavorite_filter())
            {
                ws.GetFavoriteSalon(getActivity());
            }
//            else if (global.isPrev_visit_filter())
//            {
//                ws.PreviouslyVisited(getActivity());
//            }
            else
            {
                ws.SalonListingWebService(getActivity());
             //   ws.PreviouslyVisited(getActivity());
            }

            return resp;

        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation

            progressDialog.dismiss();

            lv.bringToFront();

            ViewCompat.setElevation(lv,3);
//            ViewCompat.setElevation(searchslider,2);
//            ViewCompat.setElevation(filterslider,2);


            view_fade.setVisibility(View.GONE);

            adapter = new CustomAdapter(getActivity());
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            postVisitAdapter = new PostVisitAdapter(getActivity(),al_salon_name,al_salon_distance);
            lv_post_visit.setAdapter(postVisitAdapter);

            global.setPrev_visit_filter(false);
            global.setFavorite_filter(false);
            global.setFilter(false);
            isSearchSelected = false;

        }

    }


    public void resetFilterValues()
    {
        global.setFilterRating("");
        iv_rating.setImageResource(R.drawable.rating_black);
        tv_rating.setTextColor(Color.parseColor("#484848"));  // Color text black
        isRatingSelected = true;

        global.setSortBy("");
        iv_popular.setImageResource(R.drawable.popular_black);
        tv_popular.setTextColor(Color.parseColor("#484848"));  // Color text black
        isPopularSelected = true;

        global.setAvailable("");
        iv_available.setImageResource(R.drawable.available_black);
        tv_available.setTextColor(Color.parseColor("#484848"));  // Color text black
        isAvailableSelected = true;


        iv_visits.setImageResource(R.drawable.post_visit_black);
        tv_visits.setTextColor(Color.parseColor("#484848"));  // Color text black
        rel_post_visit.setVisibility(View.GONE);
        isPostVisitSelected = true;

        global.setPrice_from("");
        global.setPrice_to("");
        iv_price.setImageResource(R.drawable.price_black);
        tv_price.setTextColor(Color.parseColor("#484848"));  // Color text black
        rel_price_option.setVisibility(View.GONE);
        isPriceSelected = true;

        global.setDistance_from("");
        global.setDistance_to("");
        iv_distance.setImageResource(R.drawable.distance_black);
        tv_distance.setTextColor(Color.parseColor("#484848"));  // Color text black
        rel_dis_option.setVisibility(View.GONE);
        isDistanceSelected = true;
    }


    private class PreviousVisitAsync extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {

            ws.PreviouslyVisited(getActivity());

            return resp;

        }

        @Override
        protected void onPostExecute(String result) {

            postVisitAdapter = new PostVisitAdapter(getActivity(),al_salon_name,al_salon_distance);
            lv_post_visit.setAdapter(postVisitAdapter);

            global.setPrev_visit_filter(false);
            global.setFavorite_filter(false);
            global.setFilter(false);
            isSearchSelected = false;

        }

    }


    public void setLayout(Activity activity)
    {
        lv_search_items = (HorizontalListView) activity.findViewById(R.id.lv_search_items);
        lv = (ListView) activity.findViewById(R.id.list);
        global = (MyApplication) activity.getApplicationContext();

        adapter = new CustomAdapter(activity);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Log.e("Size",global.al_search_items.size()+"");
        Log.e("Size",global.al_treatment_name.size()+"");

        if ((global.al_search_items.size() == 0) && (global.al_treatment_name.size() == 0) )
        {
            lv_search_items.setVisibility(View.GONE);
        }

    }

}

