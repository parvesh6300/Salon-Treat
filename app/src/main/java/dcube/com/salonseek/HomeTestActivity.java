package dcube.com.salonseek;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import utils.MyApplication;
import utils.PrefUtils;
import utils.User;
import utils.Utilities;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

public class HomeTestActivity extends FragmentActivity {

    DrawerLayout drawer;
    ImageView profileview;
    LinearLayout bottombar;
    RelativeLayout fragmentholder;
    ImageView appointment;
    ImageView search;

    LinearLayout pro_bg;
    RelativeLayout logout;

    ImageView profilepic;
    TextView username;
    TextView email;

    Context myContext;
    User user;

    MyApplication global;
    WebServicesHandler ws;

    String login_pref = "Login_pref";
    String is_logged_in_pref = "Logged_in_pref";

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Utilities utils;

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_test);

        user = PrefUtils.getCurrentUser(HomeTestActivity.this);
        utils = new Utilities();

        myContext = this;

        global = (MyApplication) getApplicationContext();

        String projectToken = "ca750f62cdae3cd937e9bb337ad112a4"; // e.g.: "1ef7e30d2a58d27f4b90c42e31d6d7ad"
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, projectToken);


        try {
            JSONObject props = new JSONObject();
          //  props.put("Gender", "Female");
            props.put("Logged in", true);
            mixpanel.track("Logged - in", props);

        } catch (JSONException e) {
            Log.e("MYAPP", "Unable to add properties to JSONObject", e);
        }


        global.setPrev_visit_filter(false);
        global.setFavorite_filter(false);
        global.setFilter(false);

        profileview = (ImageView) findViewById(R.id.profile);
        bottombar = (LinearLayout) findViewById(R.id.bottom);

        logout = (RelativeLayout) findViewById(R.id.logout);

        appointment = (ImageView) findViewById(R.id.appointment);
        search = (ImageView) findViewById(R.id.search);

        fragmentholder = (RelativeLayout) findViewById(R.id.fragmentholder);


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentholder, HomeFragment.newInstance(), "rageComicList")
                    .commit();
        }

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appointment.setImageResource(R.drawable.newbookiconselected);
                search.setImageResource(R.drawable.searchicon);
                FragmentManager fragmentManager = getSupportFragmentManager() ;
                fragmentManager.beginTransaction().replace(R.id.fragmentholder, BookingFrag.newInstance(), "rageComicList").commit();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncTaskRunner().execute();

                    }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logOut();

                setSharedPreferences();

                PrefUtils.clearCurrentUser(HomeTestActivity.this);
                Toast.makeText(HomeTestActivity.this, "Logged out", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(HomeTestActivity.this,LoginActivity.class));
                finish();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        final NavigationView rightNavigationView = (NavigationView) findViewById(R.id.nav_right_view);
        assert rightNavigationView != null;

        View headerView = rightNavigationView.inflateHeaderView(R.layout.nav_header_main);

        profilepic = (ImageView) headerView.findViewById(R.id.profilepic);
        username = (TextView) headerView.findViewById(R.id.name);
        email = (TextView) headerView.findViewById(R.id.email);
        pro_bg = (LinearLayout) headerView.findViewById(R.id.pro_bg);

        new GetProfile().execute();

        if(global.isFblogin()) {

            username.setText(user.name);
            email.setText(user.email);

            Picasso.with(myContext).load(user.Image).into(profilepic);
        }


        if (rightNavigationView != null) {

            rightNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    // Handle Right navigation view item clicks here.
                    int id = item.getItemId();

                    if (id == R.id.nav_acc_details) {

                        startActivity(new Intent(HomeTestActivity.this,AccountActivity.class));

                    } else if (id == R.id.nav_share) {

                        startActivity(new Intent(HomeTestActivity.this,ShareUsActivity.class));

                    } else if (id == R.id.nav_settings) {

                        startActivity(new Intent(HomeTestActivity.this,SettingActivity.class));

                    } else if (id == R.id.nav_about) {

                        String url = "http://www.google.com";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);

                    }

                    drawer.closeDrawer(GravityCompat.END);

                    /*Important Line*/
                    return true;
                }
            });
        }

        profileview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
                drawer.bringToFront();
            }
        });


    }

    @Override
    public void onBackPressed() {


        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (drawer.isDrawerOpen(GravityCompat.END))
        {  /*Closes the Appropriate Drawer*/
            drawer.closeDrawer(GravityCompat.END);
            bottombar.bringToFront();

        }
        else {
//            super.onBackPressed();
//            System.exit(0);
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
            {
                super.onBackPressed();
                return;
            }
            else
            {
                Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
            }

            mBackPressed = System.currentTimeMillis();

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_openRight) {
            //drawer.openDrawer(GravityCompat.END);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class GetProfile extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... params) {

            ws.GetProfileService(myContext);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            username.setText(global.getProfileDetails().get(GlobalConstants.FULL_NAME));
            email.setText(global.getProfileDetails().get(GlobalConstants.LOGIN_EMAIL));

            String user_img = global.getProfileDetails().get(GlobalConstants.USER_IMG);

            Drawable dr = new BitmapDrawable(utils.getBlurBitmap_fromURL(GlobalConstants.SALON_IMAGES_URL+user_img, HomeTestActivity.this));
            pro_bg.setBackgroundDrawable(dr);

            Picasso.with(myContext).
                    load(GlobalConstants.SALON_IMAGES_URL+user_img).
                    into(profilepic);
        }
    }

    public void setSharedPreferences()
    {
        pref = getSharedPreferences(login_pref,MODE_PRIVATE);

        editor = pref.edit();

        editor.putBoolean(is_logged_in_pref,false);

        editor.apply();
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;
        private String resp;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(HomeTestActivity.this, "Getting Salons around you", "Wait!");
        }

        @Override
        protected String doInBackground(String... params) {

            ws.SalonListingWebService(HomeTestActivity.this);

            return resp;

        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation

            if (progressDialog.isShowing())
            {
                progressDialog.cancel();
            }

            appointment.setImageResource(R.drawable.newbookicon);
            search.setImageResource(R.drawable.searchiconselected);
            FragmentManager fragmentManager = getSupportFragmentManager() ;
            fragmentManager.beginTransaction().replace(R.id.fragmentholder, HomeFragment.newInstance(), "rageComicList").commit();

        }

    }


    public void changeIcons(Activity activity)
    {
        appointment = (ImageView) activity.findViewById(R.id.appointment);
        search = (ImageView) activity.findViewById(R.id.search);

        appointment.setImageResource(R.drawable.newbookicon);
        search.setImageResource(R.drawable.searchiconselected);
    }




}