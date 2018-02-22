package dcube.com.salonseek;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import java.util.List;

public class LocationTestActivity extends Activity implements LocationListener {

    LocationManager locationmanager;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_location_test);

        locationmanager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);

        Criteria cri=new Criteria();

        //String provider = locationmanager.getBestProvider(cri,false);
        //String provider = locationmanager.getProviders(cri,false).get(0);

        List<String> lProviders = locationmanager.getProviders(false);
        for(int i=0; i<lProviders.size(); i++){
            Log.d("LocationActivity", lProviders.get(i));
        }

        String provider = locationmanager.getBestProvider(cri, true); // null


        if(provider!=null && !provider.equals(""))

        {

            Location location= locationmanager.getLastKnownLocation(provider);

            if(location != null) {
                if (ContextCompat.checkSelfPermission(LocationTestActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(LocationTestActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                }
            }

            locationmanager.requestLocationUpdates(provider,2000,1,this);

            if(location!=null)

            {

                onLocationChanged(location);

            }

            else{

                Toast.makeText(getApplicationContext(),"location not found",Toast.LENGTH_LONG ).show();

            }
        }

        else

        {

            Toast.makeText(getApplicationContext(),"Provider is null",Toast.LENGTH_LONG).show();

        }

    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

// Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);

        return true;

    }

    @Override

    public void onLocationChanged(Location location) {

        Log.v("Latitude", " "+location.getLatitude());
        Log.v("Longitude", " "+location.getLongitude());

    }

    @Override

    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override

    public void onProviderEnabled(String s) {

    }

    @Override

    public void onProviderDisabled(String s) {

    }

}
