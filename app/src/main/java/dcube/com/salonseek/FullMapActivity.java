package dcube.com.salonseek;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import utils.MyApplication;
import webservices.GlobalConstants;

public class FullMapActivity extends Activity implements OnMapReadyCallback {

    ImageView back;

    GoogleMap mMap;

    MyApplication global;

    float lat, longi;

    int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_map);

        back = (ImageView) findViewById(R.id.back);

        global = (MyApplication) getApplicationContext();

        n = global.getItem_selected();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        lat = Float.parseFloat(global.getBookedListing().get(n).get(GlobalConstants.BOOKED_SALON_LAT));
        longi = Float.parseFloat(global.getBookedListing().get(n).get(GlobalConstants.BOOKED_SALON_LONG));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng salon = new LatLng(lat, longi);

        mMap.addMarker(new MarkerOptions()
                .title(global.getBookedListing().get(n).get(GlobalConstants.BOOKED_SALON_NAME))
                .snippet(global.getBookedListing().get(n).get(GlobalConstants.BOOKED_SALON_ADDRESS))
                .position(salon)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));

        //   googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(salon,13));


        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(salon,13));

    }


}
