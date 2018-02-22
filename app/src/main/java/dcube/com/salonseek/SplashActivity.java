package dcube.com.salonseek;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import utils.LocationManagerClass;
import utils.MyApplication;
import webservices.GlobalConstants;
import webservices.WebServicesHandler;

public class SplashActivity extends Activity {

    MyApplication global;
    Handler handler;
    private LocationManager lm;
    private String tower;
    WebServicesHandler ws;
    Context context = SplashActivity.this;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    String login_pref = "Login_pref";

    String fst_login = "is_First_Login";
    String is_logged_in_pref = "Logged_in_pref";

    String user_name_pref = "user_name";
    String password_pref = "password";

    Boolean is_first_login= true ;
    Boolean is_logged_in = false;

    String str_username,str_password;

    ProgressBar progressBarFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_splash);

        global = (MyApplication) getApplicationContext();

        progressBarFooter = (ProgressBar) findViewById(R.id.pbFooterLoading);

        getSharePreferences();

        setSharedPreferences();

        String projectToken = "ca750f62cdae3cd937e9bb337ad112a4"; // e.g.: "1ef7e30d2a58d27f4b90c42e31d6d7ad"
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, projectToken);

        try {
            JSONObject props = new JSONObject();
            props.put("Gender", "Female");
            props.put("Logged in", false);
            mixpanel.track("Open - App", props);

        } catch (JSONException e) {
            Log.e("MYAPP", "Unable to add properties to JSONObject", e);
        }

        new Thread (null,CurrentLocation,"").start();

        handler = new Handler() {

            public void handleMessage(Message msg) {

                if (is_first_login)
                {
                    Intent i = new Intent(SplashActivity.this,OnBoardingActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    if (is_logged_in)
                    {
                        new AsyncTaskRunner().execute();

                    }
                    else
                    {
                        Intent i = new Intent(SplashActivity.this,LoginActivity.class);  //LoginActivity  FilterSlider
                        startActivity(i);
                        finish();
                    }

                }

            }
        };
    }

    public Runnable CurrentLocation = new Runnable() {

        @Override
        public void run() {

            if (Looper.myLooper() == null)
            {
                Looper.prepare();
            }

            try {

                if (is_first_login)
                {
                    ws.GetOnBoardScreens(SplashActivity.this);
                }

                LocationManagerClass location =  new LocationManagerClass(SplashActivity.this);

                lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Criteria c = new Criteria();
                c.setAccuracy(Criteria.ACCURACY_COARSE);
                tower = lm.getBestProvider(c,false);

                if(lm != null) {

                    if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        Location  loc = lm.getLastKnownLocation(tower);

                        Log.i("lat in Splash:--",""+ loc.getLatitude());
                        Log.i("lng in Splash:--",""+ loc.getLongitude());

                        global.setLat(""+loc.getLatitude());
                        global.setLang(""+loc.getLongitude());

                        Geocoder geocoder = new Geocoder(SplashActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                        String cityName = addresses.get(0).getAddressLine(0);
                        String stateName = addresses.get(0).getAddressLine(1);
                        String countryName = addresses.get(0).getAddressLine(2);

                        global.setPlace_name(cityName);

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                Thread.sleep(2000);
                String latitude  =  global.getLat();
                String longitute =  global.getLang();

                Log.i("lat in Splash:--",""+latitude);
                Log.i("lng in Splash:--",""+longitute);

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            handler.sendMessage(new Message());
        }
    };

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }



    public void saveImages()
    {

        Log.e("SaveImages","SaveImages");

        for (int i=0 ; i< 4 ; i++)
        {

            Log.e("URL","Url"+GlobalConstants.SALON_IMAGES_URL+global.getOnBoardListing().get(i).get(GlobalConstants.PHOTO_OnBoard));

            Picasso.with(SplashActivity.this)
                    .load(GlobalConstants.SALON_IMAGES_URL+global.getOnBoardListing().get(i).get(GlobalConstants.PHOTO_OnBoard))
                    .into(new Target() {
                              @Override
                              public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                  try {

                                      String root = Environment.getExternalStorageDirectory().toString();
                                      File myDir = new File(root + "/Android/data/" + context.getPackageName() + "/yourDirectory");

                                      if (!myDir.exists()) {

                                          myDir.mkdirs();

                                          Log.e("Directory","Directory " + myDir.toString());
                                      }

                                      String name = new Date().toString() + ".png";
                                      myDir = new File(myDir, name);
                                      FileOutputStream out = new FileOutputStream(myDir);
                                      bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);

                                      Log.e("FileOutputStream","FileOutputStream " + out);

                                      out.flush();
                                      out.close();
                                  }
                                  catch(Exception e){

                                      Log.e("Picaso","Picaso Some exception occur");

                                  }
                              }

                              @Override
                              public void onBitmapFailed(Drawable errorDrawable) {
                              }

                              @Override
                              public void onPrepareLoad(Drawable placeHolderDrawable) {
                              }
                          }
                    );

        }

    }


    public void setSharedPreferences()
    {
        pref = getSharedPreferences(login_pref,MODE_PRIVATE);

        editor = pref.edit();

        editor.putBoolean(fst_login,false);

        editor.apply();
    }


    public void getSharePreferences()
    {
        pref = getSharedPreferences(login_pref,MODE_PRIVATE);

        is_first_login = pref.getBoolean(fst_login,true);

        is_logged_in = pref.getBoolean(is_logged_in_pref,false);

        str_username = pref.getString(user_name_pref,"rohit@gmail.com");
        str_password = pref.getString(password_pref,"123");
    }


    public class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        public String str_login_status;

       // ProgressBar pbFooterLoading =(ProgressBar)findViewById(R.id.pbFooterLoading);;

        @Override
        protected void onPreExecute() {
          //  progressDialog = ProgressDialog.show(SplashActivity.this, "Loading..", "Wait!");

            progressBarFooter.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

          str_login_status =  ws.LoginWebService(SplashActivity.this, str_username, str_password);

            return resp;

        }

        @Override
        protected void onPostExecute(String result) {

//            if (progressDialog.isShowing())
//            {
//                progressDialog.dismiss();
//            }

            progressBarFooter.setVisibility(View.GONE);

            if (str_login_status.equalsIgnoreCase("true"))
            {
                Intent i = new Intent(SplashActivity.this,HomeTestActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(context,"Some Error Occured \n Login Again..",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }

        }

    }

}