package dcube.com.salonseek;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import utils.MyApplication;
import webservices.GlobalConstants;

public class WebDirectActivity extends Activity {

    WebView web_view;

    MyApplication global;
    float lat, longi;
    float user_lat, user_long;
    boolean isfavorite;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_direct);

        web_view = (WebView) findViewById (R.id.web_view);

        global = (MyApplication) getApplicationContext();

        try {

            isfavorite = getIntent().getExtras().getBoolean("isFavorite");
            position = getIntent().getExtras().getInt("position");

        }

        catch (Exception e)
        {

        }

        if (isfavorite)
        {
            lat = Float.parseFloat(global.getAl_fav_salon().get(position).get(GlobalConstants.LATITUDE));
            longi = Float.parseFloat(global.getAl_fav_salon().get(position).get(GlobalConstants.LONGITUDE));
        }
        else
        {

            lat = Float.parseFloat(global.getSalonDetailListing().get(0).get(GlobalConstants.LATITUDE));
            longi = Float.parseFloat(global.getSalonDetailListing().get(0).get(GlobalConstants.LONGITUDE));
        }

        user_lat = Float.parseFloat("30.6972");
        user_long = Float.parseFloat("76.6858");

//        user_lat = Float.parseFloat(global.getSrc_lat());
//        user_long = Float.parseFloat(global.getSrc_long());

        Log.e("Lat",""+user_lat);
        Log.e("Long",""+user_long);

        web_view.setWebViewClient(new WebViewClient());
        web_view.getSettings().setJavaScriptEnabled(true);

     //   web_view.loadUrl("http://maps.google.com/maps?" + "saddr="+ user_lat +","+user_long + "&daddr="+lat+","+longi+"");

       web_view.loadUrl("http://maps.google.com/maps?" + "saddr="+user_lat+","+user_long+"" + "&daddr="+lat+","+longi+"");

    }
}
